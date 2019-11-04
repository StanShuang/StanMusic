package com.stan.music.base;

/**
 * Author: Stan
 * Date: 2019/11/4 17:41
 * Description: ${DESCRIPTION}
 */
public class BasePresenter<V extends BaseView, M extends BaseModel> {
    protected V mView;
    protected M mModel;

    public void unSubscribe() {
        if (mView != null) {
            mView = null;
        }
    }


}
