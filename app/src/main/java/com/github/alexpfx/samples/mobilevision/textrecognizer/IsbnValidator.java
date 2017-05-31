package com.github.alexpfx.samples.mobilevision.textrecognizer;

import com.google.android.gms.vision.text.TextBlock;

/**
 * Created by alexandre on 29/05/2017.
 */

class IsbnValidator implements CodeDetectorProcessor.TextBlockValidator {
    @Override
    public boolean isValid(TextBlock item) {
        if (item.getValue().replaceAll("\\s+", "").length() != 12) {
            return false;
        }
        return true;
    }
}
