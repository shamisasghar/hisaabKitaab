package com.technology.hisabKitab.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.technology.hisabKitab.Adapter.UpdateDeleteAdapter;
import com.technology.hisabKitab.R;
import com.technology.hisabKitab.utils.LoginUtils;

public class Update_DeleteFragment extends Fragment implements View.OnClickListener {

    private ViewHolder mHolder;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    TextView mEmptyView;
    DatabaseReference db;
    FireBaseHelper helper;
    UpdateDeleteAdapter updateDeleteAdapter;

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
        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_update_delete);
      //  mEmptyView = (TextView) view.findViewById(R.id.txt_empty);
        layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


        db = FirebaseDatabase.getInstance().getReference().child(LoginUtils.getUserEmail(getContext())).child("Person");
        helper = new FireBaseHelper(db);
        updateDeleteAdapter = new UpdateDeleteAdapter(getContext(), helper.retrieve());
        recyclerView.setAdapter(updateDeleteAdapter);


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
