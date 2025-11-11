package dev.sanal.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final ChatClient openAichatClient;
    private final ChatClient ollamaAiChatClient;
    private final static String OPENAI = "openai";
    private final static String OLLAMA = "ollama";

    public ChatController(
            @Qualifier("openAiChatClient") ChatClient openAichatClient,
            @Qualifier("ollamaAiChatClient") ChatClient ollamaAiChatClient
    ) {
        this.openAichatClient = openAichatClient;
        this.ollamaAiChatClient = ollamaAiChatClient;
    }

    @GetMapping("/ai")
    String generation(@RequestParam("userInput") String userInput,
                      @RequestParam(value = "aiModel", defaultValue = "ollama") String aiModel) {
        if (aiModel.equals(OPENAI))
            return this.openAichatClient.prompt().user(userInput).call().content();
        return this.ollamaAiChatClient.prompt().user(userInput).call().content();
    }

    @GetMapping("/country/cities")
    CountryCities getCountryCities(
            @RequestParam(value = "country", defaultValue = "Sweden") String country
    ) {
        String promptTemplates = "Generate 5 popular cities in the {country}";
        return this.ollamaAiChatClient.prompt()
                .user(promptUserSpec -> promptUserSpec
                        .text(promptTemplates)
                        .param("country", country))
                .call()
                .entity(CountryCities.class);
    }

    @GetMapping("/stream/ai")
    Flux<String> streamGeneration(@RequestParam("userInput") String userInput,
                                  @RequestParam(value = "aiModel", defaultValue = "ollama") String aiModel) {
        if (aiModel.equals(OPENAI))
            return this.openAichatClient.prompt().user(userInput).stream().content();
        return this.ollamaAiChatClient.prompt().user(userInput).stream().content();
    }
}
