package net.jspiner.zeplindiff.ui.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.subjects.BehaviorSubject;

public abstract class BaseActivity<B extends ViewDataBinding, P extends BasePresenterInterface> extends AppCompatActivity {

    protected P presenter;
    protected B binding;

    private BehaviorSubject disposeSubject = BehaviorSubject.create();

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract P createPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());
        presenter = createPresenter();
        presenter.attachView();
    }

    @Override
    public void setContentView(int layoutResID) {
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        binding = DataBindingUtil.bind(view);
        super.setContentView(view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        disposeSubject.onComplete();
    }

    public <T> LifecycleTransformer<T> disposeTransformer() {
        return RxLifecycle.bind(disposeSubject);
    }
}
