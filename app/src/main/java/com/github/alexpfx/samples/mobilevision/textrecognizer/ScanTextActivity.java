package com.github.alexpfx.samples.mobilevision.textrecognizer;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ScanTextActivity extends AppCompatActivity {

    private static final String TAG = "ScanTextActivity";
    public static final String DETECTED_TEXT = "detected_text";
    private static final int MULT = 2;

    @BindView(R.id.camera_preview)
    SurfaceView mSurfaceCameraPreview;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.camera_overlay)
    CameraOverlay mCameraOverlay;


    //   836500000010 035101620009 001010201729 909646066052
//               103,51
//


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_text);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
//
        createCameraSource();

    }

    private void createCameraSource() {
        TextRecognizer textRecognizer = new TextRecognizer.Builder(this).build();

        DgCodeProcessorListener processorListener = new DgCodeProcessorListener(this, mCameraOverlay);


        final CameraSource cameraSource = new CameraSource.Builder(this, textRecognizer)
                .setAutoFocusEnabled(true)
                .setRequestedFps(4f)
                .setRequestedPreviewSize(480 * MULT, 270 * MULT)
                .build();







        mSurfaceCameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (ActivityCompat.checkSelfPermission(ScanTextActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ScanTextActivity.this,
                            new String[]{Manifest.permission.CAMERA},
                            20);
                    return;
                }
                try {
                    cameraSource.start(mSurfaceCameraPreview.getHolder());
                    processorListener.setCameraOverlay(mCameraOverlay);
                    mCameraOverlay.setCameraSource(cameraSource);
                    textRecognizer.setProcessor(new CodeDetectorProcessor(new DgValidator(), processorListener));


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Log.d(TAG, "surfaceChanged: ");

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


    }

}
