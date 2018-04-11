package net.jspiner.zeplindiff.ui.screen;

import net.jspiner.zeplindiff.utils.KeyManager;
import net.jspiner.zeplindiff.api.Api;
import net.jspiner.zeplindiff.ui.base.BasePresenter;

public class ScreenPresenter extends BasePresenter<Contract.View> implements Contract.Presenter {

    public ScreenPresenter(Contract.View view) {
        super(view);
    }

    @Override
    public void attachView() {
        requestScreenList();
    }

    @Override
    public void requestScreenList() {
        Api.getService().getScreenList(KeyManager.getToken(), view.getProjectId())
                .compose(networkTransformer())
                .compose(disposeTransformer())
                .subscribe(screenModel -> view.addScreenList(screenModel.screens));
    }
}
