package com.example.prm392_team1_spaapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_team1_spaapp.R;
import com.example.prm392_team1_spaapp.model.RechargeHistory;

import java.util.List;


public class ListHistoryAdapter extends RecyclerView.Adapter<ListHistoryAdapter.ListViewHolder>{


    private List<RechargeHistory> listRechargeHistory;

    public ListHistoryAdapter(List<RechargeHistory> data) {
        this.listRechargeHistory = data;
    }


    public void setData(List<RechargeHistory> list){
        this.listRechargeHistory = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_history_item, parent, false);
        return  new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        RechargeHistory rechargeHistory = listRechargeHistory.get(position);
        if (rechargeHistory ==null){
            return;
        }
        holder.tv_name.setText(rechargeHistory.getTransactionType());
        holder.tv_date.setText(rechargeHistory.getTransactionTime().toString());
        holder.description.setText(rechargeHistory.getDescription());
        holder.money.setText(Float.toString(rechargeHistory.getAmount()));
        holder.status.setText(rechargeHistory.getStatus()==1? "Thành công" : "Thất bại");
        int colorRes = rechargeHistory.getStatus() == 1 ? R.color.green : R.color.red;
        int color = ContextCompat.getColor(holder.itemView.getContext(), colorRes);
        holder.status.setTextColor(color);

    }

    @Override
    public int getItemCount() {
        if(listRechargeHistory !=null){
            return listRechargeHistory.size();
        }
        return 0;
    }


    public class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name;
        private TextView tv_date;
        private TextView description;
        private TextView money;
        private TextView status;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_date = itemView.findViewById(R.id.tv_date);
            description = itemView.findViewById(R.id.description);
            money = itemView.findViewById(R.id.money);
            status = itemView.findViewById(R.id.status);
        }
    }
}
