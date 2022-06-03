package com.whoisnian.atest;

import static androidx.core.view.ViewCompat.generateViewId;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams;
import androidx.constraintlayout.widget.ConstraintSet;

import hello.Hello;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ConstraintLayout layout_main;
    TextView textView_hello;
    TextView textView_counter;
    Button button_counter;
    Button button_date;
    AlertDialog.Builder dialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layout_main = new ConstraintLayout(this);
        textView_hello = new TextView(layout_main.getContext());
        textView_counter = new TextView(layout_main.getContext());
        button_counter = new Button(layout_main.getContext());
        button_date = new Button(layout_main.getContext());
        dialogBuilder = new AlertDialog.Builder(layout_main.getContext());

        textView_hello.setId(generateViewId());
        textView_hello.setText(Hello.greetings(Build.MODEL));

        textView_counter.setId(generateViewId());
        textView_counter.setTextSize(48);
        textView_counter.setTextColor(Color.parseColor("#27AE60"));
        textView_counter.setText("-1");

        button_counter.setId(generateViewId());
        button_counter.setAllCaps(false);
        button_counter.setText("increase by one");
        button_counter.setOnClickListener(this);

        button_date.setId(generateViewId());
        button_date.setAllCaps(false);
        button_date.setText("date");
        button_date.setOnClickListener(this);

        LayoutParams lp_textView_hello = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp_textView_hello.topToTop = ConstraintSet.PARENT_ID;
        lp_textView_hello.bottomToTop = textView_counter.getId();
        lp_textView_hello.leftToLeft = ConstraintSet.PARENT_ID;
        lp_textView_hello.rightToRight = ConstraintSet.PARENT_ID;
        lp_textView_hello.verticalChainStyle = ConstraintSet.CHAIN_PACKED;

        LayoutParams lp_textView_counter = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp_textView_counter.topToBottom = textView_hello.getId();
        lp_textView_counter.bottomToTop = button_counter.getId();
        lp_textView_counter.leftToLeft = ConstraintSet.PARENT_ID;
        lp_textView_counter.rightToRight = ConstraintSet.PARENT_ID;
        lp_textView_counter.verticalChainStyle = ConstraintSet.CHAIN_PACKED;

        LayoutParams lp_button_counter = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp_button_counter.topToBottom = textView_counter.getId();
        lp_button_counter.bottomToBottom = ConstraintSet.PARENT_ID;
        lp_button_counter.leftToLeft = ConstraintSet.PARENT_ID;
        lp_button_counter.rightToRight = ConstraintSet.PARENT_ID;
        lp_button_counter.verticalChainStyle = ConstraintSet.CHAIN_PACKED;

        LayoutParams lp_button_date = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp_button_date.topToTop = ConstraintSet.PARENT_ID;
        lp_button_date.bottomToBottom = ConstraintSet.PARENT_ID;
        lp_button_date.leftToLeft = ConstraintSet.PARENT_ID;
        lp_button_date.rightToRight = ConstraintSet.PARENT_ID;
        lp_button_date.verticalBias = (float) 0.95;
        lp_button_date.horizontalBias = (float) 0.9;

        layout_main.addView(textView_hello, lp_textView_hello);
        layout_main.addView(textView_counter, lp_textView_counter);
        layout_main.addView(button_counter, lp_button_counter);
        layout_main.addView(button_date, lp_button_date);
        setContentView(layout_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        runAndCatchInNewThread(() -> {
            String text = String.valueOf(Hello.getCnt());
            runInUiThread(() -> textView_counter.setText(text));
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == button_counter.getId()) {
            runAndCatchInNewThread(() -> {
                String text = String.valueOf(Hello.incCnt());
                runInUiThread(() -> textView_counter.setText(text));
            });
        } else if (v.getId() == button_date.getId()) {
            runAndCatchInNewThread(() -> {
                String text = Hello.sshExec("date");
                runInUiThread(() -> dialogBuilder.setTitle("� Result �").setMessage(text).show());
            });
        }
    }

    private void runInUiThread(Runnable action) {
        layout_main.post(action);
    }

    private void runAndCatchInNewThread(RunnableWithException action) {
        new Thread(() -> {
            try {
                action.run();
            } catch (Exception e) {
                e.printStackTrace();
                runInUiThread(() -> dialogBuilder.setTitle("⚠ Exception ⚠").setMessage(e.getMessage()).show());
            }
        }).start();
    }

    @FunctionalInterface
    public interface RunnableWithException {
        void run() throws Exception;
    }
}