package com.github.alexpfx.samples.mobilevision.textrecognizer;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.text.TextBlock;

/**
 * Created by alexandre on 01/06/2017.
 */
class DgCodeProcessorListener implements CodeDetectorProcessor.CodeProcessorListener {
    private ScanTextActivity mScanTextActivity;
    int count = 0;
    TextBlock last = null;
    private CameraOverlay mCameraOverlay;

    public DgCodeProcessorListener(ScanTextActivity scanTextActivity) {
        mScanTextActivity = scanTextActivity;
    }

    public DgCodeProcessorListener(ScanTextActivity scanTextActivity, CameraOverlay cameraOverlay) {
        mScanTextActivity = scanTextActivity;
        mCameraOverlay = cameraOverlay;
    }

    public void setCameraOverlay(CameraOverlay cameraOverlay) {
        mCameraOverlay = cameraOverlay;
    }

    private static final String TAG = "DgCodeProcessorListener";
    @Override
    public void onReceiveDetection(TextBlock item) {
        if (mCameraOverlay != null){
            Log.d(TAG, "onReceiveDetection: "+item.getBoundingBox());
            mCameraOverlay.setPosition(item.getBoundingBox());
        }

        if (last != null && item.getValue().equals(last.getValue())) {
            count++;
        } else {

            count = 1;
        }
        last = item;
        if (count > 2) {
//            deliveryResult(item);
        }

    }

    private void deliveryResult(TextBlock item) {
        Intent intent = new Intent();
        intent.putExtra(ScanTextActivity.DETECTED_TEXT, item.getValue());
        mScanTextActivity.setResult(CommonStatusCodes.SUCCESS, intent);
        mScanTextActivity.finish();
    }
}
