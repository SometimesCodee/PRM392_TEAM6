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
import com.example.prm392_team1_spaapp.model.Promotion;

import java.util.List;

public class PromotionAdapter extends RecyclerView.Adapter<PromotionAdapter.PromotionViewHolder> {

    private List<Promotion> mListPromotions;
    private OnItemClickListener itemClickListener;

    public void setData(List<Promotion> list, OnItemClickListener listener){
        this.mListPromotions = list;
        this.itemClickListener = listener;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public PromotionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_promtion,parent,false);
        return new PromotionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PromotionViewHolder holder, int position) {
        Promotion promotion = mListPromotions.get(position);
        if (promotion == null){
            return;
        }

        holder.imgPromotionImage.setImageResource(promotion.getImageResId());
        holder.tvPromotionName.setText(promotion.getTitle());
        holder.tvDiscount.setText(String.valueOf(promotion.getDiscount())+"%" );
        holder.tvDesc.setText("Tới ngày " + promotion.getEndDate());
        holder.layoutPromotionItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(promotion);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        if (mListPromotions!=null){
            return mListPromotions.size();
        }
        return 0;
    }

    public class PromotionViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout layoutPromotionItem;
        private ImageView imgPromotionImage;
        private TextView tvPromotionName,tvDiscount,tvDesc;
        public PromotionViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPromotionImage = itemView.findViewById(R.id.img_promotion);
            tvPromotionName = itemView.findViewById(R.id.tv_promotion_name);
            tvDiscount = itemView.findViewById(R.id.tv_discount);
            tvDesc = itemView.findViewById(R.id.tv_desc);
            layoutPromotionItem = itemView.findViewById(R.id.layout_promotion_item);
        }
    }
}
