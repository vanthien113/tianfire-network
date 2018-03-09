package com.example.thienpro.mvp_firebase.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.FragmentProfileBinding;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.presenter.Impl.ProfilePresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.ProfilePresenter;
import com.example.thienpro.mvp_firebase.ultils.DownloadUltil;
import com.example.thienpro.mvp_firebase.view.ProfileView;
import com.example.thienpro.mvp_firebase.view.activity.EditPostActivity;
import com.example.thienpro.mvp_firebase.view.activity.FriendProfileActivity;
import com.example.thienpro.mvp_firebase.view.activity.PictureActivity;
import com.example.thienpro.mvp_firebase.view.activity.PostActivity;
import com.example.thienpro.mvp_firebase.view.adapters.HomeAdapter;
import com.example.thienpro.mvp_firebase.view.bases.BaseFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ThienPro on 11/22/2017.
 */

public class ProfileFragment extends BaseFragment<FragmentProfileBinding> implements ProfileView, HomeAdapter.ListPostMenuListener, HomeAdapter.DownloadImageListener, HomeAdapter.FriendProfileListener {
    private static final int REQUEST_CHANGE_AVATAR = 1;
    private static final int REQUEST_CHANGE_COVER = 2;

    private HomeAdapter homeAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private ProfilePresenter presenter;
    private ArrayList<Post> listPost;

    public static ProfileFragment newInstance() {
        Bundle args = new Bundle();
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void init(@Nullable View view) {
        presenter = new ProfilePresenterImpl(getContext());
        presenter.attachView(this);

        mLinearLayoutManager = new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false);

        viewDataBinding.rvProfile.setLayoutManager(mLinearLayoutManager);
        viewDataBinding.rvProfile.setNestedScrollingEnabled(false);

        presenter.loadPost();
        presenter.getUser();

        viewDataBinding.setEvent(this);
    }

    @Override
    public void onResume() {
        loadData();
        super.onResume();
    }

    @Override
    protected void attach(Context context) {

    }

    public void loadData() {
        if (listPost != null) {
            viewDataBinding.rvProfile.setLayoutFrozen(true);
            listPost.clear();
            presenter.loadPost();
            viewDataBinding.rvProfile.setLayoutFrozen(false);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) { // Hàm sẽ được chạy sau khi ấn sang tab Home
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            loadData();
        }
    }

    @Override
    public void onPost() {
        PostActivity.startActivity(getContext());
    }

    @Override
    public void showList(ArrayList<Post> list) {
        Collections.reverse(list);
        listPost = list;
        homeAdapter = new HomeAdapter(listPost, getContext(), this, null, this, this);
        viewDataBinding.rvProfile.setAdapter(homeAdapter);
        viewDataBinding.rvProfile.setLayoutFrozen(false);
    }

    @Override
    public void showUser(User user) {
        showAvatarChanged(user.getAvatar());
        showCoverChanged(user.getCover());

        viewDataBinding.tvName.setText(user.getName());
    }

    @Override
    public void onChangeAvatar() {
        ImagePicker.create(this)
                .returnAfterFirst(true)
                .imageTitle("Tap to select")
                .showCamera(true)
                .single()
                .imageDirectory("Camera")
                .start(REQUEST_CHANGE_AVATAR);
    }

    @Override
    public void onChangeCover() {
        ImagePicker.create(this)
                .returnAfterFirst(true)
                .imageTitle("Tap to select")
                .showCamera(true)
                .single()
                .imageDirectory("Camera")
                .start(REQUEST_CHANGE_COVER);
    }

    @Override
    public void reloadPost() {
        loadData();
    }

    @Override
    public void showAvatarChanged(String avatarUrl) {
        Glide.with(viewDataBinding.getRoot().getContext())
                .load(avatarUrl)
                .asBitmap().centerCrop()
                .into(new BitmapImageViewTarget(viewDataBinding.ivAvatar) {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(viewDataBinding.getRoot().getResources(), resource);
                        roundedBitmapDrawable.setCircular(true);
                        viewDataBinding.ivAvatar.setImageDrawable(roundedBitmapDrawable);
                    }
                });
    }

    @Override
    public void showCoverChanged(String coverUrl) {
        Glide.with(viewDataBinding.getRoot().getContext())
                .load(coverUrl)
                .asBitmap().centerCrop()
                .into(new BitmapImageViewTarget(viewDataBinding.ivCover) {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    protected void setResource(Bitmap resource) {
                        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), resource);

                        viewDataBinding.llCover.setBackground(bitmapDrawable);
                    }
                });
    }

    @Override
    public void onShowListPictureClick() {
        PictureActivity.startActivity(getContext(), null);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CHANGE_AVATAR && resultCode == RESULT_OK && data != null) {
            List<Image> images = data.getParcelableArrayListExtra("selectedImages");
            if (images != null && images.size() > 0) {
                Image image = images.get(0);

                presenter.changeAvatar(Uri.fromFile(new File(image.getPath())));
            }
        }
        if (requestCode == REQUEST_CHANGE_COVER && resultCode == RESULT_OK && data != null) {
            List<Image> images = data.getParcelableArrayListExtra("selectedImages");
            if (images != null && images.size() > 0) {
                Image image = images.get(0);

                presenter.changeCover(Uri.fromFile(new File(image.getPath())));
            }
        }
    }

    @Override
    public void onEditPost(Post post) {
        EditPostActivity.start(getContext(), post);
    }

    @Override
    public void onDeletePost(Post post) {
        presenter.deletePost(post);
    }

    @Override
    protected void screenResume() {

    }

    @Override
    protected void screenPause() {

    }

    @Override
    protected void screenStart(@Nullable Bundle saveInstanceState) {

    }

    @Override
    public void onDownload(String imageUrl) {
        DownloadUltil.startDownload(getContext(), imageUrl);
    }

    @Override
    public void onFriendProfile(String userId) {
        FriendProfileActivity.startActivity(getContext(), userId);
    }
}