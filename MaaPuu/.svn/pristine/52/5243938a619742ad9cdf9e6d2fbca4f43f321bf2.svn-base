package com.maapuu.mereca.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.maapuu.mereca.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LanuchActivity extends Activity {
    @BindView(R.id.mRoot)
    LinearLayout mRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lanuch);
        ButterKnife.bind(this);
        goToMain();
    }

    private void goToMain(){
        mRoot.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent it = new Intent(LanuchActivity.this,MainActivity.class);
                startActivity(it);
                finish();
            }
        },2000);
    }
}
