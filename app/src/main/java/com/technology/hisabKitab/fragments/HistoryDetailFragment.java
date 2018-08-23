package com.technology.hisabKitab.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.technology.hisabKitab.Adapter.HistoryAdapter;
import com.technology.hisabKitab.Model.AddEntery;
import com.technology.hisabKitab.R;
import com.technology.hisabKitab.utils.AlertDialogHelper;
import com.technology.hisabKitab.utils.AppUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class HistoryDetailFragment extends Fragment implements View.OnClickListener,AlertDialogHelper.AlertDialogListener{
    private Update_DeleteFragment.ViewHolder mHolder;

    ListView mListView;
    TextView mEmptyView;
    DatabaseReference db;
    DatabaseReference databaseReference;
    Firebase helper;
    HistoryAdapter adapter;
    Calendar calendar;
    String Current_month;
    boolean isMultiSelect = false;
    ArrayList<AddEntery> multiselect_list = new ArrayList<>();
    AlertDialogHelper alertDialogHelper;
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem view;

        inflater.inflate(R.menu.menu_search, menu);
        view = menu.findItem(R.id.action_search);
        SearchView mSearchView = (SearchView) view.getActionView();
//        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                mAdapter.getFilter().filter(query);
//                Toast.makeText(getContext(), "submit", Toast.LENGTH_SHORT).show();
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                mAdapter.getFilter().filter(newText);
//                Toast.makeText(getContext(), "changed", Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        });


    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        alertDialogHelper =new AlertDialogHelper(getContext());

        View view = inflater.inflate(R.layout.fragment_history_detail, container, false);
//        mListView = (ListView) view.findViewById(R.id.list);
//        mEmptyView = (TextView) view.findViewById(R.id.txt_empty);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        calendar=Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MMMM-dd");
        Current_month= AppUtils.getFormattedDate(df.format(calendar.getTime()));

        db = FirebaseDatabase.getInstance().getReference().child("Months").child(Current_month);
        helper = new Firebase(db);
        adapter = new HistoryAdapter(getContext(), helper.retrieve(),multiselect_list);
        recyclerView.setAdapter(adapter);




        return view;
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void onPositiveClick(int from) {

    }

    @Override
    public void onNegativeClick(int from) {

    }

    @Override
    public void onNeutralClick(int from) {

    }

    public static class ViewHolder {


        public ViewHolder(View view) {


        }
    }
}