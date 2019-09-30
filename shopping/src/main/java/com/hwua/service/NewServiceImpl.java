package com.hwua.service;


import com.hwua.entity.News;
import com.hwua.mapper.NewDao;
import com.hwua.service.I.NewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
@Service
public class NewServiceImpl implements NewService {
    @Autowired
    private NewDao newDao ;

    /**
     * 查询所有新闻
     * @return
     * @throws SQLException
     */
    @Override
    public List<News> queryNews() throws SQLException {
        return newDao.queryNews();
    }

    /**
     * 通过id查询新闻
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public News queryNewById(Integer id) throws SQLException {
        return newDao.queryNewById(id);
    }
}
