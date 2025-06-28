package com.example.prm392_team6_spaapp.network;

import com.example.prm392_team6_spaapp.model.openai.ChatRequest;
import com.example.prm392_team6_spaapp.model.openai.ChatResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface OpenAIService {

    @POST("v1/chat/completions")
    Call<ChatResponse> sendMessage(
            @Header("Authorization") String authHeader,
            @Body ChatRequest request
    );
}
