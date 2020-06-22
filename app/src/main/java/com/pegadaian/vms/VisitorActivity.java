package com.pegadaian.vms;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class VisitorActivity extends AppCompatActivity {

    @BindView(R.id.rvVisitor) RecyclerView recyclerView;
    @BindView(R.id.etSearch) EditText edtSearch;

    List<VisitorData> visitorDataList;
    ProgressDialog progressDialog;
    VisitorAdapter visitorAdapter;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        // SET ADAPTER
        visitorDataList = new ArrayList<>();
        visitorAdapter = new VisitorAdapter(VisitorActivity.this, visitorDataList);
        recyclerView.setAdapter(visitorAdapter);

        // CONVERTER RECYCLER VIEW
        GridLayoutManager gridLayoutManager = new GridLayoutManager(VisitorActivity.this, 1);
        gridLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(gridLayoutManager);

        // MENDAPATKAN REFERENSI FIREBASE
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Visitor");

        // MENDAPATKAN DATA VISITOR DARI FIREBASE
        databaseReference.addValueEventListener(new ValueEventListener() {
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

        // EDIT TEXT SEARCH
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

    // FILTER DATA UNTUK SEARCH
    private void filter(String text) {

        ArrayList<VisitorData> filterList = new ArrayList<>();

        for (VisitorData item : visitorDataList) {
            if (item.getItemNama().toLowerCase().contains(text.toLowerCase())) {
                filterList.add(item);
            }
        }
        visitorAdapter.filteredList(filterList);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(VisitorActivity.this, MainActivity.class));
        finish();
    }
}
