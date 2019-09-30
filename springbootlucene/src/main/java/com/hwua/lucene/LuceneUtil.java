package com.hwua.lucene;

import com.hwua.entity.Message;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Component;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class LuceneUtil {

    private Directory directory;

    public LuceneUtil() throws IOException {
        //创建索引库
        directory = FSDirectory.open(new File("D:\\lucene\\index").toPath());

    }

    /**
     * 创建索引库
     * @param list 短信结果集
     * @throws Exception
     */
    public void createIndexDB(List<Message> list) throws Exception{
        //以写的方式打开索引库，内部使用默认索引库
        IndexWriter indexWriter = new IndexWriter(directory,new IndexWriterConfig(new IKAnalyzer()));
        //遍历数据库的集合，为每一条数据创建Document对象
        for (Message msg : list) {
            Document document = new Document();
            //创建一个域（key value 是否保存（只有保存的才能读出内容））
            //短信标题
            Field msgTitle = new TextField("msgTitle",msg.getTitle(),Field.Store.YES);
            //短信内容
            Field msgContent = new TextField("msgContent",msg.getMsgContent(),Field.Store.YES);
            document.add(msgTitle);
            document.add(msgContent);
            //吧文档添加到索引库
            indexWriter.addDocument(document);
        }
        //关闭资源并提交
        indexWriter.close();
    }

    public List<Document> queryParser(String term) throws Exception {
        List<Document> list = new ArrayList<>();
        //以读的方式打开索引库
        IndexReader indexReader = DirectoryReader.open(directory);
        //创建IndexSearcher对象来查询指定索引库
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        //创建queryParser查询对象 参数1：作用域名，IK分词器
        QueryParser queryParser = new QueryParser("msgTitle",new IKAnalyzer());
        //输入要查询的关键字
        Query query = queryParser.parse(term);
        //得到对应关键字的索引id结果集
        TopDocs search = indexSearcher.search(query, 10);
        //遍历得到的Document对象
        for (ScoreDoc scoreDoc: search.scoreDocs){
            int docid = scoreDoc.doc;//得到文档的id
            //根据文档id找到对应的Document对象
            Document document = indexSearcher.doc(docid);
            list.add(document);
        }
        return list;
    }
}
