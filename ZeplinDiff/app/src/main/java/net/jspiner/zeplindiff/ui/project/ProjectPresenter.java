package net.jspiner.zeplindiff.ui.project;

import net.jspiner.zeplindiff.KeyManager;
import net.jspiner.zeplindiff.api.Api;
import net.jspiner.zeplindiff.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProjectPresenter extends BasePresenter<Contract.View> implements Contract.Presenter {

    public ProjectPresenter(Contract.View view) {
        super(view);
    }

    @Override
    public void attachView() {
        requestProjectList();
    }

    @Override
    public void requestProjectList() {
        Api.getService().getProjectList(KeyManager.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        projectModel -> view.addProjectList(projectModel.projects)
                );
    }
}
