package com.mytunes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mytunes.view.MasterFragment;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new MasterFragment()).commit();
    }

}