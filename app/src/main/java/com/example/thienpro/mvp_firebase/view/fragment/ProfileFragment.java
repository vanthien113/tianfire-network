package com.example.thienpro.mvp_firebase.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.FragmentProfileBinding;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.presenter.Impl.ProfilePresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.ProfilePresenter;
import com.example.thienpro.mvp_firebase.ultils.LayoutUltils;
import com.example.thienpro.mvp_firebase.ultils.widget.LoadingDialog;
import com.example.thienpro.mvp_firebase.ultils.widget.SHBitmapHelper;
import com.example.thienpro.mvp_firebase.view.ProfileView;
import com.example.thienpro.mvp_firebase.view.adapters.HomeAdapter;
import com.example.thienpro.mvp_firebase.view.adapters.SearchUserAdapter;
import com.example.thienpro.mvp_firebase.view.bases.BaseFragment;
import com.example.thienpro.mvp_firebase.view.listener.HomeNavigationListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by ThienPro on 11/22/2017.
 */

public class ProfileFragment extends BaseFragment<FragmentProfileBinding> implements ProfileView, HomeAdapter.ListPostMenuListener, HomeAdapter.DownloadImageListener, HomeAdapter.FriendProfileListener, SearchUserAdapter.SearchUserClickListener {
    public static final int REQUEST_CHANGE_AVATAR = 1;
    public static final int REQUEST_CHANGE_COVER = 2;

    private HomeAdapter homeAdapter;
    private ProfilePresenter presenter;
    private ArrayList<Post> listPost;
    private SearchUserAdapter searchUserAdapter;
    private HomeNavigationListener navigationListener;
    private LoadingDialog dialog;

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

        dialog = new LoadingDialog(getContext());

        presenter.loadPost();
        presenter.getUser();

        getBinding().rvProfile.setLayoutManager(LayoutUltils.getLinearLayoutManager(getContext()));
        getBinding().rvProfile.setNestedScrollingEnabled(false);

        getBinding().setEvent(this);

        getBinding().srlProfile.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
                getBinding().srlProfile.setRefreshing(false);
            }
        });

        searchEvent();
    }

    private void searchEvent() {
        getBinding().etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(getBinding().etSearch.getText())) {
                    presenter.searchUser(charSequence.toString());
                    getBinding().tvClear.setVisibility(View.VISIBLE);
                } else {
                    getBinding().tvClear.setVisibility(View.GONE);
                    getBinding().rvSearch.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onResume() {
        loadData();
        super.onResume();
    }

    @Override
    protected void attach(Context context) {
        if (context instanceof HomeNavigationListener) {
            navigationListener = (HomeNavigationListener) context;
        }
    }

    public void loadData() {
        if (listPost != null) {
            listPost.clear();
            presenter.loadPost();
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
        navigationListener.navigationToPostActivity();
    }

    @Override
    public void showList(ArrayList<Post> list) {
        Collections.reverse(list);
        listPost = list;
        homeAdapter = new HomeAdapter(listPost, getContext(), this, null, this, this);
        getBinding().rvProfile.setAdapter(homeAdapter);
    }

    @Override
    public void showSearchUser(ArrayList<User> list) {
        if (list != null && list.size() != 0) {
            getBinding().rvSearch.setVisibility(View.VISIBLE);
            searchUserAdapter = new SearchUserAdapter(list, this);
            getBinding().rvSearch.setLayoutManager(LayoutUltils.getLinearLayoutManager(getContext()));
            getBinding().rvSearch.setAdapter(searchUserAdapter);
        } else {
            getBinding().rvSearch.setVisibility(View.GONE);
        }
    }

    @Override
    public void showUser(User user) {
        showAvatarChanged(user.getAvatar());
        showCoverChanged(user.getCover());
        getBinding().tvName.setText(user.getName());
    }

    @Override
    public void onChangeAvatar() {
        SHBitmapHelper.takePhoto(this, REQUEST_CHANGE_AVATAR);
    }

    @Override
    public void onChangeCover() {
        SHBitmapHelper.takePhoto(this, REQUEST_CHANGE_COVER);
    }

    @Override
    public void reloadPost() {
        loadData();
    }

    @Override
    public void showAvatarChanged(String avatarUrl) {
        SHBitmapHelper.bindCircularImage(getBinding().ivAvatar, avatarUrl);
    }

    @Override
    public void showCoverChanged(String coverUrl) {
        SHBitmapHelper.bindImage(getBinding().ivCover, coverUrl);
    }

    @Override
    public void onShowListPictureClick() {
        navigationListener.navigationToPictureActivity(null);
    }

    @Override
    public void showLoading() {
        dialog.show();
    }

    @Override
    public void hideLoading() {
        dialog.dismiss();
    }

    @Override
    public void onClearTextClick() {
        getBinding().etSearch.setText(null);
        getBinding().rvSearch.setVisibility(View.GONE);
    }

    @Override
    public void showChangeComplete() {
        showToastMessage(R.string.cap_nhat_thanh_cong);
    }

    @Override
    public void showDeleteComplete() {
        showToastMessage(R.string.da_xoa);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        presenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onEditPost(Post post) {
        navigationListener.navigationToEditPostActivity(post);
    }

    @Override
    public void onDeletePost(Post post) {
        presenter.deletePost(post);
    }

    @Override
    public void onDownload(String imageUrl) {
        presenter.downloadImage(imageUrl);
    }

    @Override
    public void onFriendProfile(String userId) {
        navigationListener.navigationToFriendProfileActivity(userId);
    }

    @Override
    public void userClick(User user) {
        navigationListener.navigationToFriendProfileActivity(user.getId());
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
}