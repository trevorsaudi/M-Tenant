package com.example.finalmtenant;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class TenantViewHolder extends RecyclerView.ViewHolder {

    TextView username,apartmentNo,rent,delete;
    View view;

    public TenantViewHolder(@NonNull View itemView) {
        super(itemView);

        username = itemView.findViewById(R.id.textViewUserName);
        apartmentNo = itemView.findViewById(R.id.textViewApartmentNo);
        rent = itemView.findViewById(R.id.textViewRent);
        delete=itemView.findViewById(R.id.trash);
        view = itemView;


    }
}
