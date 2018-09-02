package com.technology.hisabKitab.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.util.Size;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;
import com.technology.hisabKitab.Api.ApiClient;
import com.technology.hisabKitab.Api.ApiInterface;
import com.technology.hisabKitab.Model.AddEntery;
import com.technology.hisabKitab.Model.Contents;
import com.technology.hisabKitab.Model.Data;
import com.technology.hisabKitab.Model.Filter;
import com.technology.hisabKitab.Model.Notification;
import com.technology.hisabKitab.Model.User;
import com.technology.hisabKitab.R;
import com.technology.hisabKitab.toolbox.ToolbarListener;
import com.technology.hisabKitab.utils.AppUtils;
import com.technology.hisabKitab.utils.Constants;
import com.technology.hisabKitab.utils.LoginUtils;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEnteryFragment extends Fragment implements View.OnClickListener {
    DatabaseReference db;
    Spinner spinner;
    View view;
    TextView date_time;
    String current_date_time;
    private ArrayList<User> users;
    FireBaseHelper helper;
    String spinner_item;
    private ViewHolder mHolder;
    boolean[] checkedItems;
    List<String> areas;
    DatabaseReference databaseReference;
    ArrayList<Integer> mUserItems = new ArrayList<>();
    String[] aary;
    String selected_person = "";
    String Current_month, totalamount;
    Calendar calendar;
    int eachamount;
    ImageView img_edit;
    SimpleDateFormat df;
    SimpleDateFormat simpleDateFormat;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    ProgressBar progressBar;
    String Remarks, TotalAmount;
    int size;
    String data,monthformat;

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_add_person:
                ShowDialog();
                break;
            case R.id.btn_save_entery:
                Today_Entery();
                break;

        }

    }


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
            ((ToolbarListener) context).setTitle("Today Entery");
        }
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mHolder = new ViewHolder(view);
        mHolder.button_add_person.setOnClickListener(this);
        mHolder.button_add_entery.setOnClickListener(this);
//        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
//        toolbar.setOnClickListener(this);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_add_entery, container, false);
        spinner = (Spinner) view.findViewById(R.id.Spinner);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        img_edit = (ImageView) view.findViewById(R.id.image_edit);
        users = new ArrayList<>();
        date_time = (TextView) view.findViewById(R.id.txt_date_time);
        databaseReference = FirebaseDatabase.getInstance().getReference().child(LoginUtils.getUserEmail(getContext())).child("Months");
        db = FirebaseDatabase.getInstance().getReference().child(LoginUtils.getUserEmail(getContext())).child("Person");
        helper = new FireBaseHelper(db);
        users = helper.retrieve();
        progressBar.setVisibility(View.VISIBLE);
        Toast.makeText(getContext(), "" + LoginUtils.getUserEmail(getContext()), Toast.LENGTH_SHORT).show();


