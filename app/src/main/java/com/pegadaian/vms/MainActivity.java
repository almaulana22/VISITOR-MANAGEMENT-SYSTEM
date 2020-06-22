package com.pegadaian.vms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.tvWelcome) TextView txtWelcome;
    @BindView(R.id.cvTambah) CardView cardTambah;
    @BindView(R.id.cvCheckin) CardView cardCheckin;
    @BindView(R.id.cvCheckout) CardView cardCheckout;
    @BindView(R.id.fabVisitor) FloatingActionButton fabVis;

    boolean doubleBack = false;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);

        // CUSTOM TOAST
        Toasty.Config.getInstance()
                .setToastTypeface(ResourcesCompat.getFont(this, R.font.nexa_bold))
                .setTextSize(15)
                .apply();

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
    public void onClick(View v) {
        if (v == cardTambah) {
            startActivity(new Intent(MainActivity.this, AddVisitorActivity.class));
            finish();
        } else if (v == cardCheckin) {
            startActivity(new Intent(MainActivity.this, CheckActivity.class)
                    .putExtra("requestCode", 1));
        } else if (v == cardCheckout) {
            startActivity(new Intent(MainActivity.this, CheckActivity.class)
                    .putExtra("requestCode", 2));
        } else if (v == fabVis) {
            startActivity(new Intent(MainActivity.this, VisitorActivity.class));
            finish();
        }
    }

    public void onBackPressed() {

        if (doubleBack) {
            super.onBackPressed();
            return;
        }

        this.doubleBack = true;
        Toasty.warning(MainActivity.this, "Tekan sekali lagi untuk keluar",
                Toast.LENGTH_SHORT, true).show();

        new Handler().postDelayed(() -> doubleBack = false, 2000);
    }
}
