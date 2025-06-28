package com.example.prm392_team6_spaapp.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import com.example.prm392_team6_spaapp.BuildConfig;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.prm392_team6_spaapp.R;
import com.example.prm392_team6_spaapp.adapter.MessageAdapter;
import com.example.prm392_team6_spaapp.model.AppDatabase;
import com.example.prm392_team6_spaapp.model.ChatDao;
import com.example.prm392_team6_spaapp.model.ChatEntity;
import com.example.prm392_team6_spaapp.model.ChatMessage;
import com.example.prm392_team6_spaapp.model.openai.ChatRequest;
import com.example.prm392_team6_spaapp.model.openai.ChatResponse;
import com.example.prm392_team6_spaapp.model.openai.Message;
import com.example.prm392_team6_spaapp.network.OpenAIService;
import com.example.prm392_team6_spaapp.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatFragment extends Fragment {

    private ProgressBar progressLoading;
    private RecyclerView recyclerMessages;
    private EditText editMessage;
    private ImageButton btnSend;
    private MessageAdapter adapter;
    private List<ChatMessage> chatMessages;
    private List<Message> apiMessages;

    private AppDatabase db;
    private ChatDao chatDao;

    private String currentUsername;
    private String API_KEY;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        API_KEY = BuildConfig.OPENAI_API_KEY;
        recyclerMessages = view.findViewById(R.id.recyclerMessages);
        editMessage = view.findViewById(R.id.editMessage);
        btnSend = view.findViewById(R.id.btnSend);
        progressLoading = view.findViewById(R.id.progressLoading);

        chatMessages = new ArrayList<>();
        apiMessages = new ArrayList<>();
        adapter = new MessageAdapter(chatMessages);
        recyclerMessages.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerMessages.setAdapter(adapter);

        // 👉 Lấy username từ SharedPreferences
        currentUsername = com.example.prm392_team6_spaapp.dataLocal.DataLocalManager.getInstance().getPrefUsername();

        // Init SQLite
        db = Room.databaseBuilder(getContext(), AppDatabase.class, "chat_db").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        chatDao = db.chatDao();

        // Load lịch sử chat theo tài khoản
        List<ChatEntity> history = chatDao.getMessagesForUser(currentUsername);
        for (ChatEntity msg : history) {
            chatMessages.add(new ChatMessage(msg.message, msg.isUser));
            apiMessages.add(new Message(msg.isUser ? "user" : "assistant", msg.message));
        }
        adapter.notifyDataSetChanged();

        btnSend.setOnClickListener(v -> {
            String userMessage = editMessage.getText().toString().trim();
            if (!TextUtils.isEmpty(userMessage)) {
                sendMessage(userMessage);
                editMessage.setText("");
            }
        });

        return view;
    }

    private void sendMessage(String content) {
        // Hiển thị tin nhắn người dùng
        ChatMessage userMessage = new ChatMessage(content, true);
        chatMessages.add(userMessage);
        adapter.notifyItemInserted(chatMessages.size() - 1);
        recyclerMessages.scrollToPosition(chatMessages.size() - 1);

        // Lưu SQLite
        ChatEntity userEntity = new ChatEntity(content, true, System.currentTimeMillis(), currentUsername);
        chatDao.insert(userEntity);

        // Gửi đến OpenAI
        apiMessages.add(new Message("user", content));
        ChatRequest request = new ChatRequest("gpt-3.5-turbo", apiMessages);

        progressLoading.setVisibility(View.VISIBLE); // Show loading

        OpenAIService service = RetrofitClient.getOpenAIService();
        Call<ChatResponse> call = service.sendMessage(API_KEY, request);

        call.enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                progressLoading.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    String botReply = response.body().getChoices().get(0).getMessage().getContent();
                    apiMessages.add(new Message("assistant", botReply));

                    chatMessages.add(new ChatMessage(botReply, false));
                    adapter.notifyItemInserted(chatMessages.size() - 1);
                    recyclerMessages.scrollToPosition(chatMessages.size() - 1);

                    // Lưu SQLite
                    ChatEntity botEntity = new ChatEntity(botReply, false, System.currentTimeMillis(), currentUsername);
                    chatDao.insert(botEntity);
                }
            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {
                progressLoading.setVisibility(View.GONE);
                String errorText = "Lỗi kết nối đến ChatGPT";
                chatMessages.add(new ChatMessage(errorText, false));
                adapter.notifyItemInserted(chatMessages.size() - 1);
                recyclerMessages.scrollToPosition(chatMessages.size() - 1);
            }
        });
    }
}
