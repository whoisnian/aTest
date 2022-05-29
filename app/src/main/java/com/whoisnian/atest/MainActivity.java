package com.whoisnian.atest;

import static androidx.core.view.ViewCompat.generateViewId;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams;
import androidx.constraintlayout.widget.ConstraintSet;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import hello.Hello;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ConstraintLayout layout_main;
    TextView textView_hello;
    TextView textView_counter;
    Button button_counter;
    AlertDialog.Builder dialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layout_main = new ConstraintLayout(this);
        textView_hello = new TextView(layout_main.getContext());
        textView_counter = new TextView(layout_main.getContext());
        button_counter = new Button(layout_main.getContext());
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

        layout_main.addView(textView_hello, lp_textView_hello);
        layout_main.addView(textView_counter, lp_textView_counter);
        layout_main.addView(button_counter, lp_button_counter);
        setContentView(layout_main);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("FirebaseMessaging", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Log and toast
                        String msg = "on Complete " + task.getResult();
                        Log.d("FirebaseMessaging", msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(() -> {
            try {
                String text = String.valueOf(Hello.getCnt());
                layout_main.post(() -> textView_counter.setText(text));
            } catch (Exception e) {
                e.printStackTrace();
                layout_main.post(() -> dialogBuilder.setTitle("⚠ Exception ⚠").setMessage(e.getMessage()).show());
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        new Thread(() -> {
            try {
                String text = String.valueOf(Hello.incCnt());
                layout_main.post(() -> textView_counter.setText(text));
            } catch (Exception e) {
                e.printStackTrace();
                layout_main.post(() -> dialogBuilder.setTitle("⚠ Exception ⚠").setMessage(e.getMessage()).show());
            }
        }).start();
    }
}