package com.example.finalmtenant;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class HistoryViewHolder extends RecyclerView.ViewHolder {

    TextView husername,hamount,hmode,hpNo;
    View view;


    public HistoryViewHolder(@NonNull View itemView) {
        super(itemView);

//        husername = itemView.findViewById(R.id.textViewUserName);
        hamount = itemView.findViewById(R.id.hamount);
        hmode = itemView.findViewById(R.id.hmode);
        hpNo=itemView.findViewById(R.id.hphonenumber);
        view = itemView;
    }
}
