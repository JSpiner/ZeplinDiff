package net.jspiner.zeplindiff.ui.base;

public abstract class BasePresenter<T> implements BasePresenterInterface {

    protected T view;

    public BasePresenter(T view){
        this.view = view;
    }

}
