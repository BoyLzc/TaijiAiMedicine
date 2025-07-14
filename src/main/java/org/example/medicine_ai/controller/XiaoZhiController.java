package org.example.medicine_ai.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.medicine_ai.assistant.XiaoZhiAgent;
import org.example.medicine_ai.bean.ChatForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "硅谷小智")
@RestController
@ResponseBody
public class XiaoZhiController {
    @Autowired
    private XiaoZhiAgent xiaoZhiAgent;

    @Operation(summary = "对话")
    @PostMapping("/chat")
    public String chat(@RequestBody ChatForm chatForm) {
        return xiaoZhiAgent.chat(chatForm.getMemoryId(), chatForm.getUserMessage());
    }
}
