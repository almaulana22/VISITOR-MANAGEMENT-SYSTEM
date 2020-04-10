package com.pegadaian.vms;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.disklrucache.DiskLruCache;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Member;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import butterknife.OnItemSelected;

public class AddVisitorActivity extends AppCompatActivity {

    ImageView visitorImage;
    EditText etNama, etPerusahaan, etTelp, etEmail;
    MaterialSpinner spTujuan, spHost;
    String dataFoto;

    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQ_CODE = 102;
    private StorageReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambahdata);

        visitorImage = findViewById(R.id.imgVisitorView);
        etNama = findViewById(R.id.etNama);
        etPerusahaan = findViewById(R.id.etPerusahaan);
        etTelp = findViewById(R.id.etTelp);
        etEmail = findViewById(R.id.etEmail);
        spTujuan = findViewById(R.id.spTujuan);
        spHost = findViewById(R.id.spHost);

        // MENDAPATKAN REFERENSI FIREBASE
        reference = FirebaseStorage.getInstance().getReference();

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

        askCameraPermissions();
    }

    // MENDAPATKAN PERMISSION KAMERA
    private void askCameraPermissions() {

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
                Toast.makeText(this, "Izin Kamera Dibutuhkan!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // MEMBUKA KAMERA
    private void openCamera() {

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
        } else {
            Toast.makeText(this, "Foto Belum Diambil!", Toast.LENGTH_SHORT).show();
        }
    }

    // BUTTON SIMPAN
    public void btnSimpan(View view) {

        uploadImage();
    }

    // UPLOAD GAMBAR KE FIREBASE
    public void uploadImage() {

        // MENDAPATKAN DATA GAMBAR DARI IMAGE VIEW
        visitorImage.setDrawingCacheEnabled(true);
        visitorImage.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) visitorImage.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        // KOMPRESS BITMAP KE JPG
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bytes = stream.toByteArray();

        // MENENTUKAN FILE PENYIMPANAN
        String namaFile = UUID.randomUUID() + ".jpg";
        String fotoPath = "VisitorImage/" + namaFile;
        UploadTask uploadTask = reference.child(fotoPath).putBytes(bytes);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Menambahkan Data....");
        progressDialog.show();

        uploadTask.addOnSuccessListener(taskSnapshot -> {

            uploadVisitor();
            progressDialog.dismiss();
        }).addOnFailureListener(e -> progressDialog.setMessage("Gagal Menambahkan Data!"));
    }


    // UPLOAD DATA VISITOR KE FIREBASE
    public void uploadVisitor() {

        VisitorData visitorData = new VisitorData(
                etNama.getText().toString(),
                etPerusahaan.getText().toString(),
                etTelp.getText().toString(),
                etEmail.getText().toString(),
                dataFoto,
                spTujuan.getText().toString(),
                spHost.getText().toString()
        );

        String myCurrentDateTime = DateFormat.getDateTimeInstance()
                .format(Calendar.getInstance().getTime());

        FirebaseDatabase.getInstance().getReference("Visitor")
                .child(myCurrentDateTime).setValue(visitorData).addOnCompleteListener(task -> {

            if (task.isSuccessful()) {

                Intent pindah = new Intent(AddVisitorActivity.this, MainActivity.class);
                startActivity(pindah);
                Toast.makeText(AddVisitorActivity.this, "Data Visitor Tersimpan", Toast.LENGTH_SHORT).show();
                AddVisitorActivity.this.finish();
            }
        }).addOnFailureListener(e -> Toast.makeText(AddVisitorActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}