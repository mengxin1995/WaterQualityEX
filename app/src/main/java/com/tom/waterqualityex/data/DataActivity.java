package com.tom.waterqualityex.data;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.tom.waterqualityex.R;
import com.tom.waterqualityex.global.GlobalConstants;
import com.tom.waterqualityex.global.MyApplication;
import com.tom.waterqualityex.welcome.WelcomeActivity;

public class DataActivity extends AppCompatActivity {

    private static final int JUMP_TO_WELCOME = 1;
    private Handler mHandle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case JUMP_TO_WELCOME:
                    Intent intent = new Intent(MyApplication.getContext(), WelcomeActivity.class);
                    DataActivity.this.startActivity(intent);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        replaceFragment(new RightFragment());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandle.sendEmptyMessageDelayed(JUMP_TO_WELCOME, GlobalConstants.DELAYED_TIME);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mHandle.removeMessages(JUMP_TO_WELCOME);
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                mHandle.removeMessages(JUMP_TO_WELCOME);
                mHandle.sendEmptyMessageDelayed(JUMP_TO_WELCOME, GlobalConstants.DELAYED_TIME);
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    public void sendCancleMessages(){
        mHandle.removeMessages(JUMP_TO_WELCOME);
    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.right_fragment_contain, fragment);
        transaction.commit();
    }
}
