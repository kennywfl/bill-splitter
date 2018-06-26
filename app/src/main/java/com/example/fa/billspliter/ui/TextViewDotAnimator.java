package com.example.fa.billspliter.ui;

import android.os.Handler;
import android.widget.TextView;

public class TextViewDotAnimator implements Runnable {

    private final TextView textView;
    private String originalText;
    private Handler viewHandler;

    private int dotCount = 0;

    TextViewDotAnimator(TextView textView, Handler viewHandler) {
        this.textView = textView;
        this.originalText = textView.getText().toString();
        this.viewHandler = viewHandler;
    }

    @Override
    public void run() {
        StringBuilder stringBuilder = new StringBuilder(originalText);
        for (int i = 0; i < dotCount; i++) {
            stringBuilder.append(".");
        }
        textView.setText(stringBuilder.toString());
        if (viewHandler != null) {
            viewHandler.postDelayed(this, 500);
        }

        dotCount++;
        if (dotCount > 6) {
            dotCount = 0;
        }
    }
}