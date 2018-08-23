package com.technology.hisabKitab.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.technology.hisabKitab.R;
import com.technology.hisabKitab.utils.AppUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HistoryDetailFragment extends Fragment implements View.OnClickListener{
    private Update_DeleteFragment.ViewHolder mHolder;

    ListView mListView;
    TextView mEmptyView;
    DatabaseReference db;
    DatabaseReference databaseReference;
    Firebase helper;
    HistoryAdapter adapter;
    Calendar calendar;
    String Current_month;

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

        View view = inflater.inflate(R.layout.fragment_history_detail, container, false);
        mListView = (ListView) view.findViewById(R.id.list);
        mEmptyView = (TextView) view.findViewById(R.id.txt_empty);
        calendar=Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MMMM-dd");
        Current_month= AppUtils.getFormattedDate(df.format(calendar.getTime()));

        db = FirebaseDatabase.getInstance().getReference().child("Months").child(Current_month);
        helper = new Firebase(db);
        adapter = new HistoryAdapter(getContext(), helper.retrieve());
        mListView.setAdapter(adapter);




        return view;
    }


    @Override
    public void onClick(View view) {

    }

    public static class ViewHolder {


        public ViewHolder(View view) {


        }
    }
}