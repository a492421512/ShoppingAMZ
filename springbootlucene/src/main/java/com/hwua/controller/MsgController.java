package com.hwua.controller;

import com.hwua.service.MsgService;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.ScoreDoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class MsgController {
    @Autowired
    private MsgService msgService;

    @RequestMapping("/query")
    public String queryByTerm(String term, Model model) throws Exception {
        //获得对应的Document
        List<Document> docList = msgService.findMsgByString(term);
        model.addAttribute("list",docList);
        System.out.println(docList);
        return "success";
    }
}
