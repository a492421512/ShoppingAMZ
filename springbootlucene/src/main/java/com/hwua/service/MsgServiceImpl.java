package com.hwua.service;

import com.hwua.entity.Message;
import com.hwua.lucene.LuceneUtil;
import com.hwua.mapper.MsgMapper;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.ScoreDoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class MsgServiceImpl implements MsgService {
    @Autowired
    private MsgMapper msgMapper;
    @Autowired
    private LuceneUtil luceneUtil;

    /**
     * 通过全文检索进行关键字查询
     * @param term
     * @return
     * @throws Exception
     */
    @Override
    public List<Document> findMsgByString(String term) throws Exception{
        File file = new File("D:\\lucene\\index");
        File[] files = file.listFiles();
        //判断索引库里面是否已经创建
        if (files.length==0) {
            //得到短信结果集
            List<Message> msgList = msgMapper.findAllMsg();
            //创建索引库
            luceneUtil.createIndexDB(msgList);
        }
        //进行query查询,返回索引库的索引id
        return luceneUtil.queryParser(term);
    }
}
