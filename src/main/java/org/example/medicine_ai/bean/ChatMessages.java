package org.example.medicine_ai.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor // 全参
@NoArgsConstructor // 无参
@Document("test") // 数据库中文档名(表名)
public class ChatMessages {

    // 唯一标识，映射到MongoDB文档的 _id 字段
    @Id
    private ObjectId messageId;

    private int memoryId;

    private String content; // 存储当前聊天记录列表的json字符串
}
