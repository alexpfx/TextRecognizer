package com.github.alexpfx.samples.mobilevision.textrecognizer;

import android.util.SparseArray;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;

/**
 * Created by alexandre on 29/05/2017.
 */

public class CodeDetectorProcessor implements com.google.android.gms.vision.Detector.Processor<com.google.android.gms.vision.text.TextBlock> {

    private CodeProcessorListener mProcessorListener = p -> {
    };
    private TextBlockValidator mTextBlockValidator = p -> true;


    public CodeDetectorProcessor() {

    }


    public CodeDetectorProcessor(TextBlockValidator textBlockValidator, CodeProcessorListener processorListener) {
        mProcessorListener = processorListener;
        mTextBlockValidator = textBlockValidator;
    }


    @Override
    public void release() {

    }

    @Override
    public void receiveDetections(Detector.Detections<TextBlock> detections) {
        SparseArray<TextBlock> items = detections.getDetectedItems();
        for (int i = 0; i < items.size(); i++) {
            TextBlock textBlock = items.get(i);
            if (textBlock == null || textBlock.getValue() == null) {
                continue;
            }
            if (mTextBlockValidator.isValid(textBlock)) {
                mProcessorListener.onReceiveDetection(textBlock);
            }
        }

    }

    public void setProcessorListener(CodeProcessorListener processorListener) {
        mProcessorListener = processorListener;
    }

    public void setTextBlockValidator(TextBlockValidator textBlockValidator) {
        mTextBlockValidator = textBlockValidator;
    }

    public interface TextBlockValidator {
        boolean isValid(TextBlock item);

    }

    public interface CodeProcessorListener {
        void onReceiveDetection(TextBlock item);

    }
}
