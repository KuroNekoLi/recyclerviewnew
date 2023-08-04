package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.btn_show_tabs);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTabsFragment();
            }
        });
    }

    private void showTabsFragment() {
        TabsFragment tabsFragment = new TabsFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, tabsFragment)
                .commit();
        findViewById(R.id.fragment_container).setVisibility(View.VISIBLE);
    }
}
