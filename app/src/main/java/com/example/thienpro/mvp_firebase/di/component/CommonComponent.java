package com.example.thienpro.mvp_firebase.di.component;

import com.example.thienpro.mvp_firebase.di.module.CommonModule;
import com.example.thienpro.mvp_firebase.presenter.ChangePasswordPresenter;
import com.example.thienpro.mvp_firebase.presenter.EditInfoPresenter;
import com.example.thienpro.mvp_firebase.presenter.EditPostPresenter;
import com.example.thienpro.mvp_firebase.presenter.FriendProfilePresenter;
import com.example.thienpro.mvp_firebase.presenter.HomePresenter;
import com.example.thienpro.mvp_firebase.presenter.LoginPresenter;
import com.example.thienpro.mvp_firebase.presenter.PicturePresenter;
import com.example.thienpro.mvp_firebase.presenter.PostPresenter;
import com.example.thienpro.mvp_firebase.presenter.ProfilePresenter;
import com.example.thienpro.mvp_firebase.presenter.RegisterPresenter;
import com.example.thienpro.mvp_firebase.presenter.SearchUserPresenter;
import com.example.thienpro.mvp_firebase.presenter.SettingPresenter;
import com.example.thienpro.mvp_firebase.presenter.ShareLocationPresenter;
import com.example.thienpro.mvp_firebase.presenter.UserLocationPresenter;
import com.example.thienpro.mvp_firebase.presenter.VerifiEmailPresenter;

import dagger.Subcomponent;

/**
 * Created by TranHuuPhuc on 4/4/18.
 */
@Subcomponent(modules = CommonModule.class)
public interface CommonComponent {
    ProfilePresenter getProfilePresenter();

    LoginPresenter getLoginPresenter();

    HomePresenter getHomePresenter();

    ShareLocationPresenter getAppSettingPresenter();

    ChangePasswordPresenter getChangePasswordPresenter();

    EditInfoPresenter getEditInfoPresenter();

    EditPostPresenter getEditPostPresenter();

    FriendProfilePresenter getFriendProfilePresenter();

    PicturePresenter getPicturePresenter();

    PostPresenter getPostPresenter();

    RegisterPresenter getRegistrerPresenter();

    SettingPresenter getSettingPresenter();

    UserLocationPresenter getUserLocationPresenter();

    VerifiEmailPresenter getVerifiEmailPresenter();

    SearchUserPresenter getSearchUserPresenter();
}
