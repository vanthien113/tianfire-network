package com.example.thienpro.mvp_firebase.presenter.Impl;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.esafirm.imagepicker.model.Image;
import com.example.thienpro.mvp_firebase.model.Impl.PostInteractorImpl;
import com.example.thienpro.mvp_firebase.model.Impl.UserInteractorImpl;
import com.example.thienpro.mvp_firebase.model.PostInteractor;
import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.presenter.ProfilePresenter;
import com.example.thienpro.mvp_firebase.ultils.DownloadUltil;
import com.example.thienpro.mvp_firebase.view.ProfileView;
import com.example.thienpro.mvp_firebase.view.bases.BasePresentermpl;
import com.example.thienpro.mvp_firebase.view.fragment.ProfileFragment;
import com.google.firebase.database.DatabaseError;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * - Presenter: Là lớp xử lý logic từ dữ liệu nhận được.
 * - Nhận dữ liệu từ lớp Model
 * - Đẩy dữ liệu lên lớp V.
 */

/**
 * Created by ThienPro on 11/16/2017.
 */

public class ProfilePresenterImpl extends BasePresentermpl<ProfileView> implements ProfilePresenter {
    public static final String TAG = "ProfilePresenterImpl";
    private PostInteractor postInteractor;
    private UserInteractor userInteractor;
    private Context context;

    public ProfilePresenterImpl(Context context) {
        this.context = context;
        postInteractor = new PostInteractorImpl();
        userInteractor = new UserInteractorImpl(context);
    }

    public void loadPost() {
        getView().showLoadingDialog();
        postInteractor.loadPersonalPost(new PostInteractor.ListPostCallback() {
            @Override
            public void listPost(DatabaseError e, ArrayList<Post> listPost) {
                getView().hideLoadingDialog();

                if (e == null) {
                    getView().showList(listPost);
                } else {
                    getView().showDatabaseError(e);
                }
            }
        });
    }

    @Override
    public void getUser() {
        getView().showLoadingDialog();
        userInteractor.getUser(new UserInteractor.GetUserCallback() {
            @Override
            public void getUser(DatabaseError error, User user) {
                getView().hideLoadingDialog();
                if (error != null) {
                    getView().showDatabaseError(error);
                } else {
                    userInteractor.saveCurrentLocalUser(user);
                    getView().showUser(user);
                }
            }
        }, true);
    }

    @Override
    public void deletePost(Post post) {
        getView().showLoadingDialog();
        postInteractor.deletePost(post, new PostInteractor.DeletePostCallback() {
            @Override
            public void listPost(Exception e) {
                getView().hideLoadingDialog();
                if (e != null) {
                    getView().showExceptionError(e);
                } else {
                    getView().reloadPost();
                    getView().showDeleteComplete();
                }
            }
        });
    }

    @Override
    public void searchUser(String userName) {
        userInteractor.searchUser(userName, new UserInteractor.SearchUserCallBack() {
            @Override
            public void onFinish(DatabaseError e, ArrayList<User> list) {
                if (e != null) {
                    getView().showDatabaseError(e);
                } else {
                    getView().showSearchUser(list);
                }
            }
        });
    }

    @Override
    public void downloadImage(String imageUrl) {
        DownloadUltil.startDownload(context, imageUrl);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ProfileFragment.REQUEST_CHANGE_AVATAR && resultCode == RESULT_OK && data != null) {
            List<Image> images = data.getParcelableArrayListExtra("selectedImages");
            if (images != null && images.size() > 0) {
                Image image = images.get(0);

                changeAvatar(Uri.fromFile(new File(image.getPath())));
            }
            return;
        }
        if (requestCode == ProfileFragment.REQUEST_CHANGE_COVER && resultCode == RESULT_OK && data != null) {
            List<Image> images = data.getParcelableArrayListExtra("selectedImages");
            if (images != null && images.size() > 0) {
                Image image = images.get(0);

                changeCover(Uri.fromFile(new File(image.getPath())));
            }
        }
    }

    private void changeAvatar(final Uri uri) {
        getView().showLoading();
        userInteractor.addAvatar(uri, new UserInteractor.AddAvatarCallback() {
            @Override
            public void addAvatar(Exception e, String uri) {
                getView().hideLoading();
                if (e != null) {
                    getView().showExceptionError(e);
                } else {
                    userInteractor.saveCurrentLocalUser(new User(null, null, null, null, null, uri, null));
                    getView().showChangeComplete();
                    getView().showAvatarChanged(uri);
                    getView().reloadPost();
                }
            }
        });
    }

    private void changeCover(final Uri uri) {
        getView().showLoading();
        userInteractor.addCover(uri, new UserInteractor.AddCoverCallback() {
            @Override
            public void addCover(Exception e, String uri) {
                getView().hideLoading();
                if (e != null) {
                    getView().showExceptionError(e);
                } else {
                    userInteractor.saveCurrentLocalUser(new User(null, null, null, null, null, null, uri));
                    getView().showCoverChanged(uri);
                    getView().showChangeComplete();
                }
            }
        });
    }
}
