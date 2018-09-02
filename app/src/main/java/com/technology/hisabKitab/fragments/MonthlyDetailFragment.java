package com.technology.hisabKitab.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.technology.hisabKitab.Adapter.HistoryAdapter;
import com.technology.hisabKitab.Model.AddEntery;
import com.technology.hisabKitab.Model.User;
import com.technology.hisabKitab.R;
import com.technology.hisabKitab.dialog.SimpleDialog;
import com.technology.hisabKitab.toolbox.ToolbarListener;
import com.technology.hisabKitab.utils.AppUtils;
import com.technology.hisabKitab.utils.LoginUtils;
import com.technology.hisabKitab.utils.RecyclerItemClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MonthlyDetailFragment  extends Fragment implements View.OnClickListener {
    private Update_DeleteFragment.ViewHolder mHolder;

    ListView mListView;
    TextView mEmptyView;
    DatabaseReference db, userdb, delete_item;
    DatabaseReference databaseReference;
    FireBaseHelper helper_user;
    Firebase helper;
    HistoryAdapter adapter;
    Calendar calendar;
    String Current_month, current_date_time, each_amount, payed_person, remarks, totalamount;
    boolean isMultiSelect = false;
    ArrayList<AddEntery> multiselect_list = new ArrayList<>();
    ArrayList<AddEntery> user_list = new ArrayList<>();
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    ActionMode mActionMode;
    Menu context_menu;
    SimpleDialog mSimpleDialog;
    String[] aary;
    String selected_person = "";
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();
    List<String> areas;
    private ArrayList<User> users;
    int Postion;

    Bundle bundle;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ToolbarListener) {
            ((ToolbarListener) context).setTitle("History");
        }
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_history_detail, container, false);
//        mListView = (ListView) view.findViewById(R.id.list);
//        mEmptyView = (TextView) view.findViewById(R.id.txt_empty);
        users = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        databaseReference = FirebaseDatabase.getInstance().getReference().child(LoginUtils.getUserEmail(getContext())).child("Months");

        userdb = FirebaseDatabase.getInstance().getReference().child(LoginUtils.getUserEmail(getContext())).child("Person");
        helper_user = new FireBaseHelper(userdb);
        users = helper_user.retrieve();

        Getuserdata();
        Getdatetime();
        manipulateBundle(getArguments());


