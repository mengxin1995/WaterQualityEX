package com.tom.waterqualityex.simple;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.tom.waterqualityex.BaseActivity;
import com.tom.waterqualityex.R;

public class HelpingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helping);

        ImageButton back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
