package com.example.finalmtenant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TenantsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    Button addTenants;
    private RecyclerView recyclerView;

    private List<Tenants> items;
    private FirebaseRecyclerOptions<Tenants> options;
    private DatabaseReference reference;
    private FirebaseRecyclerAdapter<Tenants, TenantViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenants);
        toolbar = findViewById(R.id.myToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        FloatingActionButton addTenants = findViewById(R.id.fab);
//        addTenants = findViewById(R.id.addNewTenant);
        recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        items = new ArrayList<>();
        displayData();

        addTenants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TenantsActivity.this, AddNewTenant.class);
                startActivity(intent);
            }
        });

    }



    private void displayData() {
        reference = FirebaseDatabase.getInstance().getReference("Tenants");
        Log.w("reference", reference.toString());

        options = new FirebaseRecyclerOptions.Builder<Tenants>().setQuery(reference, Tenants.class).build();
        adapter = new FirebaseRecyclerAdapter<Tenants, TenantViewHolder>(options) {
            @NonNull
            @Override
            public TenantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_tenant_view, parent, false);
                return new TenantViewHolder(v);
            }

            @Override
            protected void onBindViewHolder(@NonNull TenantViewHolder holder, final int position, @NonNull Tenants model) {

                final String key = getRef(position).getKey();

                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), Target_tenant.class);
                        intent.putExtra("key", key);
                        startActivity(intent);
                    }
                });
                holder.username.setText(model.getUsername());
                holder.apartmentNo.setText(model.getApartmentNo());
                holder.rent.setText(model.getRent());


                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String currentTenant = getItem(position).getName();
                        showDeleteTenantDialog(currentTenant);
                    }
                });
            }


        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);


    }

    private void showDeleteTenantDialog(final String currentTenant) {
        AlertDialog.Builder builder = new AlertDialog.Builder(TenantsActivity.this);
        builder.setTitle("Delete");
        builder.setMessage("Are you sure you want to delete this tenant?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Query mQuery = reference.orderByChild("name").equalTo(currentTenant);
                mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            ds.getRef().removeValue();
                        }
                        Toast.makeText(TenantsActivity.this, "Successfully deleted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(TenantsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

}
