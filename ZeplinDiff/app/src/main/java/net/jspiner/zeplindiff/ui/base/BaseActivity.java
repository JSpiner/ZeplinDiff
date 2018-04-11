package net.jspiner.zeplindiff.ui.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

public abstract class BaseActivity<B extends ViewDataBinding, P extends BasePresenterInterface> extends AppCompatActivity {

    protected P presenter;
    protected B binding;

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
    }
}
