package com.whoisnian.atest;

import static androidx.core.view.ViewCompat.generateViewId;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams;
import androidx.constraintlayout.widget.ConstraintSet;

import hello.Hello;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ConstraintLayout layout_main = new ConstraintLayout(this);
        TextView textView_hello = new TextView(layout_main.getContext());
        textView_hello.setId(generateViewId());
        textView_hello.setText(Hello.greetings(Build.MODEL));
        Button button_count = new Button(layout_main.getContext());
        button_count.setId(generateViewId());
        button_count.setAllCaps(false);
        button_count.setText("https://counter.whoisnian.workers.dev/cnt\n-1");

        LayoutParams layoutParams_hello = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams_hello.topToTop = ConstraintSet.PARENT_ID;
        layoutParams_hello.bottomToTop = button_count.getId();
        layoutParams_hello.leftToLeft = ConstraintSet.PARENT_ID;
        layoutParams_hello.rightToRight = ConstraintSet.PARENT_ID;
        layoutParams_hello.verticalChainStyle = ConstraintSet.CHAIN_PACKED;

        LayoutParams layoutParams_count = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams_count.topToBottom = textView_hello.getId();
        layoutParams_count.bottomToBottom = ConstraintSet.PARENT_ID;
        layoutParams_count.leftToLeft = ConstraintSet.PARENT_ID;
        layoutParams_count.rightToRight = ConstraintSet.PARENT_ID;
        layoutParams_count.verticalChainStyle = ConstraintSet.CHAIN_PACKED;

        layout_main.addView(textView_hello, layoutParams_hello);
        layout_main.addView(button_count, layoutParams_count);
        setContentView(layout_main);
    }
}