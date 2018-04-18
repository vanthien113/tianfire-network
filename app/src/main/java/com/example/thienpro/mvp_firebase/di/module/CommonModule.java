package com.example.thienpro.mvp_firebase.di.module;

import com.example.thienpro.mvp_firebase.manager.PostManager;
import com.example.thienpro.mvp_firebase.manager.UserManager;
import com.example.thienpro.mvp_firebase.model.LocationInteractor;
import com.example.thienpro.mvp_firebase.model.PostInteractor;
import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.presenter.ChangePasswordPresenter;
import com.example.thienpro.mvp_firebase.presenter.EditInfoPresenter;
import com.example.thienpro.mvp_firebase.presenter.EditPostPresenter;
import com.example.thienpro.mvp_firebase.presenter.FriendProfilePresenter;
import com.example.thienpro.mvp_firebase.presenter.HomePresenter;
import com.example.thienpro.mvp_firebase.presenter.Impl.ChangePasswordPresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.Impl.EditInfoPresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.Impl.EditPostPresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.Impl.FriendProfilePresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.Impl.HomePresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.Impl.LoginPresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.Impl.PicturePresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.Impl.PostPresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.Impl.ProfilePresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.Impl.RegisterPresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.Impl.SearchUserPresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.Impl.SettingPresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.Impl.ShareLocationPresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.Impl.UserLocationPresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.Impl.VerifiEmailPresenterImpl;
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

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
@Singleton
public class CommonModule {
    @Provides
    public ProfilePresenter providesProfilePresenter(UserManager userManager, PostInteractor postInteractor, UserInteractor userInteractor, PostManager postManager) {
        return new ProfilePresenterImpl(userManager, postInteractor, userInteractor, postManager);
    }

    @Provides
    public LoginPresenter providesLoginPresenter(UserInteractor userInteractor) {
        return new LoginPresenterImpl(userInteractor);
    }

    @Provides
    public HomePresenter providesHomePresenter(UserInteractor userInteractor, PostInteractor postInteractor, PostManager postManager) {
        return new HomePresenterImpl(userInteractor, postInteractor, postManager);
    }

    @Provides
    public ShareLocationPresenter providesAppSettingPresenter(LocationInteractor locationInteractor, UserManager userManager) {
        return new ShareLocationPresenterImpl(locationInteractor, userManager);
    }

    @Provides
    public ChangePasswordPresenter providesChangePasswordPresenter(UserInteractor userInteractor) {
        return new ChangePasswordPresenterImpl(userInteractor);
    }

    @Provides
    public EditInfoPresenter providesEditInfoPresenter(UserInteractor userInteractor, UserManager userManager) {
        return new EditInfoPresenterImpl(userInteractor, userManager);
    }

    @Provides
    public EditPostPresenter providesEditPostPresenter(PostInteractor postInteractor) {
        return new EditPostPresenterImpl(postInteractor);
    }

    @Provides
    public FriendProfilePresenter provideFriendProfilePresenter(UserInteractor userInteractor, PostInteractor postInteractor) {
        return new FriendProfilePresenterImpl(userInteractor, postInteractor);
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
    public SettingPresenter provideSettingPresenter(UserInteractor userInteractor, UserManager userManager) {
        return new SettingPresenterImpl(userInteractor, userManager);
    }

    @Provides
    public UserLocationPresenter provideUserLocationPresenter(LocationInteractor locationInteractor) {
        return new UserLocationPresenterImpl(locationInteractor);
    }

    @Provides
    public VerifiEmailPresenter provideVerifiEmailPresenter(UserInteractor userInteractor) {
        return new VerifiEmailPresenterImpl(userInteractor);
    }

    @Provides
    public SearchUserPresenter provideSearchUserPresenter(UserInteractor userInteractor) {
        return new SearchUserPresenterImpl(userInteractor);
    }
}
