package com.example.prm392_team6_spaapp.model.openai;

import java.util.List;

public class ChatRequest {
    private String model;
    private List<Message> messages;

    public ChatRequest(String model, List<Message> messages) {
        this.model = model;
        this.messages = messages;
    }

    public String getModel() {
        return model;
    }

    public List<Message> getMessages() {
        return messages;
    }
}
