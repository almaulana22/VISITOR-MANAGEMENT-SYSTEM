package com.pegadaian.vms;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class AddVisitorActivity extends AppCompatActivity {

    @BindView(R.id.imgVisitorView) ImageView visitorImage;
    @BindView(R.id.imgQr) ImageView qrImage;
    @BindView(R.id.etNama) EditText etNama;
    @BindView(R.id.etPerusahaan) EditText etPerusahaan;
    @BindView(R.id.etTelp) EditText etTelp;
    @BindView(R.id.etEmail) EditText etEmail;
    @BindView(R.id.spTujuan) MaterialSpinner spTujuan;
    @BindView(R.id.spHost) MaterialSpinner spHost;
    @BindView(R.id.tvAddCheck) TextView txtAddCheck;

    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQ_CODE = 102;
    StorageReference reference;
    ProgressDialog progressDialog;
    String dataFoto, dataQr;
    String fileFoto = UUID.randomUUID() + ".jpg";
    int first = 0;
    int last = 1;

    // MENDAPATKAN TANGGAL & WAKTU TERKINI
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    String myCurrentDateTime = simpleDateFormat.format(new Date());

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambahdata);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);

        reference = FirebaseStorage.getInstance().getReference();
        progressDialog = new ProgressDialog(this);

        // SET VALUE AWAL
        txtAddCheck.setText(null);
        visitorImage.setId(first);

        // CUSTOM TOAST
        Toasty.Config.getInstance()
                .setToastTypeface(ResourcesCompat.getFont(this, R.font.nexa_bold))
                .setTextSize(15)
                .apply();

        // DAFTAR NAMA HOST
        List<String> tujuan = new ArrayList<>();
        tujuan.add("Meeting");
        tujuan.add("Interview");
        spTujuan.setItems(tujuan);

        // JIKA ITEM SPINNER TUJUAN DI KLIK, MAKA...
        spTujuan.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {

                spTujuan.setOnItemSelectedListener(this::onItemSelected);
            }
        });

        // DAFTAR NAMA HOST
        List<String> host = new ArrayList<>();
        host.add("Joko Supriyatno (Transformation Office)");
        host.add("Bowo Subono (Keuangan)");
        host.add("Eka Jayadi (Hukum)");
        host.add("Adam Iskandar (Budaya Kerja)");
        spHost.setItems(host);

        // JIKA ITEM SPINNER HOST DI KLIK, MAKA...
        spHost.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {

                spHost.setOnItemSelectedListener(this::onItemSelected);
            }
        });
    }

    // BUTTON AMBIL FOTO
    public void btnAmbilFoto(View view) {

        // MENDAPATKAN PERMISSION KAMERA
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        } else {
            openCamera();
        }
    }

    // MENGECEK PERMISSION KAMERA
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == CAMERA_PERM_CODE) {

            if (grantResults.length < 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toasty.error(AddVisitorActivity.this, "Izin kamera dibutuhkan",
                        Toast.LENGTH_SHORT, true).show();
            }
        }
    }

    // MEMBUKA KAMERA
    public void openCamera() {

        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, CAMERA_REQ_CODE);
    }

    // SETELAH MELAKUKAN FOTO, MAKA...
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQ_CODE && resultCode == RESULT_OK) {

            Bitmap imageVisitor = (Bitmap) data.getExtras().get("data");
            visitorImage.setImageBitmap(imageVisitor);
            visitorImage.setId(last);
        } else {
            Toasty.warning(AddVisitorActivity.this, "Foto belum diambil",
                    Toast.LENGTH_SHORT, true).show();
        }
    }

    // BUTTON SIMPAN
    public void btnSimpan(View view) {

        if (visitorImage.getId() == 0) {
            Toasty.error(AddVisitorActivity.this, "Data belum lengkap",
                    Toast.LENGTH_SHORT, true).show();
        } else if (etNama.getText().toString().isEmpty()) {
            Toasty.error(AddVisitorActivity.this, "Data belum lengkap",
                    Toast.LENGTH_SHORT, true).show();
        } else if (etPerusahaan.getText().toString().isEmpty()) {
            Toasty.error(AddVisitorActivity.this, "Data belum lengkap",
                    Toast.LENGTH_SHORT, true).show();
        } else if (etTelp.getText().toString().isEmpty()) {
            Toasty.error(AddVisitorActivity.this, "Data belum lengkap",
                    Toast.LENGTH_SHORT, true).show();
        } else if (etEmail.getText().toString().isEmpty()) {
            Toasty.error(AddVisitorActivity.this, "Data belum lengkap",
                    Toast.LENGTH_SHORT, true).show();;
        } else if (spTujuan.getText().toString().equals("P i l i h   t u j u a n")) {
            Toasty.error(AddVisitorActivity.this, "Data belum lengkap",
                    Toast.LENGTH_SHORT, true).show();
        } else if (spHost.getText().toString().equals("P i l i h   h o s t")) {
            Toasty.error(AddVisitorActivity.this, "Data belum lengkap",
                    Toast.LENGTH_SHORT, true).show();
        } else {
            uploadImageQr();
        }
    }

    // UPLOAD QR KE FIREBASE
    public void uploadImageQr() {

        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        // MEMBUAT QR DARI TANGGAL
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(myCurrentDateTime, BarcodeFormat.QR_CODE, 300, 300);
            Bitmap bitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.RGB_565);

            // MENGUBAH UKURAN BITMAP QR
            for (int x = 0; x < 300; x++) {
                for (int y = 0; y < 300; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            qrImage.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        // MENDAPATKAN DATA GAMBAR DARI IMAGE VIEW
        Bitmap bitmapQr = ((BitmapDrawable) qrImage.getDrawable()).getBitmap();
        ByteArrayOutputStream streamQr = new ByteArrayOutputStream();

        // KOMPRESS BITMAP KE JPG
        bitmapQr.compress(Bitmap.CompressFormat.JPEG, 100, streamQr);
        byte[] bytesQr = streamQr.toByteArray();

        // MENENTUKAN FILE PENYIMPANAN
        String fotoPath = "QrImage/" + fileFoto;
        UploadTask uploadTaskQr = reference.child(fotoPath).putBytes(bytesQr);

        uploadTaskQr.addOnSuccessListener(taskSnapshot -> {

            Task <Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
            while (!uriTask.isComplete());
            Uri urlImage = uriTask.getResult();
            dataQr = urlImage.toString();
            uploadImageVisitor();
        }).addOnFailureListener(e -> Toasty.error(AddVisitorActivity.this, "QR gagal disimpan",
                Toast.LENGTH_SHORT, true).show());
    }

    // UPLOAD FOTO VISITOR KE FIREBASE
    public void uploadImageVisitor() {

        // MENDAPATKAN DATA GAMBAR DARI IMAGE VIEW
        Bitmap bitmapVisitor = ((BitmapDrawable) visitorImage.getDrawable()).getBitmap();
        ByteArrayOutputStream streamVisitor = new ByteArrayOutputStream();

        // KOMPRESS BITMAP KE JPG
        bitmapVisitor.compress(Bitmap.CompressFormat.JPEG, 100, streamVisitor);
        byte[] bytesVisitor = streamVisitor.toByteArray();

        // MENENTUKAN FILE PENYIMPANAN
        String fotoPath = "VisitorImage/" + fileFoto;
        UploadTask uploadTask = reference.child(fotoPath).putBytes(bytesVisitor);

        uploadTask.addOnSuccessListener(taskSnapshot -> {

            Task <Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
            while (!uriTask.isComplete());
            Uri urlImage = uriTask.getResult();
            dataFoto = urlImage.toString();
            uploadVisitor();
            progressDialog.dismiss();
        }).addOnFailureListener(e -> Toasty.error(AddVisitorActivity.this, "Foto gagal disimpan",
                Toast.LENGTH_SHORT, true).show());
    }

    // UPLOAD DATA VISITOR KE FIREBASE
    public void uploadVisitor() {

        VisitorData visitorData = new VisitorData(
                etNama.getText().toString(),
                etPerusahaan.getText().toString(),
                etTelp.getText().toString(),
                etEmail.getText().toString(),
                spTujuan.getText().toString(),
                spHost.getText().toString(),
                txtAddCheck.getText().toString(),
                txtAddCheck.getText().toString(),
                dataFoto,
                dataQr
        );

        FirebaseDatabase.getInstance().getReference("Visitor")
                .child(myCurrentDateTime).setValue(visitorData).addOnCompleteListener(task -> {

            if (task.isSuccessful()) {

                startActivity(new Intent(AddVisitorActivity.this, MainActivity.class));
                Toasty.success(AddVisitorActivity.this, "Data visitor tersimpan",
                        Toast.LENGTH_SHORT, true).show();
                finish();
            }
        }).addOnFailureListener(e -> Toasty.error(AddVisitorActivity.this, "Data gagal disimpan",
                Toast.LENGTH_SHORT, true).show());
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AddVisitorActivity.this, MainActivity.class));
        finish();
    }
}