package com.github.alexpfx.samples.mobilevision.textrecognizer;

import android.util.Log;

import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;

/**
 * Created by alexandre on 29/05/2017.
 */

class DgValidator implements CodeDetectorProcessor.TextBlockValidator {
    private static final String TAG = "DgValidator";
    @Override
    public boolean isValid(TextBlock textBlock) {
        Log.d(TAG, "isValid: "+textBlock.getLanguage());
        for (Text text : textBlock.getComponents()) {
            Log.d(TAG, "isValid: "+text.getValue());
        }

        String text = textBlock.getValue().replaceAll("\\s+", "");
        if (text.length() != 12) {
            return false;
        }

        return true;
    }
}
