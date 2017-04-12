package com.tom.waterqualityex;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

import com.tom.waterqualityex.global.GlobalConstants;
import com.tom.waterqualityex.global.MyApplication;
import com.tom.waterqualityex.welcome.WelcomeActivity;

/**
 * Created by mengxin on 17-4-12.
 */

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    private static final int JUMP_TO_WELCOME = 1;
    private Handler mHandle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case JUMP_TO_WELCOME:
                    Intent intent = new Intent(MyApplication.getContext(), WelcomeActivity.class);
                    BaseActivity.this.finish();
                    BaseActivity.this.startActivity(intent);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mHandle.sendEmptyMessageDelayed(JUMP_TO_WELCOME, GlobalConstants.DELAYED_TIME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sendCancleMessages();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onTouchEvent: " + "down");
                mHandle.removeMessages(JUMP_TO_WELCOME);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "onTouchEvent: " + "move");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "onTouchEvent: " + "up");
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

    public void sendMessages(){
        mHandle.sendEmptyMessageDelayed(JUMP_TO_WELCOME, GlobalConstants.DELAYED_TIME);
    }
}
