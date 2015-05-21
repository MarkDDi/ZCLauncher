package com.word.wordinsidehome.service.image;

public abstract class AjaxCallBack {
    private boolean progress;
    private int rate;

    public AjaxCallBack() {
        super();
        this.progress = true;
        this.rate = 0x3E8; // 1000
    }

    public int getRate() {
        return this.rate;
    }

    public boolean isProgress() {
        return this.progress;
    }

    public void onFailure(Throwable t, int errorNo, String strMsg) {
    }

    public void onLoading(long count, long current) {
    }

    public void onStart() {
    }

    public void onSuccess(Object arg1) {
    }

    public AjaxCallBack progress(boolean progress, int rate) {
        this.progress = progress;
        this.rate = rate;
        return this;
    }
}

