package net.jspiner.zeplindiff.ui.project;

import net.jspiner.zeplindiff.model.Project;
import net.jspiner.zeplindiff.ui.base.BasePresenterInterface;
import net.jspiner.zeplindiff.ui.base.BaseViewInterface;

import java.util.ArrayList;

public interface Contract {

    interface View extends BaseViewInterface {
        void addProjectList(ArrayList<Project> projectList);
    }

    interface Presenter extends BasePresenterInterface {
        void requestProjectList();
    }
}
