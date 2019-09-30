package com.hwua.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hwua.entity.Product;
import com.hwua.entity.ProductCategory;
import com.hwua.service.I.ProductCategoryService;
import com.hwua.service.I.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.*;

@Controller
@RequestMapping("/Product")
public class ProductController {
    @Autowired
    private ProductCategoryService pcs;
    @Autowired
    private ProductService ps;

    //查询商品分类
    @RequestMapping("/queryProductCategory")
    @ResponseBody
    public Map<String,Object> queryProductCategory(){
        Map<String,Object> map = new HashMap<>();
        try {
            //查询一级分类
            List<ProductCategory> parentList = pcs.queryParent();
            //把一级分类放入到map中
            map.put("parent",parentList);

            //查询一级分类的id
            List<Long> listById = pcs.queryParentById();
            //通过一级分类id查询出二级分类
            for (Long id : listById){
                List<ProductCategory> sonList = pcs.querySon(id);
                //把二级分类都放入到map中
                map.put(id.toString(),sonList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }


    //查询热卖商品
    @RequestMapping("/queryMaxProduct")
    @ResponseBody
    public Map<String,Object> queryMaxProduct(){
        Map<String,Object> map = new HashMap<>();
        try {
            //得到热卖商品结果集
            List<Product> list = ps.queryMaxProduct();
            //放入map集合中
            map.put("list",list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 分页查询
     * @param currentPage//当前页的页数
     * @param PageSize//每一页的记录
     * @return
     */
    @RequestMapping("/paging")
    @ResponseBody
    public Map<String,Object> paging(@RequestParam("currentPage")Integer currentPage,@RequestParam("PageSize")Integer PageSize){
        Map<String,Object> map = new HashMap<>();
        List<Product> pageList = null;
        //利用分页插件分页
        //获取第1页，10条内容，
        PageHelper.startPage(currentPage,PageSize);
        try {
            //进行分页查询得到结果集
            pageList = ps.queryProductLimit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //用PageInfo对结果进行包装
        PageInfo page = new PageInfo(pageList);
        map.put("paging",page);
        return map;
    }

    //通过id查询商品详情
    @RequestMapping("/productById")
    @ResponseBody
    public Map<String,Object> productById(@RequestParam("id")String id, HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map = new HashMap<>();
        //父类名
        String parName = "";
        //子类名
        String sonName = "";
        //父子类id
        Long parId = 0l;
        Long sonId  = 0l;
        try {
            //进行id查询商品
            Product product = ps.queryProductById(Integer.parseInt(id));
            //设置父子类id
            parId = product.getMajor_id();
            sonId = product.getMinor_id();
            //通过父子类id查询父子类名
            parName = pcs.queryProName(parId);
            sonName = pcs.queryProName(sonId);
            //把数据放入map
            map.put("productById",product);
            //把父类名和子类名传入集合
            map.put("parentName",parName);
            map.put("sonName",sonName);
            map.put("parId",parId);
            map.put("sonId",sonId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //获取最近浏览记录
        //进行字符串拼接
        String browseId = getBrowseId(id, request);
        //把拼接的字符串传到Cookie
        Cookie cookie = new Cookie("browseId", browseId);
        cookie.setMaxAge(60*60*24);//让cookie保存一天
        response.addCookie(cookie);//把cookie放到响应头中
        return map;
    }

    //打印最近浏览记录
    @RequestMapping("/prcLook")
    @ResponseBody
    public Map<String,Object> prcLook(@CookieValue(value = "browseId",required = false)String browseId){
        Map<String,Object> map = new HashMap<>();
        List<Product> list = new ArrayList<>();
        if (browseId==null){
            map.put("lookList",list);
            return map;
        }
        //进行字符串切割,获取商品id数组
        String[] split = browseId.split("-");
        //通过id获取商品并加到list中
        for (String id : split) {
            try {
                Product product = ps.queryProductById(Integer.parseInt(id));
                list.add(product);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        map.put("lookList",list);
        return map;
    }


    //字符串拼接
    public static String getBrowseId(String id,HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String browseId = null;
        boolean flag = false;//默认第一次访问

        if (cookies!=null){
            for (Cookie cookie : cookies) {
                //如果在cookies中找到k就说明不是第一次访问
                if (cookie.getName().equals("browseId")){
                    flag = true;//改为非第一次
                    //通过K获得V
                    browseId = cookie.getValue();
                    break;
                }
            }
        }

        //如果第一次访问直接返回id
        if (cookies==null || flag==false){
            return id;
        }

        //进行字符串切割
        String[] split = browseId.split("-");
        //因为要进行增删所有变成集合进行操作
        LinkedList<String> list = new LinkedList<>(Arrays.asList(split));
        //如果浏览记录小于3
        if (list.size()<3){
            //如果出现重复的就删除之前的
            if (list.contains(id)){
                list.remove(id);
            }
        }

        //如果浏览记录等于3
        if (list.size()==3){
            //如果有重复的就删除重复的，否则删除最后一个
            if (list.contains(id)){
                list.remove(id);
            }else{
                list.removeLast();
            }
        }
        //最后一定会添加当前的id
        list.addFirst(id);

        //进行字符串拼接
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            //第一个下标永远不加"-"
            if (i>0){
                sb.append("-");
            }
            sb.append(list.get(i));
        }

        return sb.toString();
    }
}
