package com.aipersonalbackend.aipersonalbackend.service;

import java.util.List;

public class OpenAIResponseDTO {
    public String id;
    public String object;
    public long created;
    public String model;
    public List<Choice> choices;
    public Usage usage;

    public static class Choice {
        public int index;
        public Message message;
        public String finish_reason;
    }

    public static class Message {
        public String role;
        public String content;
    }

    public static class Usage {
        public int prompt_tokens;
        public int completion_tokens;
        public int total_tokens;
    }
}
