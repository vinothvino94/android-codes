package com.example.fragmentmy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void bone(View view) {
        FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frid, new BlankFragment()).commit();
    }

    public void btwo(View view) {
        FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frid, new BlankFragment2()).commit();
    }

    public void bthree(View view) {
        FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frid, new BlankFragment3()).commit();
    }
}