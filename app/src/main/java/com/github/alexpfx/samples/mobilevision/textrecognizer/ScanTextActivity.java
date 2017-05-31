package com.github.alexpfx.samples.mobilevision.textrecognizer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;


public class ScanTextActivity extends AppCompatActivity {

    private static final String TAG = "ScanTextActivity";
    public static final String DETECTED_TEXT = "detected_text";
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

        textRecognizer.setProcessor(new CodeDetectorProcessor(new IsbnValidator(), new CodeDetectorProcessor.CodeProcessorListener() {
            @Override
            public void onReceiveDetection(TextBlock item) {
                Log.d(TAG, "onReceiveDetection: "+item.getValue());
                Intent intent = new Intent();
                intent.putExtra(DETECTED_TEXT, item.getValue());
                setResult(CommonStatusCodes.SUCCESS, intent);
                finish();
            }
        }));

        final CameraSource cameraSource = new CameraSource.Builder(this, textRecognizer)
                .setAutoFocusEnabled(true)
                .setRequestedFps(10f)
                .setRequestedPreviewSize(640, 480)
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



    }

}
