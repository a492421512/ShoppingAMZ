package com.hwua.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hwua.entity.Product;
import com.hwua.service.I.ProductCategoryService;
import com.hwua.service.I.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/proCategory")
public class ProCategoryController {
    @Autowired
    private ProductCategoryService pcs;
    @Autowired
    private ProductService ps;

    //查询二级类目的商品
    @RequestMapping("/querySonId")
    @ResponseBody
    public Map<String,Object> querySonId(@RequestParam("id")Long id){
        Map<String,Object> map = new HashMap<>();
        //父类名
        String parName = "";
        //子类名
        String sonName = "";
        //父子类id
        Long parId = 0l;
        Long sonId  = 0l;
        try {
            //获取父类id
            List<Long> parentId = pcs.queryParentById();
            List<Product> list = null;
            //如果传过来的是一级分类ID就打印一级分类集合
            if (parentId.contains(id)){
                list = ps.queryProByParentId(id);
                //查询分类名
                parName = pcs.queryProName(id);
                //设置父类id
                parId = id;
            }else {//否则打印二级分类
                //得到对应ID的结果集
                list = ps.queryProBySonId(id);
                //通过子类id查询父类的id
                sonId = id;
                parId = pcs.queryParentId(id);
                //查询分类名
                parName = pcs.queryProName(parId);
                sonName = pcs.queryProName(id);
            }
            map.put("sonList",list);
            //把父类名和子类名传入集合
            map.put("parentName",parName);
            map.put("sonName",sonName);
            map.put("parId",parId);
            map.put("sonId",sonId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    //进行模糊查询
    @RequestMapping("/queryProList")
    @ResponseBody
    public Map<String,Object> queryProList(@RequestParam("qname")String qname){
        Map<String,Object> map = new HashMap<>();
        //父类名
        String parName = "";
        //子类名
        String sonName = "";
        //父子类id
        Long parId = 0L;
        Long sonId  = 0L;
        try {
            //得到模糊查询结果集
            List<Product> list = ps.queryLikeByName(qname);
            if (list!=null) {
                //得到集合中第一个父子类id
                Product product = list.get(0);
                parId = product.getMajor_id();
                sonId = product.getMinor_id();
                //查询分类名
                parName = pcs.queryProName(parId);
                sonName = pcs.queryProName(sonId);
            }
            map.put("sonList",list);
            //把父类名和子类名传入集合
            map.put("parentName",parName);
            map.put("sonName",sonName);
            map.put("parId",parId);
            map.put("sonId",sonId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }
}
