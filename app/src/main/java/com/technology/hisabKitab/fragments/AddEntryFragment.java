package com.technology.hisabKitab.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.technology.hisabKitab.Model.AddEntery;
import com.technology.hisabKitab.Model.User;
import com.technology.hisabKitab.R;
import com.technology.hisabKitab.toolbox.ToolbarListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class AddEntryFragment extends Fragment implements View.OnClickListener {
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
            ((ToolbarListener) context).setTitle("Today Entry");
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
        users = new ArrayList<>();
        date_time = (TextView) view.findViewById(R.id.txt_date_time);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Months");
        db = FirebaseDatabase.getInstance().getReference().child("Person");
        helper = new FireBaseHelper(db);
        users = helper.retrieve();
//        checkedItems = new boolean[listItems.length];

        getdatetime();
        getSpinnerdata();


        return view;
    }

    public void getdatetime() {
        SimpleDateFormat outputFmt = new SimpleDateFormat("yyyy-MMMM-dd");
        outputFmt.setTimeZone(TimeZone.getTimeZone("gmt"));
        current_date_time = outputFmt.format(new Date());
        date_time.setText(current_date_time);

    }

    public void getSpinnerdata() {
        // Toast.makeText(getContext(), ""+users.size(), Toast.LENGTH_SHORT).show();


        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

               areas  = new ArrayList<>();

                try {
                    for (int i = 0; i < users.size(); i++) {
                        areas.add(String.valueOf(users.get(i).getFname()));
                        aary= areas.toArray(new String[i]);
                      //
                        //  Toast.makeText(getContext(), ""+listItems, Toast.LENGTH_SHORT).show();
                    }

                    final ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, areas);

                    spinner.setAdapter(areasAdapter);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                            spinner_item=areasAdapter.getItem(position);
                            Toast.makeText(getContext(), ""+spinner_item, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                } catch (Exception ex) {
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public static class ViewHolder {

        Button button_add_person, button_add_entery;
        EditText total_amount,each_amount,remarks;

        public ViewHolder(View view) {
            button_add_entery = (Button) view.findViewById(R.id.btn_save_entery);
            button_add_person = (Button) view.findViewById(R.id.btn_add_person);
            total_amount = (EditText) view.findViewById(R.id.edittext_total_amount);
            each_amount = (EditText) view.findViewById(R.id.edittext_each_amount);
            remarks = (EditText) view.findViewById(R.id.edittext_remarks);

        }

    }
    public void ShowDialog()
    {
//        checkedItems = new boolean[listItems.size()];
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
        mBuilder.setTitle("select person's");
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
                if(isChecked){
                    mUserItems.add(position);
                }else{
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
                Toast.makeText(getContext(), ""+ selected_person, Toast.LENGTH_SHORT).show();
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

    public void Today_Entery()
    {

        AddEntery addEntery=new AddEntery(spinner_item,mHolder.total_amount.getText().toString(),
                mHolder.each_amount.getText().toString(),mHolder.remarks.getText().toString(),
                selected_person,current_date_time);

        databaseReference.child(current_date_time).setValue(addEntery);

    }


}
