package com.example.prm392_team6_spaapp.adapter.my_interface;

import com.example.prm392_team6_spaapp.model.Promotion;
import com.example.prm392_team6_spaapp.model.Service;

public interface OnItemClickListener {
    void onItemClick(Service selectedService);

    void onItemClick(Promotion selectedPromotion);
}
