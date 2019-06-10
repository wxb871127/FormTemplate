package com.business.callback;

public interface CommandCallback {
    void onFailed(String msg);
    void onSuccess(Object object);
}
