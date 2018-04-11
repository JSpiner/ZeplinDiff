package net.jspiner.zeplindiff.ui.project;

import net.jspiner.zeplindiff.utils.KeyManager;
import net.jspiner.zeplindiff.api.Api;
import net.jspiner.zeplindiff.ui.base.BasePresenter;

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
                .compose(networkTransformer())
                .compose(disposeTransformer())
                .subscribe(
                        projectModel -> view.addProjectList(projectModel.projects)
                );
    }
}
