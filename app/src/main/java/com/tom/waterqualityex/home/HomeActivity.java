package com.tom.waterqualityex.home;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;

import com.tom.waterqualityex.BaseFragmentActivity;
import com.tom.waterqualityex.R;
import com.tom.waterqualityex.global.GlobalConstants;
import com.tom.waterqualityex.global.MyApplication;
import com.tom.waterqualityex.welcome.WelcomeActivity;

public class HomeActivity extends BaseFragmentActivity {

    private static final int JUMP_TO_WELCOME = 1;
    private Handler mHandle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case JUMP_TO_WELCOME:
                    Intent intent = new Intent(MyApplication.getContext(), WelcomeActivity.class);
                    HomeActivity.this.startActivity(intent);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected Fragment creatFragment() {
        return new HomeFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_home;
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.fragment_container;
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
}
