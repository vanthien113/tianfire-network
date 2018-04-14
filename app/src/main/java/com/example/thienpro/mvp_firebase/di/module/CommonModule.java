package com.example.thienpro.mvp_firebase.di.module;

import com.example.thienpro.mvp_firebase.manager.UserManager;
import com.example.thienpro.mvp_firebase.model.Impl.UserInteractorImpl;
import com.example.thienpro.mvp_firebase.model.LocationInteractor;
import com.example.thienpro.mvp_firebase.model.PostInteractor;
import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.presenter.AppSettingPresenter;
import com.example.thienpro.mvp_firebase.presenter.ChangePasswordPresenter;
import com.example.thienpro.mvp_firebase.presenter.EditInfoPresenter;
import com.example.thienpro.mvp_firebase.presenter.EditPostPresenter;
import com.example.thienpro.mvp_firebase.presenter.FriendProfilePresenter;
import com.example.thienpro.mvp_firebase.presenter.HomePresenter;
import com.example.thienpro.mvp_firebase.presenter.Impl.AppSettingPresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.Impl.ChangePasswordPresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.Impl.EditInfoPresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.Impl.EditPostPresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.Impl.FriendProfilePresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.Impl.HomePresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.Impl.ListLocationPresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.Impl.LoginPresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.Impl.PicturePresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.Impl.PostPresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.Impl.ProfilePresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.Impl.RegisterPresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.Impl.SettingPresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.Impl.UserLocationPresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.Impl.VerifiEmailPresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.ListLocationPresenter;
import com.example.thienpro.mvp_firebase.presenter.LoginPresenter;
import com.example.thienpro.mvp_firebase.presenter.PicturePresenter;
import com.example.thienpro.mvp_firebase.presenter.PostPresenter;
import com.example.thienpro.mvp_firebase.presenter.ProfilePresenter;
import com.example.thienpro.mvp_firebase.presenter.RegisterPresenter;
import com.example.thienpro.mvp_firebase.presenter.SettingPresenter;
import com.example.thienpro.mvp_firebase.presenter.UserLocationPresenter;
import com.example.thienpro.mvp_firebase.presenter.VerifiEmailPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class CommonModule {
    @Provides
    public ProfilePresenter provideProfilePresenter(UserManager userManager, PostInteractor postInteractor, UserInteractor userInteractor) {
        return new ProfilePresenterImpl(userManager, postInteractor, userInteractor);
    }

    @Provides
    public LoginPresenter providesLoginPresenter(UserInteractor userInteractor) {
        return new LoginPresenterImpl(userInteractor);
    }

    @Provides
    public HomePresenter provideHomePresenter(UserInteractor userInteractor, PostInteractor postInteractor) {
        return new HomePresenterImpl(userInteractor, postInteractor);
    }

    @Provides
    public AppSettingPresenter provideAppSettingPresenter(LocationInteractor locationInteractor, UserInteractor userInteractor) {
        return new AppSettingPresenterImpl(locationInteractor, userInteractor);
    }

    @Provides
    public ChangePasswordPresenter provideChangePasswordPresenter(UserInteractorImpl userInteractor) {
        return new ChangePasswordPresenterImpl(userInteractor);
    }

    @Provides
    public EditInfoPresenter provideEditInfoPresenter(UserInteractor userInteractor, UserManager userManager) {
        return new EditInfoPresenterImpl(userInteractor, userManager);
    }

    @Provides
    public EditPostPresenter provideEditPostPresenter(PostInteractor postInteractor) {
        return new EditPostPresenterImpl(postInteractor);
    }

    @Provides
    public FriendProfilePresenter provideFriendProfilePresenter(UserInteractor userInteractor, PostInteractor postInteractor) {
        return new FriendProfilePresenterImpl(userInteractor, postInteractor);
    }

    @Provides
    public ListLocationPresenter provideListLocationPresenter(LocationInteractor locationInteractor) {
        return new ListLocationPresenterImpl(locationInteractor);
    }

    @Provides
    public PicturePresenter providePicturePresenter(PostInteractor postInteractor) {
        return new PicturePresenterImpl(postInteractor);
    }

    @Provides
    public PostPresenter providePostPresenter(PostInteractor postInteractor) {
        return new PostPresenterImpl(postInteractor);
    }

    @Provides
    public RegisterPresenter provideRegistrerPresenter(UserInteractor userInteractor) {
        return new RegisterPresenterImpl(userInteractor);
    }

    @Provides
    public SettingPresenter provideSettingPresenter(UserInteractor userInteractor) {
        return new SettingPresenterImpl(userInteractor);
    }

    @Provides
    public UserLocationPresenter provideUserLocationPresenter(LocationInteractor locationInteractor) {
        return new UserLocationPresenterImpl(locationInteractor);
    }

    @Provides
    public VerifiEmailPresenter provideVerifiEmailPresenter(UserInteractor userInteractor) {
        return new VerifiEmailPresenterImpl(userInteractor);
    }


}
