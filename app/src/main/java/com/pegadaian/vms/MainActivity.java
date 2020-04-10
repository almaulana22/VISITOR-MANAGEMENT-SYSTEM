package com.pegadaian.vms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.tvWelcome)
    TextView txtWelcome;
    @BindView(R.id.cvTambah)
    CardView cardTambah;
    @BindView(R.id.cvCheckin)
    CardView cardCheckin;
    @BindView(R.id.cvCheckout)
    CardView cardCheckout;
    @BindView(R.id.fabVisitor)
    FloatingActionButton fabVis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // MENGAKTIFKAN GREETING
        greeting();
    }

    // SETTING WAKTU GREETING
    private void greeting() {
        Calendar calendar = Calendar.getInstance();
        int timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 0 && timeOfDay < 12) {
            txtWelcome.setText("Selamat pagi.");
        } else if (timeOfDay >= 12 && timeOfDay < 15) {
            txtWelcome.setText("Selamat siang.");
        } else if (timeOfDay >= 15 && timeOfDay < 18) {
            txtWelcome.setText("Selamat sore.");
        } else if (timeOfDay >= 18 && timeOfDay < 24) {
            txtWelcome.setText("Selamat malam.");
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.cvTambah) {
            Intent intentPindah = new Intent(MainActivity.this, AddVisitorActivity.class);
            startActivity(intentPindah);
        } else if (view.getId() == R.id.cvCheckin) {
            Intent intentPindah = new Intent(MainActivity.this, AddVisitorActivity.class);
            startActivity(intentPindah);
        } else if (view.getId() == R.id.cvCheckout) {
            Intent intentPindah = new Intent(MainActivity.this, AddVisitorActivity.class);
            startActivity(intentPindah);
        } else if (view.getId() == R.id.fabVisitor) {
            Intent intentPindah = new Intent(MainActivity.this, VisitorActivity.class);
            startActivity(intentPindah);
        }
    }
}
