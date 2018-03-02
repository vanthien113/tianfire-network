package com.example.thienpro.mvp_firebase.view.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.example.thienpro.mvp_firebase.ultils.LoadingDialog;
import com.example.thienpro.mvp_firebase.view.ProfileView;
import com.example.thienpro.mvp_firebase.view.activity.PostActivity;
import com.example.thienpro.mvp_firebase.view.adapters.HomeAdapter;
import com.google.firebase.database.DatabaseError;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ThienPro on 11/22/2017.
 */

public class ProfileFragment extends Fragment implements ProfileView {
    private static final int REQUEST_CHANGE_AVATAR = 1;
    private static final int REQUEST_CHANGE_COVER = 2;

    private FragmentProfileBinding binding;
    private HomeAdapter homeAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private ProfilePresenter presenter;
    private ArrayList<Post> listPost;
    private LoadingDialog loadingDialog;

    public static ProfileFragment newInstance() {
        Bundle args = new Bundle();
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);

        presenter = new ProfilePresenterImpl(this, getContext());
        loadingDialog = new LoadingDialog(getContext());
        mLinearLayoutManager = new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false);

        binding.rvProfile.setLayoutManager(mLinearLayoutManager);
        binding.rvProfile.setNestedScrollingEnabled(false);

        presenter.loadPost();
//        presenter.getCurrentUser();
        presenter.getUser();

        binding.setEvent(this);
        return binding.getRoot(); // Lưu ý: binding.getRoot();
    }

    @Override
    public void onResume() {
        loadData();
        super.onResume();
    }

    public void loadData() {
        if (listPost != null) {
            binding.rvProfile.setLayoutFrozen(true);
            listPost.clear();
            presenter.loadPost();
            binding.rvProfile.setLayoutFrozen(false);
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
        homeAdapter = new HomeAdapter(listPost, getContext());
        binding.rvProfile.setAdapter(homeAdapter);
        binding.rvProfile.setLayoutFrozen(false);
    }

    @Override
    public void loadPostError(DatabaseError e) {
        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoading() {
        loadingDialog.dismiss();
    }

    @Override
    public void showUser(User user) {
        Glide.with(binding.getRoot().getContext())
                .load(user.getAvatar())
                .asBitmap().centerCrop()
                .into(new BitmapImageViewTarget(binding.ivAvatar) {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(binding.getRoot().getResources(), resource);
                        roundedBitmapDrawable.setCircular(true);
                        binding.ivAvatar.setImageDrawable(roundedBitmapDrawable);
                    }
                });

        Glide.with(binding.getRoot().getContext())
                .load(user.getCover())
                .asBitmap().centerCrop()
                .into(new BitmapImageViewTarget(binding.ivCover) {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    protected void setResource(Bitmap resource) {
                        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), resource);

                        binding.llCover.setBackground(bitmapDrawable);
                    }
                });

        binding.tvName.setText(user.getName());
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
    public void showError(Exception e) {
        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessenger(String messenger) {
        Toast.makeText(getContext(), messenger, Toast.LENGTH_SHORT).show();

        loadData();
    }

    @Override
    public void showLoading() {
        loadingDialog.show();
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
}