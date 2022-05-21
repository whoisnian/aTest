package com.whoisnian.atest;

import android.os.Bundle;
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
        textView_hello.setText(Hello.greetings("World"));

        LayoutParams layoutParams_hello = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams_hello.bottomToBottom = ConstraintSet.PARENT_ID;
        layoutParams_hello.endToEnd = ConstraintSet.PARENT_ID;
        layoutParams_hello.startToStart = ConstraintSet.PARENT_ID;
        layoutParams_hello.topToTop = ConstraintSet.PARENT_ID;

        layout_main.addView(textView_hello, layoutParams_hello);
        setContentView(layout_main);
    }
}