//        checkedItems = new boolean[listItems.length];

        getdatetime();
        getSpinnerdata();

        img_edit.setOnClickListener(new View.OnClickListener() {

            Calendar c = Calendar.getInstance();
            // From calander get the year, month, day, hour, minute
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            @Override
            public void onClick(View view) {
                // Toast.makeText(getActivity(), "cleick", Toast.LENGTH_SHORT).show();
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), R.style.DatePickerSpinner, onDateSetListener, year, month, day);
                datePickerDialog.getWindow();
                datePickerDialog.show();

                //    showDate(year, month, day, R.style.DatePickerSpinner);

            }
        });
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                Calendar calendar= Calendar.getInstance();
                String month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
                 data= year + "-" + getMonthShortName(monthOfYear) + "-" + dayOfMonth;
                 monthformat=getMonthShortName(monthOfYear)+", "+year;
                Toast.makeText(getContext(), monthformat, Toast.LENGTH_SHORT).show();
                current_date_time=data;
                date_time.setText(current_date_time);


                // current_date_time=data;
            }
        };


        return view;
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


    public void getdatetime() {
        SimpleDateFormat outputFmt = new SimpleDateFormat("yyyy-MMMM-dd");
        outputFmt.setTimeZone(TimeZone.getTimeZone("gmt"));
        current_date_time = outputFmt.format(new Date());
        date_time.setText(current_date_time);
        calendar = Calendar.getInstance();
        df = new SimpleDateFormat("yyyy-MMMM-dd");
        Current_month = AppUtils.getFormattedDate(df.format(calendar.getTime()));


    }

    public void getSpinnerdata() {
        // Toast.makeText(getContext(), ""+users.size(), Toast.LENGTH_SHORT).show();


        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                checkedItems = new boolean[users.size()];
                progressBar.setVisibility(View.GONE);

                areas = new ArrayList<>();

                try {

                    for (int i = 0; i < users.size(); i++) {
//                        if(i==0)
//                        {
//                            areas.add("Select Payed Person");
//                        }
//                        else {
                        areas.add(String.valueOf(users.get(i).getFname()));

                        aary = areas.toArray(new String[i]);
                        //     }
                        //
                        //  Toast.makeText(getContext(), ""+listItems, Toast.LENGTH_SHORT).show();
                    }
                    if (areas != null) {
                        progressBar.setVisibility(View.GONE);
                    }

                    final ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, areas);
                    spinner.setAdapter(areasAdapter);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                            spinner_item = areasAdapter.getItem(position);
                            //   Toast.makeText(getContext(), ""+spinner_item, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                } catch (Exception ex) {
                    progressBar.setVisibility(View.GONE);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    public static class ViewHolder {

        Button button_add_person, button_add_entery;
        EditText total_amount, each_amount, remarks;


        public ViewHolder(View view) {
            button_add_entery = (Button) view.findViewById(R.id.btn_save_entery);
            button_add_person = (Button) view.findViewById(R.id.btn_add_person);
            total_amount = (EditText) view.findViewById(R.id.edittext_total_amount);
            each_amount = (EditText) view.findViewById(R.id.edittext_each_amount);
            remarks = (EditText) view.findViewById(R.id.edittext_remarks);

        }

    }

    public void ShowDialog() {
        if (mHolder.total_amount.getText().toString().equals("")) {
            AppUtils.showSnackBar(getView(), "Add Total Amount First");

        } else {

            Toast.makeText(getContext(), spinner_item, Toast.LENGTH_SHORT).show();
            for (int i = 0; i < users.size(); i++) {
                if (spinner_item.equals(users.get(i).getFname())) {
                    Toast.makeText(getContext(), "mathces", Toast.LENGTH_SHORT).show();
                    checkedItems[i] = false;
                } else {
                    checkedItems[i] = true;
                    mUserItems.add(i);
                }


            }


//        checkedItems = new boolean[listItems.size()];
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
            mBuilder.setTitle("UnSelect Dining Person's");
            mBuilder.setMultiChoiceItems(aary, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
//                        if (isChecked) {
//                            if (!mUserItems.contains(position)) {
//                                mUserItems.add(position);
//                            }
//                        } else if (mUserItems.contains(position)) {
//                            mUserItems.remove(position);
//                        }
                    if (isChecked) {
                        mUserItems.add(position);
                    } else {
                        mUserItems.remove((Integer.valueOf(position)));
                    }
                }
            });

            mBuilder.setCancelable(false);
            mBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {

                    for (int i = 0; i < mUserItems.size(); i++) {
                        selected_person = selected_person + aary[mUserItems.get(i)];
                        if (i != mUserItems.size() - 1) {
                            selected_person = selected_person + ", ";
                        }
                    }
                    Toast.makeText(getContext(), "" + selected_person, Toast.LENGTH_SHORT).show();
                    GetEachAmount();
                    //  mItemSelected.setText(selected_person);
                }
            });

            mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            mBuilder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    for (int i = 0; i < mUserItems.size(); i++) {
                        // checkedItems[i] = false;
                        mUserItems.clear();
                        // mItemSelected.setText("");
                    }
                }
            });

            AlertDialog mDialog = mBuilder.create();
            mDialog.show();
        }

    }

    public void GetEachAmount() {
        totalamount = mHolder.total_amount.getText().toString();
        size = mUserItems.size();
        eachamount = Integer.parseInt(totalamount) / (size + 1);
        mHolder.each_amount.setText(String.valueOf(eachamount));
        //      Toast.makeText(getContext(), ""+String.valueOf(eachamount), Toast.LENGTH_SHORT).show();

    }


    public void Today_Entery()

    {
        Remarks = mHolder.remarks.getText().toString().trim();
        TotalAmount = mHolder.total_amount.getText().toString().trim();
        if (TextUtils.isEmpty(TotalAmount)) {
            AppUtils.showSnackBar(getView(), "Enter Total Amount");
        } else if (TextUtils.isEmpty(Remarks))

        {
            AppUtils.showSnackBar(getView(), "Enter Remarks");
        } else if (size == 0) {
            AppUtils.showSnackBar(getView(), "Select Dining Person");
        } else {

            if(monthformat!=null)
            {
                AddEntery addEntery = new AddEntery(spinner_item, mHolder.total_amount.getText().toString(),
                        String.valueOf(eachamount), mHolder.remarks.getText().toString(),
                        selected_person, current_date_time);
                databaseReference.setValue(addEntery);

            }
            else {

                AddEntery addEntery = new AddEntery(spinner_item, mHolder.total_amount.getText().toString(),
                        String.valueOf(eachamount), mHolder.remarks.getText().toString(),
                        selected_person, current_date_time);
                databaseReference.child(Current_month).child(current_date_time).setValue(addEntery);
//            OneSignal.sendTag("User", LoginUtils.getUserEmail(getContext()));
            }

            sendNotification();
            getActivity().finish();
        }
    }

    public void sendNotification() {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Notification request = new Notification();
        request.app_id = "720b0741-307c-4d08-9be0-3445acbc95dd";
        request.contents = new Contents();
        request.contents.en = "Entery of " + mHolder.remarks.getText().toString() + " has been Added"+"\n"+current_date_time;
        request.data = new Data();
        request.data.data = "data";
        Filter filter = new Filter();
        filter.field = "tag";
        filter.key = LoginUtils.getUserEmail(getContext());
        filter.relation = "=";
        filter.value = "1";
        request.filters = new ArrayList<>();
        request.filters.add(filter);
        Call<Object> call = apiService.postPackets(request);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
//                hideLoader();
//                finish();
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.d("TAAAG", "" + t.getMessage());

            }
        });
    }


}