//        db = FirebaseDatabase.getInstance().getReference().child(LoginUtils.getUserEmail(getContext())).child("Months").child(Current_month);
//        helper = new Firebase(db);
//        adapter = new HistoryAdapter(getContext(), helper.retrieve(), multiselect_list);
//        recyclerView.setAdapter(adapter);
        user_list = helper.retrieve();
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (isMultiSelect)
                    multi_select(position);
                //      Toast.makeText(getContext(), ""+user_list.get(position).getSelected_person(), Toast.LENGTH_SHORT).show();



            }

            @Override
            public void onItemLongClick(View view, int position) {
                //       Toast.makeText(getContext(), ""+user_list.get(position).getSelected_person(), Toast.LENGTH_SHORT).show();
                Postion=position;
                if (!isMultiSelect) {
                    multiselect_list = new ArrayList<AddEntery>();
                    isMultiSelect = true;

                    if (mActionMode == null) {
                        mActionMode = getActivity().startActionMode(mActionModeCallback);
                    }
                }

                multi_select(position);

            }
        }));


        return view;
    }

    private void manipulateBundle(Bundle bundle) {
        if (bundle != null) {

            String datetime =bundle.getString("MonthFormat");
            db = FirebaseDatabase.getInstance().getReference().child(LoginUtils.getUserEmail(getContext())).child("Months").child(datetime);
            helper = new Firebase(db);
            adapter = new HistoryAdapter(getContext(), helper.retrieve(), multiselect_list);
            recyclerView.setAdapter(adapter);
            Toast.makeText(getContext(), datetime, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View view) {

    }


    public static class ViewHolder {


        public ViewHolder(View view) {


        }
    }

    public void multi_select(int position) {
        if (mActionMode != null) {
            if (multiselect_list.contains(user_list.get(position)))
                multiselect_list.remove(user_list.get(position));
            else
                multiselect_list.add(user_list.get(position));


            if (multiselect_list.size() > 0)
                mActionMode.setTitle("" + multiselect_list.size());


            else
                mActionMode.setTitle("");

            refreshAdapter();

        }
    }

    public void refreshAdapter() {
        adapter.selected_usersList = multiselect_list;
        adapter.usersList = user_list;
        adapter.notifyDataSetChanged();
    }

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_multi_select, menu);
            context_menu = menu;
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_delete:

                    mSimpleDialog = new SimpleDialog(getContext(), null, getString(R.string.msg_delete),
                            getString(R.string.button_cancel), getString(R.string.button_ok), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            switch (view.getId()) {
                                case R.id.button_positive:

                                    if (multiselect_list.size() > 0) {
                                        for (int i = 0; i < multiselect_list.size(); i++) {
                                            user_list.remove(multiselect_list.get(i));
                                            current_date_time = multiselect_list.get(i).getDate();
                                            DeleteItem(current_date_time);
                                            adapter.notifyDataSetChanged();
                                        }


                                        if (mActionMode != null) {
                                            mActionMode.finish();
                                        }
                                        Toast.makeText(getContext(), "Delete Click", Toast.LENGTH_SHORT).show();
                                        mSimpleDialog.dismiss();
                                    }


                                    break;
                                case R.id.button_negative:
                                    mSimpleDialog.dismiss();
                                    break;


                            }
                        }
                    });
                    mSimpleDialog.show();

                    //alertDialogHelper.showAlertDialog("","Delete Contact","DELETE","CANCEL",1,false);
                    return true;
                case R.id.action_clear:
                    Toast.makeText(getContext(), "clear", Toast.LENGTH_SHORT).show();
                    ShowDialog();
                    return true;


                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
            isMultiSelect = false;
            multiselect_list = new ArrayList<AddEntery>();
            refreshAdapter();
        }
    };

    public void ShowDialog() {
//        checkedItems = new boolean[listItems.size()];
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
        mBuilder.setTitle("Unselect payed person's");
        String currentString=user_list.get(Postion).getSelected_person();
        String[] separated = currentString.split(",");
//        int position=areas.indexOf(separated[0]);
        mUserItems.clear();
        for (int i=0; i<separated.length;i++){
            int position=areas.indexOf(separated[i]);

            if(position!=-1){
                checkedItems[position]=true;
                mUserItems.add(position);
            }
        }
        //   Toast.makeText(getContext(), "current item"+position, Toast.LENGTH_SHORT).show();
        mBuilder.setMultiChoiceItems(aary, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position,boolean isChecked) {


//                        if (isChecked) {
//                            if (!mUserItems.contains(position)) {
//                                mUserItems.add(position);
//                            }
//                        } else if (mUserItems.contains(position)) {
//                            mUserItems.remove(position);
//                        }
                if (isChecked) {
                    // Toast.makeText(getContext(), ""+isChecked+position, Toast.LENGTH_SHORT).show();
                    mUserItems.add(position);

                } else {
                    mUserItems.remove((Integer.valueOf(position)));
                    //Toast.makeText(getContext(), ""+isChecked+position, Toast.LENGTH_SHORT).show();

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
                        selected_person = selected_person + ",";
                    }
                }
                Toast.makeText(getContext(), selected_person, Toast.LENGTH_SHORT).show();

                for (int i = 0; i < multiselect_list.size(); i++) {
                    current_date_time = multiselect_list.get(i).getDate();
                    each_amount = multiselect_list.get(i).getEach_amount();
                    remarks = multiselect_list.get(i).getRemarks();
                    payed_person = multiselect_list.get(i).getPayed_person_name();
                    totalamount = multiselect_list.get(i).getTotal_amount();

                    AddEntery addEntery = new AddEntery(payed_person,
                            totalamount,
                            each_amount, remarks,
                            selected_person,
                            current_date_time);
                    databaseReference.child(Current_month).child(current_date_time).setValue(addEntery);

                    adapter.notifyDataSetChanged();
                }

                Toast.makeText(getContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();
                getActivity().finish();
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
//                for (int i = 0; i < mUserItems.size(); i++) {
//                    //checkedItems[i] = false;
//                    mUserItems.clear();
//                    // mItemSelected.setText("");
//                }
                mUserItems.clear();
            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();


    }

    public void Getuserdata() {

        userdb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                checkedItems=new boolean[users.size()];

                areas = new ArrayList<>();
                for (int i = 0; i < users.size(); i++) {
                    areas.add(String.valueOf(users.get(i).getFname()));
                    aary = areas.toArray(new String[i]);
                    //
                    //  Toast.makeText(getContext(), ""+listItems, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void Getdatetime() {
        calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MMMM-dd");
        Current_month = AppUtils.getFormattedDate(df.format(calendar.getTime()));

    }

    public void DeleteItem(String value) {
        delete_item = FirebaseDatabase.getInstance().getReference().child(LoginUtils.getUserEmail(getContext())).child("Months").child(Current_month).child(value);
        delete_item.removeValue();

    }

}