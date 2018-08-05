package com.technology.hisabKitab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.technology.hisabKitab.dialog.SimpleDialog;
import com.technology.hisabKitab.fragments.HomeFragment;
import com.technology.hisabKitab.toolbox.ToolbarListener;
import com.technology.hisabKitab.utils.ActivityUtils;
import com.technology.hisabKitab.utils.Constants;

/**
 * Created by Bilal Rashid on 10/10/2017.
 */

public class HomeActivity extends AppCompatActivity implements ToolbarListener {
    private Toolbar mToolbar;
    private SimpleDialog mSimpleDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbarSetup();
        String fragmentName = getIntent().getStringExtra(Constants.FRAGMENT_NAME);
        Bundle bundle = getIntent().getBundleExtra(Constants.DATA);
        if (!TextUtils.isEmpty(fragmentName)) {
            Fragment fragment = Fragment.instantiate(this, fragmentName);
            if (bundle != null)
                fragment.setArguments(bundle);
            addFragment(fragment);
        } else {
            addFragment(new HomeFragment());
        }
//        if (!EventBus.getDefault().isRegistered(this))
//            EventBus.getDefault().register(this);
    }

    public void addFragment(final Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

    public void toolbarSetup() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(" ");
        ActivityUtils.centerToolbarTitle(mToolbar,false);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    public void setTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

    }

    @Override
    public void onBackPressed() {

        mSimpleDialog = new SimpleDialog(this, null, getString(R.string.msg_exit),
                getString(R.string.button_cancel), getString(R.string.button_ok), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.button_positive:
                        mSimpleDialog.dismiss();
                        HomeActivity.this.finish();
                        break;
                    case R.id.button_negative:
                        mSimpleDialog.dismiss();
                        break;
                }
            }
        });
        mSimpleDialog.show();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
