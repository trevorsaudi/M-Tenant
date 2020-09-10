package com.example.finalmtenant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
     private Context context;
     private List<Tenants> items;

    public ItemAdapter(Context context, List<Tenants> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.single_tenant_view,parent,false);
        return new ItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        final Tenants item=items.get(position);
        holder.tvname.setText(item.name);
        holder.tvusername.setText(item.username);
        holder.tvemail.setText(item.email);
        holder.tvpNo.setText(item.pNo);
        holder.tvapartment.setText(item.apartmentNo);
        holder.tvrent.setText(item.rent);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvname,tvusername,tvemail,tvpNo,tvapartment,tvrent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvname=itemView.findViewById(R.id.textViewName);
            tvusername=itemView.findViewById(R.id.textViewUserName);
            tvemail=itemView.findViewById(R.id.textViewEmail);
            tvpNo=itemView.findViewById(R.id.textViewPhoneNumber);
            tvapartment=itemView.findViewById(R.id.textViewApartmentNo);
            tvrent=itemView.findViewById(R.id.textViewRent);
        }
    }
}
