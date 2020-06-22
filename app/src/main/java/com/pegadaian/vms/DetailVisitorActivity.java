package com.pegadaian.vms;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.print.PrintHelper;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class DetailVisitorActivity extends AppCompatActivity {

    @BindView(R.id.tvDetailNama) TextView txtNama;
    @BindView(R.id.tvDetailPerusahaan) TextView txtPerusahaan;
    @BindView(R.id.tvDetailEmail) TextView txtEmail;
    @BindView(R.id.tvDetailTelp) TextView txtTelp;
    @BindView(R.id.tvDetailCheckin) TextView txtCheckin;
    @BindView(R.id.tvDetailCheckout) TextView txtCheckout;
    @BindView(R.id.tvPrint) TextView txtPrintNama;
    @BindView(R.id.imgDetailQr) ImageView imgQr;
    @BindView(R.id.imgDetailVisitor) ImageView imgVisitor;
    @BindView(R.id.imgPrint) ImageView imgPrintQr;
    @BindView(R.id.lnPrint) LinearLayout lnPrintVis;

    ProgressDialog progressDialog;
    Bundle bundle;
    String key = "";
    String fotoUrl = "";
    String qrUrl = "";

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailvisitor);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(this);

        // CUSTOM TOAST
        Toasty.Config.getInstance()
                .setToastTypeface(ResourcesCompat.getFont(this, R.font.nexa_bold))
                .setTextSize(15)
                .apply();

        bundle = getIntent().getExtras();
        if (bundle != null) {

            key = bundle.getString("keyValue");
            qrUrl = bundle.getString("Qr");
            fotoUrl = bundle.getString("Foto");
            txtNama.setText(bundle.getString("Nama"));
            txtPerusahaan.setText(bundle.getString("Perusahaan"));
            txtTelp.setText(bundle.getString("Telp"));
            txtEmail.setText(bundle.getString("Email"));
            txtPrintNama.setText(bundle.getString("Nama"));

            if (bundle.getString("Checkin").equals("")) {
                txtCheckin.setText("-");
            } else {
                txtCheckin.setText(bundle.getString("Checkin"));
            }

            if (bundle.getString("Checkout").equals("")) {
                txtCheckout.setText("-");
            } else {
                txtCheckout.setText(bundle.getString("Checkout"));
            }

            Glide.with(this)
                    .load(bundle.getString("Foto"))
                    .into(imgVisitor);
            Glide.with(this)
                    .load(bundle.getString("Qr"))
                    .into(imgQr);
            Glide.with(this)
                    .load(bundle.getString("Qr"))
                    .into(imgPrintQr);
        }
    }

    // BUTTON CETAK
    public void btnCetak(View view) {

        lnPrintVis.setDrawingCacheEnabled(true);
        lnPrintVis.buildDrawingCache();
        Bitmap bitmapVis = lnPrintVis.getDrawingCache();

        PrintHelper photoPrinter = new PrintHelper(DetailVisitorActivity.this);
        photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        photoPrinter.printBitmap("visitor", bitmapVis);
    }

    // BUTTON HAPUS
    public void btnDelete(View view) {

        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        deleteQr();
    }

    public void deleteQr() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Visitor");
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference qrReference = storage.getReferenceFromUrl(qrUrl);

        qrReference.delete().addOnSuccessListener(aVoid -> {
            reference.child(key).removeValue();
            deleteFoto();
        });
    }

    public void deleteFoto() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Visitor");
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference fotoReference = storage.getReferenceFromUrl(fotoUrl);

        fotoReference.delete().addOnSuccessListener(aVoid -> {
            reference.child(key).removeValue();
            progressDialog.dismiss();
            Toasty.success(DetailVisitorActivity.this, "Data visitor telah dihapus",
                    Toast.LENGTH_SHORT, true).show();
            startActivity(new Intent(getApplicationContext(), VisitorActivity.class));
            finish();
        });
    }

    public void printVis() {

//        Bitmap bitmap = Bitmap.createBitmap(lnPrint.getWidth(), lnPrint.getHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas();
//        lnPrint.draw(canvas);

//        imgPrint.setImageBitmap(bm);

//        photoPrinter.printBitmap("visitor",
//                (Bitmap.createScaledBitmap(bm, 50, 150, false)));

//        String directory = android.os.Environment.getExternalStorageDirectory().toString();
//        try {
//            PdfWriter.getInstance(bitmap, new FileOutputStream(directory + "/visitor.pdf"));
//        } catch (DocumentException | FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        document.open();
    }

//    public static Bitmap loadBitmapFromView(View v) {
//        Bitmap b = Bitmap.createBitmap(
//                v.getLayoutParams().width, v.getLayoutParams().height, Bitmap.Config.ARGB_8888);
//        Canvas c = new Canvas(b);
//        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
//        v.draw(c);
//        return b;
//    }
}
