package org.example.medicine_ai;

import org.example.medicine_ai.bean.ChatMessages;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@SpringBootTest
public class MongoDBTest {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void testMongoDB() {
        // 插入
        ChatMessages chatMessages = new ChatMessages();
        chatMessages.setContent("Hello World3");
        mongoTemplate.insert(chatMessages);
    }

    @Test
    public void testFindById(){
        ChatMessages chatMessages = mongoTemplate.findById("687349dd5556b510dfefd6d7", ChatMessages.class);
        System.out.println(chatMessages);
    }

    @Test
    public void testUpdate() {
        // 查询条件
        Criteria criteria = Criteria.where("_id").is("687349dd5556b510dfefd6d7");
        // 查询
        Query query = new Query(criteria);
        // 更新
        Update update = new Update();
        update.set("content", "新纪录");
        // 修改或新增（criteria为空）
        mongoTemplate.upsert(query, update, ChatMessages.class);
    }

    @Test
    public void testDelete() {
        Criteria criteria = Criteria.where("_id").is("687349dd5556b510dfefd6d7");
        Query query = new Query(criteria);
        mongoTemplate.remove(query, ChatMessages.class);
    }
}

