package com.technology.hisabKitab;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.SearchView;
import android.widget.Toast;

import com.technology.hisabKitab.enumerations.AnimationEnum;
import com.technology.hisabKitab.fragments.HomeFragment;
import com.technology.hisabKitab.toolbox.ToolbarListener;
import com.technology.hisabKitab.utils.ActivityUtils;
import com.technology.hisabKitab.utils.Constants;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Bilal Rashid on 10/14/2017.
 */

public class SimpleFrameActivity extends AppCompatActivity implements ToolbarListener {
    private Toolbar mToolbar;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    String current_date_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_simple);
        toolbarSetup();
        String fragmentName = getIntent().getStringExtra(Constants.FRAGMENT_NAME);
        Bundle bundle = getIntent().getBundleExtra(Constants.DATA);
        if (!TextUtils.isEmpty(fragmentName)) {
            Fragment fragment = Fragment.instantiate(this, fragmentName);
            if (bundle != null)
                fragment.setArguments(bundle);
            addFragment(fragment);
        }
    }

    public void addFragment(final Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment != null) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_history, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                onBackPressed();
                return true;
            case R.id.action_calender:

                Calendar c = Calendar.getInstance();
                // From calander get the year, month, day, hour, minute
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog=new DatePickerDialog(this,R.style.DatePickerSpinner,onDateSetListener,year,month,day);
                datePickerDialog.getWindow();
                datePickerDialog.show();
                onDateSetListener=new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar = Calendar.getInstance();
                        String month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
                        String data = month+", "+year;
                        current_date_time=data;
                        Toast.makeText(SimpleFrameActivity.this, ""+current_date_time, Toast.LENGTH_SHORT).show();
                        // current_date_time=data;
                    }
                };



                break;
            default:
                // ...
        }
        return super.onOptionsItemSelected(item);
    }


    public void toolbarSetup() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(" ");
        //ActivityUtils.centerToolbarTitle(mToolbar,true);
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
//            getActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }



}
