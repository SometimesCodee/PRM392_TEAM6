package com.example.prm392_team6_spaapp.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.prm392_team6_spaapp.BuildConfig;
import com.example.prm392_team6_spaapp.R;
import com.example.prm392_team6_spaapp.adapter.MessageAdapter;
import com.example.prm392_team6_spaapp.dataLocal.DataLocalManager;
import com.example.prm392_team6_spaapp.model.ChatDatabase;
import com.example.prm392_team6_spaapp.model.ChatDao;
import com.example.prm392_team6_spaapp.model.ChatEntity;
import com.example.prm392_team6_spaapp.model.ChatMessage;
import com.example.prm392_team6_spaapp.model.Service;
import com.example.prm392_team6_spaapp.model.ServiceDatabase;
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

    private ChatDatabase db;
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

        currentUsername = DataLocalManager.getInstance().getPrefUsername();

        db = Room.databaseBuilder(getContext(), ChatDatabase.class, "chat_db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        chatDao = db.chatDao();

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
        ChatMessage userMessage = new ChatMessage(content, true);
        chatMessages.add(userMessage);
        adapter.notifyItemInserted(chatMessages.size() - 1);
        recyclerMessages.post(() -> recyclerMessages.smoothScrollToPosition(chatMessages.size() - 1));

        chatDao.insert(new ChatEntity(content, true, System.currentTimeMillis(), currentUsername));

        // 🔥 Lấy dữ liệu dịch vụ và tạo prompt
        List<Service> services = getServicesFromDatabase();
        String servicePrompt = convertServiceListToPrompt(services);

        // ✅ Xóa hội thoại cũ, chỉ giữ dữ liệu + câu hỏi
        apiMessages.clear();
        apiMessages.add(new Message("system", servicePrompt));
        apiMessages.add(new Message("user", content));

        ChatRequest request = new ChatRequest("gpt-3.5-turbo", apiMessages);

        progressLoading.setVisibility(View.VISIBLE);

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
                    recyclerMessages.post(() -> recyclerMessages.smoothScrollToPosition(chatMessages.size() - 1));

                    chatDao.insert(new ChatEntity(botReply, false, System.currentTimeMillis(), currentUsername));
                }
            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {
                progressLoading.setVisibility(View.GONE);
                String errorText = "Lỗi kết nối đến ChatGPT";
                chatMessages.add(new ChatMessage(errorText, false));
                adapter.notifyItemInserted(chatMessages.size() - 1);
                recyclerMessages.post(() -> recyclerMessages.smoothScrollToPosition(chatMessages.size() - 1));
            }
        });
    }

    private List<Service> getServicesFromDatabase() {
        return ServiceDatabase.getInstance(getContext()).getServiceDAO().getAllService();
    }

    private String convertServiceListToPrompt(List<Service> serviceList) {
        StringBuilder sb = new StringBuilder();
        sb.append("Bạn là một tư vấn viên spa chuyên nghiệp tại hệ thống chăm sóc sắc đẹp của chúng tôi. ")
                .append("Nhiệm vụ của bạn là tư vấn cho khách hàng về các dịch vụ spa hiện có bên dưới. ")
                .append("Chỉ được sử dụng thông tin trong danh sách dịch vụ này để trả lời. ")
                .append("Không được bịa ra dịch vụ không có trong danh sách. ")
                .append("Hãy trả lời một cách thân thiện, rõ ràng, dễ hiểu, và nếu phù hợp, có thể gợi ý thêm dịch vụ tương tự.\n\n");

        sb.append("Danh sách dịch vụ spa hiện có:\n");
        for (Service service : serviceList) {
            sb.append("- Tên dịch vụ: ").append(service.getServiceName()).append("\n")
                    .append("  Giá: ").append(service.getPrice()).append(" VND\n")
                    .append("  Thời gian: ").append(service.getDuration()).append("\n")
                    .append("  Mô tả: ").append(service.getDescription()).append("\n\n");
        }

        return sb.toString();
    }
}
