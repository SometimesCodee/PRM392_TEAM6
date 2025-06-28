package com.example.prm392_team6_spaapp.model.openai;

import java.util.List;

public class ChatResponse {
    private List<Choice> choices;

    public List<Choice> getChoices() {
        return choices;
    }

    public static class Choice {
        private Message message;

        public Message getMessage() {
            return message;
        }
    }
}
