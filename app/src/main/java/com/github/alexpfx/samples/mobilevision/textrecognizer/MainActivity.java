package com.github.alexpfx.samples.mobilevision.textrecognizer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView txtResult;

    Button btnScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnScan = (Button) findViewById(R.id.btn_scan);
        txtResult = (TextView) findViewById(R.id.txt_result);

    }


    public void textScan (View view){
        Intent intent = new Intent(this, ScanTextActivity.class);
        startActivityForResult(intent, 0);

    }

}
