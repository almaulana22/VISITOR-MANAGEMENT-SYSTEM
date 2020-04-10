package com.pegadaian.vms;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VisitorActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List <VisitorData> visitorDataList;
    VisitorData visitorData;
    ProgressDialog progressDialog;
    VisitorAdapter visitorAdapter;
    EditText edtSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor);

        recyclerView = (RecyclerView)findViewById(R.id.rvVisitor);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(VisitorActivity.this,1);
        recyclerView.setLayoutManager(gridLayoutManager);

        edtSearch = (EditText)findViewById(R.id.etSearch);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data ....");

        visitorDataList = new ArrayList<>();

        visitorAdapter  = new VisitorAdapter(VisitorActivity.this,visitorDataList);
        recyclerView.setAdapter(visitorAdapter);


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Visitor");

        progressDialog.show();
        ValueEventListener eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                visitorDataList.clear();

                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {

                    VisitorData visitorData = itemSnapshot.getValue(VisitorData.class);
                    visitorData.setKey(itemSnapshot.getKey());
                    visitorDataList.add(visitorData);

                }

                visitorAdapter.notifyDataSetChanged();
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                filter(s.toString());

            }
        });
    }

    private void filter(String text) {

        ArrayList <VisitorData> filterList = new ArrayList<>();

        for(VisitorData item: visitorDataList){

            if(item.getItemNama().toLowerCase().contains(text.toLowerCase())){

                filterList.add(item);

            }
        }

        visitorAdapter.filteredList(filterList);

    }
}
