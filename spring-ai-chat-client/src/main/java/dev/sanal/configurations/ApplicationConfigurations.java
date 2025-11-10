package dev.sanal.configurations;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfigurations {

    @Bean
    public ChatClient openAiChatClient(OpenAiChatModel openAiChatModel) {
        return ChatClient.create(openAiChatModel);
    }

    @Bean
    public ChatClient ollamaAiChatClient(OllamaChatModel ollamaChatModel) {
        return ChatClient.create(ollamaChatModel);
    }
}
