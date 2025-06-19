package com.example.prm392_team1_spaapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_team1_spaapp.R;
import com.example.prm392_team1_spaapp.adapter.my_interface.OnItemClickListener;
import com.example.prm392_team1_spaapp.model.Service;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>{
    private List<Service> mListService;
    private OnItemClickListener itemClickListener;

    public void setData(List<Service> list, OnItemClickListener listener){
        this.mListService = list;
        this.itemClickListener = listener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service,parent,false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        Service service = mListService.get(position);
        if (service == null){
            return;
        }

        holder.imgServiceImage.setImageResource(service.getImg());
        holder.tvServiceName.setText(service.getServiceName());

        holder.layoutServiceItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(service);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListService!=null){
            return mListService.size();
        }
        return 0;
    }

    public class ServiceViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout layoutServiceItem;
        private ImageView imgServiceImage;
        private TextView tvServiceName;
        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            imgServiceImage = itemView.findViewById(R.id.img_service_image);
            tvServiceName = itemView.findViewById(R.id.tv_service_name);
            layoutServiceItem = itemView.findViewById(R.id.layout_service_item);
        }
    }
}
