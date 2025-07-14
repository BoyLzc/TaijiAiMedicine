package org.example.medicine_ai;

import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MedicineAiApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void testGPTDemo() {
        OpenAiChatModel demo = OpenAiChatModel.builder().
                baseUrl("https://langchain4j.dev/demo/openai/v1").
                apiKey("demo").
                modelName("gpt-4o-mini").build();
        String answer = demo.chat("你好， 你是谁");
        System.out.println(answer);
    }

    // 引入openAi和Spring集成的依赖，并在配置文件中配置好基本属性(模型等) 通过依赖注入方式获取模型对象
/*    @Qualifier("openAiChatModel")
    @Autowired
    private ChatLanguageModel chatLanguageModel;*/
    @Autowired
    private OpenAiChatModel openAiChatModel;
    @Test
    public void testSpringBootDemo() {
        String answer = openAiChatModel.chat("你好， 你是谁");
        System.out.println(answer);
    }

    // 本地部署
/*    @Qualifier("ollamaChatModel")
    @Autowired
    private  ChatLanguageModel chatLanguageModel2;*/

    @Autowired
    private OllamaChatModel ollamaChatModel;
    @Test
    public void testSpringBootDemoOllama() {
        String answer = ollamaChatModel.chat("你好， 你是谁");
        System.out.println(answer);
    }

    @Autowired
    private QwenChatModel qwenChatModel;
    @Test
    public void testDashScopeQwen(){
        String answer = qwenChatModel.chat("你好， 你是谁");
        System.out.println(answer);
    }

}
