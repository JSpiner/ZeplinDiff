package net.jspiner.zeplindiff.ui.base;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;

import net.jspiner.zeplindiff.rx.transformer.NetworkTransformer;

import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

public abstract class BasePresenter<T> implements BasePresenterInterface {

    protected T view;
    private BehaviorSubject disposeSubject = BehaviorSubject.create();

    public BasePresenter(T view){
        this.view = view;
    }

    @Override
    public void attachView() {

    }

    @Override
    public void detachView() {
        disposeSubject.onComplete();
    }

    protected <T> LifecycleTransformer<T> disposeTransformer(){
        return RxLifecycle.bind(disposeSubject);
    }

    protected <T> NetworkTransformer<T> networkTransformer(){
        return new NetworkTransformer<>();
    }
}
