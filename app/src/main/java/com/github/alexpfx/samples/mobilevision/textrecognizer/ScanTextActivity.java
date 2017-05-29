package com.github.alexpfx.samples.mobilevision.textrecognizer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;


public class ScanTextActivity extends AppCompatActivity {

    private static final String TAG = "ScanTextActivity";
    SurfaceView cameraPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_text);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cameraPreview = (SurfaceView) findViewById(R.id.camera_preview);

        createCameraSource();

    }

    private void createCameraSource() {
        TextRecognizer textRecognizer = new TextRecognizer.Builder(this).build();

        final CameraSource cameraSource = new CameraSource.Builder(this, textRecognizer)
                .setAutoFocusEnabled(true)
                .setRequestedFps(2)
                .setRequestedPreviewSize(1200, 1024)
                .build();

        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (ActivityCompat.checkSelfPermission(ScanTextActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ScanTextActivity.this,
                            new String[]{Manifest.permission.CAMERA},
                            20);

                    return;
                }
                try {
                    cameraSource.start(cameraPreview.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<TextBlock> detections) {
                SparseArray<TextBlock> detectedItems = detections.getDetectedItems();
                if (detectedItems.size() > 0){
//                    Intent intent = new Intent();
//                    intent.putExtra("orc", detectedItems.valueAt(0).getValue());
//                    setResult(CommonStatusCodes.SUCCESS, intent);

                    for (int i = 0; i < detectedItems.size(); i++) {
                        Log.d(TAG, "receiveDetections: "+detectedItems.valueAt(i).getValue());
                    }
                }



            }
        });

    }

}
