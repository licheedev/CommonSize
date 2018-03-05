package com.licheedev.commonsize;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvSw = (TextView) findViewById(R.id.tv_sw);

        Configuration config = getResources().getConfiguration();
        int smallestScreenWidthDp = config.smallestScreenWidthDp;
        tvSw.setText(String.format("match_parent, smallest width is %d", smallestScreenWidthDp));
        System.out.println(smallestScreenWidthDp);
    }
}
