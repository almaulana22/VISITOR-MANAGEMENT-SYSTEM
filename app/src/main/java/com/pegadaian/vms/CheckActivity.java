package com.pegadaian.vms;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class CheckActivity extends Activity implements QRCodeReaderView.OnQRCodeReadListener {

    @BindView(R.id.qrReader) QRCodeReaderView qrCodeReaderView;

    // MENDAPATKAN TANGGAL & WAKTU TERKINI
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    String myCurrentDateTime = simpleDateFormat.format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        ButterKnife.bind(this);

        // CUSTOM TOAST
        Toasty.Config.getInstance()
                .setToastTypeface(ResourcesCompat.getFont(this, R.font.nexa_bold))
                .setTextSize(15)
                .apply();

        qrCodeReaderView.setOnQRCodeReadListener(this);
        qrCodeReaderView.setQRDecodingEnabled(true);
        qrCodeReaderView.setAutofocusInterval(2000L);
        qrCodeReaderView.setBackCamera();
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {

        Intent getData = getIntent();
        int code = getData.getIntExtra("requestCode", 0);

        if (code == 1) {
            // MENDAPATKAN REFERENSI FIREBASE
            DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                    .getReference("Visitor").child(text);
            databaseReference.child("itemCheckin").setValue(myCurrentDateTime);

            Toasty.success(CheckActivity.this, "Checkin berhasil",
                    Toast.LENGTH_SHORT, true).show();
            this.finish();

        } else if (code == 2) {
            // MENDAPATKAN REFERENSI FIREBASE
            DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                    .getReference("Visitor").child(text);

            databaseReference.child("itemCheckout").setValue(myCurrentDateTime);

            Toasty.success(CheckActivity.this, "Checkout berhasil",
                    Toast.LENGTH_SHORT, true).show();
            this.finish();
        }
    }

//    private void tampilData() {
//
//        // MENDAPATKAN REFERENSI FIREBASE
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
//                .getReference("Visitor").child(txtData.getText().toString());
//
//        // MENDAPATKAN DATA VISITOR DARI FIREBASE
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                String nama = dataSnapshot.child("itemNama").getValue().toString();
//                String perusahaan = dataSnapshot.child("itemPerusahaan").getValue().toString();
//                String telp = dataSnapshot.child("itemTelp").getValue().toString();
//
//                txtNama.setText(nama);
//                txtPerusahaan.setText(perusahaan);
//                txtTelp.setText(telp);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });
//    }

    @Override
    protected void onResume() {
        super.onResume();
        qrCodeReaderView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        qrCodeReaderView.stopCamera();
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}
