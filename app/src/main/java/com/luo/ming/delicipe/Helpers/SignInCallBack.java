package com.luo.ming.delicipe.Helpers;

import com.luo.ming.delicipe.Models.User;

public interface SignInCallBack {

    void onSuccess(User user);
    void onFailure(String exeception);
}
