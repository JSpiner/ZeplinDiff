package net.jspiner.zeplindiff.ui.screen;

import net.jspiner.zeplindiff.model.Project;
import net.jspiner.zeplindiff.model.Screen;
import net.jspiner.zeplindiff.ui.base.BasePresenterInterface;
import net.jspiner.zeplindiff.ui.base.BaseViewInterface;

import java.util.ArrayList;

public interface Contract {

    interface View extends BaseViewInterface {
        void addScreenList(ArrayList<Screen> screenList);

        String getProjectId();
    }

    interface Presenter extends BasePresenterInterface {
        void requestScreenList();
    }
}
