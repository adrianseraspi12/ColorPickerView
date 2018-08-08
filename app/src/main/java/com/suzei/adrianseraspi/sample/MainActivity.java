package com.suzei.adrianseraspi.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.adrianseraspi.colorpickerview.ColorPickerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ColorPickerView pickerView = findViewById(R.id.color);
        pickerView.setOnColorPickListener(new ColorPickerView.PickColorListener() {

            @Override
            public void onColorPick(String coloredPickHex) {
                Toast.makeText(MainActivity.this, coloredPickHex, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
