package com.technology.hisabKitab.fragments;


import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.onesignal.OneSignal;
import com.technology.hisabKitab.FrameActivity;
import com.technology.hisabKitab.HomeActivity;
import com.technology.hisabKitab.R;
import com.technology.hisabKitab.SimpleFrameActivity;
import com.technology.hisabKitab.SplashActivity;
import com.technology.hisabKitab.toolbox.ToolbarListener;
import com.technology.hisabKitab.utils.ActivityUtils;
import com.technology.hisabKitab.utils.LoginUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Bilal Rashid on 10/10/2017.
 */

public class HomeFragment extends Fragment implements View.OnClickListener{
    private ViewHolder mHolder;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    String monthformat;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
//        if (!EventBus.getDefault().isRegistered(this))
//            EventBus.getDefault().register(this);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ToolbarListener) {
            ((ToolbarListener) context).setTitle("Dashboard");
        }
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mHolder = new ViewHolder(view);
        mHolder.cardView_add_person.setOnClickListener(this);
        mHolder.cardView_update_delete.setOnClickListener(this);
        mHolder.cardView_add_entery.setOnClickListener(this);
        mHolder.cardView_history.setOnClickListener(this);
        mHolder.cardView_check_cash.setOnClickListener(this);
        mHolder.cardView_detail.setOnClickListener(this);
//        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
//        toolbar.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.cardview_add_person:
                ActivityUtils.startActivity(getActivity(), FrameActivity.class,AddPersonFragment.class.getName(),null);
                break;
            case R.id.cardview_update:
                ActivityUtils.startActivity(getActivity(), FrameActivity.class,Update_DeleteFragment.class.getName(),null);
                break;
            case R.id.cardview_add_entry:
                ActivityUtils.startActivity(getActivity(), FrameActivity.class,AddEnteryFragment.class.getName(),null);
                break;
            case R.id.cardview_histroy:
                ActivityUtils.startActivity(getActivity(), FrameActivity.class,HistoryDetailFragment.class.getName(),null);
                break;

            case R.id.cardview_cash:
                ActivityUtils.startActivity(getActivity(), FrameActivity.class,CashInfoFragment.class.getName(),null);
                break;
            case R.id.cardview_Detail:

                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                final int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog=new DatePickerDialog(getContext(),R.style.DatePickerSpinner,onDateSetListener,year,month,day);
                datePickerDialog.getWindow();
                datePickerDialog.show();
                onDateSetListener=new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar = Calendar.getInstance();
                        monthformat=getMonthShortName(monthOfYear)+", "+year;
                      //  Toast.makeText(getContext(),monthformat, Toast.LENGTH_SHORT).show();
                        Bundle bundle=new Bundle();
                        bundle.putString("MonthFormat",monthformat);
                        ActivityUtils.startActivity(getActivity(), FrameActivity.class,HistoryDetailFragment.class.getName(),bundle);


                    }
                };

                break;

        }


    }

    public static class ViewHolder {
        CardView cardView_add_person
                ,cardView_update_delete
                ,cardView_add_entery
                ,cardView_history,cardView_check_cash,cardView_detail;


        public ViewHolder(View view) {
            cardView_add_person = (CardView) view.findViewById(R.id.cardview_add_person);
            cardView_update_delete = (CardView) view.findViewById(R.id.cardview_update);
            cardView_add_entery = (CardView) view.findViewById(R.id.cardview_add_entry);
            cardView_history = (CardView) view.findViewById(R.id.cardview_histroy);
            cardView_check_cash = (CardView) view.findViewById(R.id.cardview_cash);
            cardView_detail = (CardView) view.findViewById(R.id.cardview_Detail);

        }

    }

    public static String getMonthShortName(int monthNumber)
    {
        String monthName="";

        if(monthNumber>=0 && monthNumber<12)
            try
            {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.MONTH, monthNumber);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM");
                simpleDateFormat.setCalendar(calendar);
                monthName = simpleDateFormat.format(calendar.getTime());
            }
            catch (Exception e)
            {
                if(e!=null)
                    e.printStackTrace();
            }
        return monthName;
    }

}
