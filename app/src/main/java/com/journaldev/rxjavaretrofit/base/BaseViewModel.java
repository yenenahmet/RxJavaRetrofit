package com.journaldev.rxjavaretrofit.base;

import android.arch.lifecycle.ViewModel;
import android.databinding.ViewDataBinding;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class BaseViewModel extends ViewModel {
    protected final CompositeDisposable disposable = new CompositeDisposable();

    private ViewDataBinding viewDataBinding;

    public void setViewDataBinding(ViewDataBinding viewDataBinding){
        this.viewDataBinding =viewDataBinding;
    }

    protected <T> Observable<T> getObservableConfig(final Observable<T> observable){
        return  observable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
        if(viewDataBinding!=null){
            viewDataBinding.unbind();
            viewDataBinding =null;
        }
    }

}
