package com.technology.hisabKitab.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.technology.hisabKitab.Adapter.CustomAdapter;
import com.technology.hisabKitab.fragments.FireBaseHelper;
import com.technology.hisabKitab.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class Update_DeleteFragment extends Fragment implements View.OnClickListener {

    private ViewHolder mHolder;

    ListView mListView;
    TextView mEmptyView;
    DatabaseReference db;
    FireBaseHelper helper;
    CustomAdapter adapter;

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

        View view = inflater.inflate(R.layout.fragment_update_delete, container, false);
        mListView = (ListView) view.findViewById(R.id.list);
        mEmptyView = (TextView) view.findViewById(R.id.txt_empty);

        db = FirebaseDatabase.getInstance().getReference();
        helper = new FireBaseHelper(db);
        adapter = new CustomAdapter(getContext(), helper.retrieve());
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
