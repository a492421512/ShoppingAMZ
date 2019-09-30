package com.hwua.service;

import com.hwua.entity.Message;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.ScoreDoc;

import java.util.List;

public interface MsgService {
    //根据关键词查找
    public abstract List<Document> findMsgByString(String term) throws Exception;
}
