package com.luo.ming.delicipe.Presenters;

import com.luo.ming.delicipe.Models.User;

public class TabbedActivityPresenter {

    private View view;

    public TabbedActivityPresenter(View view) {
        this.view = view;
    }

    public void updateUiFromIntent(User user) {

        view.setNavHeaderImg(user.getPhotoUrl());
        view.setNavHeaderUserName(user.getDisplayName());
        view.setNavHeaderEmail(user.getEmail());

    }

    public interface View{
        void setNavHeaderImg(String url);
        void setNavHeaderUserName(String name);
        void setNavHeaderEmail(String email);
    }
}
