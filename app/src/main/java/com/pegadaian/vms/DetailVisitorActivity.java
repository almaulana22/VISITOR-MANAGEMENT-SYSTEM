package com.pegadaian.vms;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DetailVisitorActivity extends AppCompatActivity {

    TextView txtNama, txtPerusahaan, txtTelp, txtEmail, txtCheckin, txtCheckout;
    ImageView imgVisitor;
    String key = "";
    String imageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailvisitor);

        imgVisitor = findViewById(R.id.imgVisitor);
        txtNama = findViewById(R.id.tvNama);
        txtPerusahaan = findViewById(R.id.tvPerusahaan);
        txtTelp = findViewById(R.id.tvTelp);
        txtEmail = findViewById(R.id.tvEmail);
        txtCheckin = findViewById(R.id.tvCheckin);
        txtCheckout = findViewById(R.id.tvCheckout);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            key = bundle.getString("keyValue");
            imageUrl = bundle.getString("Image");
            txtNama.setText(bundle.getString("Nama"));
            txtPerusahaan.setText(bundle.getString("Perusahaan"));
            txtTelp.setText(bundle.getString("Telp"));
            txtEmail.setText(bundle.getString("Email"));
            txtCheckin.setText(bundle.getString("Checkin"));
            txtCheckout.setText(bundle.getString("Checkout"));

            Glide.with(this)
                    .load(bundle.getString("Image"))
                    .into(imgVisitor);
        }

    }

    // BUTTON HAPUS
    public void btnDelete(View view) {

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Visitor");
        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);

        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                reference.child(key).removeValue();
                Toast.makeText(DetailVisitorActivity.this, "Visitor Telah Dihapus", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), VisitorActivity.class));
                finish();
            }
        });
    }

//    public void btnUpdate (View view) {
//
//        startActivity(new Intent(getApplicationContext(),UpdateVisitorActivity.class)
//                .putExtra("namaKey",txtNama.getText().toString())
//                .putExtra("perusahaanKey",txtPerusahaan.getText().toString())
//                .putExtra("telpKey",txtTelp.getText().toString())
//                .putExtra("emailKey",txtEmail.getText().toString())
//                .putExtra("oldImageUrl",imageUrl)
//                .putExtra("key",key)
//        );
//    }
}
