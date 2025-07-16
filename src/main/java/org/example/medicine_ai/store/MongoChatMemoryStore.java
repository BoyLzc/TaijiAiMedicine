package org.example.medicine_ai.store;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.example.medicine_ai.bean.ChatMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class MongoChatMemoryStore implements ChatMemoryStore {
    @Autowired // 注入mongodb模板类
    private MongoTemplate mongoTemplate; // mongodb 模板类 用于操纵db
    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        System.out.println("查询记忆");
        // 定义查询语句
        Criteria criteria = Criteria.where("memoryId").is(memoryId);
        // 生成查询
        Query query = new Query(criteria);
        // 执行查询 ---> 根据记忆id找到聊天记录
        ChatMessages chatMessages = mongoTemplate.findOne(query, ChatMessages.class);
        // 如果没有记录，则返回一个空的列表
        if (chatMessages == null) {
            return new LinkedList<>();
        }
        // get方法 获取聊天记录
        String contentJson = chatMessages.getContent();
        // 将Json字符串格式的聊天记录，转化为大模型需要的 List<ChatMessage>
        return ChatMessageDeserializer.messagesFromJson(contentJson);
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> list) {
        System.out.println("更新记忆");
        // 定义查询条件
        Criteria criteria = Criteria.where("memoryId").is(memoryId);
        // 生成查询语句
        Query query = new Query(criteria);
        // 更新
        Update update = new Update();
        // 要更新的记忆
        String contentJson = ChatMessageSerializer.messagesToJson(list);
        // 定义更新内容
        update.set("content", contentJson);
        // 执行更新语句       当（criteria为空时），则是新增
        mongoTemplate.upsert(query, update, ChatMessages.class);
    }

    @Override
    public void deleteMessages(Object memoryId) {
        System.out.println("删除记忆");
        Criteria criteria = Criteria.where("memoryId").is(memoryId);
        Query query = new Query(criteria);
        mongoTemplate.remove(query, ChatMessages.class);
    }


}
