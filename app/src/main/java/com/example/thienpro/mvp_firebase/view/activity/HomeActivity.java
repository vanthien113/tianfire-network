package com.example.thienpro.mvp_firebase.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivityHomeBinding;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.presenter.HomePresenter;
import com.example.thienpro.mvp_firebase.view.HomeView;
import com.example.thienpro.mvp_firebase.view.adapters.HomeAdapter;
import com.example.thienpro.mvp_firebase.view.adapters.loadmore;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements HomeView, loadmore{
    private ActivityHomeBinding binding;
    private HomeAdapter homeAdapter;
    private LinearLayoutManager LinearLayoutManager;
    private HomePresenter homePresenter;
    private ArrayList<Post> listpost;
    private ArrayList<Post> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        binding.setEvent(this);

        setSupportActionBar(binding.tbHome);
        listpost = new ArrayList<>();
        arrayList = new ArrayList<>();

        homePresenter = new HomePresenter(this);
        homePresenter.loadAllListPost();

        LinearLayoutManager = new LinearLayoutManager(this);
        LinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.rvHome.setLayoutManager(LinearLayoutManager);

        binding.setEvent(this);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        homePresenter.loadAllListPost();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public void onSignOutClick() {
        FirebaseAuth.getInstance().signOut();
        navigationToMain();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.it_profile:
                navigationToProfile();
                return true;
            case R.id.it_edit:
                navigationToEditInfo();
                return true;
            case R.id.it_logout:
                onSignOutClick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void navigationToMain() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigationToEditInfo() {
        Intent intent = new Intent(this, EditInfoActivity.class);
        startActivity(intent);
    }

    @Override
    public void showAllPost(ArrayList<Post> list) {
        binding.tvLoading.setVisibility(View.GONE);
        listpost = list;

        for(int i = 0; i<10; i++){
            arrayList.add(listpost.get(i));
            Log.e("THIEN", "arrl "+ i +" "+ String.valueOf(arrayList.size()));
        }

        homeAdapter = new HomeAdapter(arrayList, this);
        binding.rvHome.setAdapter(homeAdapter);
    }

    @Override
    public void navigationToProfile() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
    private boolean isLoadMore = false;

    @Override
    public void onLoadmore() {
        if (isLoadMore){
            return;
        }
        isLoadMore = true;
        Handler handler= new Handler();
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<Post> mlist = new ArrayList<>();
                for(int i = homeAdapter.getItemCount(); (i<homeAdapter.getItemCount()+10) && i<listpost.size();i++)
                {
                    mlist.add(listpost.get(i));
                }
                    homeAdapter.appendItem(mlist);
                isLoadMore = false;
            }
        },300);
    }
}
