package com.tom.waterqualityex;

/**
 * Created by mengxin on 17-3-16.
 */

public interface BasePresenter {
    //与activity或fragment生命周期同步
    void start();

    void destroy();
}
