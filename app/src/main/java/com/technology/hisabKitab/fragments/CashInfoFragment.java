package com.technology.hisabKitab.fragments;

import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.technology.hisabKitab.Adapter.CashInfoAdapter;
import com.technology.hisabKitab.Adapter.UpdateDeleteAdapter;
import com.technology.hisabKitab.Model.AddEntery;
import com.technology.hisabKitab.Model.User;
import com.technology.hisabKitab.R;
import com.technology.hisabKitab.toolbox.ToolbarListener;
import com.technology.hisabKitab.utils.AppUtils;
import com.technology.hisabKitab.utils.LoginUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CashInfoFragment extends Fragment implements View.OnClickListener {

    View view;
    private CashInfoFragment.ViewHolder mHolder;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    TextView mEmptyView;
    DatabaseReference db;
    DatabaseReference databaseReference;
    FireBaseHelper helper;
    Firebase firebase;
    CashInfoAdapter updateDeleteAdapter;
    Spinner spinner;
    private ArrayList<User> users;
    private ArrayList<AddEntery> addEnteries;
    List<String> areas;
    String[] aary;
    String spinner_item;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ToolbarListener) {
            ((ToolbarListener) context).setTitle("Recovery");
        }
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

        View view = inflater.inflate(R.layout.fragment_cash_info, container, false);
        spinner = (Spinner) view.findViewById(R.id.Spinner);
        users = new ArrayList<>();
        // addEnteries = new ArrayList<>();

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_update_delete);
        //  mEmptyView = (TextView) view.findViewById(R.id.txt_empty);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        String Current_month;
        Calendar calendar;
        calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MMMM-dd");
        Current_month = AppUtils.getFormattedDate(df.format(calendar.getTime()));


        databaseReference = FirebaseDatabase.getInstance().getReference().child(LoginUtils.getUserEmail(getContext())).child("Months").child(Current_month);
        firebase = new Firebase(databaseReference);
        firebase.retrieve();

        db = FirebaseDatabase.getInstance().getReference().child(LoginUtils.getUserEmail(getContext())).child("Person");
        helper = new FireBaseHelper(db);
        updateDeleteAdapter = new CashInfoAdapter(getContext(), helper.retrieve(), firebase.retrieve());
        recyclerView.setAdapter(updateDeleteAdapter);
        users = helper.retrieve();
        addEnteries = firebase.retrieve();
        getSpinnerdata();


        return view;
    }


    @Override
    public void onClick(View view) {

    }

    public static class ViewHolder {


        public ViewHolder(View view) {


        }
    }

    public void getSpinnerdata() {
        // Toast.makeText(getContext(), ""+users.size(), Toast.LENGTH_SHORT).show();


        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                areas = new ArrayList<>();

                try {
                    for (int i = 0; i < users.size(); i++) {
                        if (i == 0) {
                            areas.add("Select Person");
                        } else {

                            areas.add(String.valueOf(users.get(i).getFname()));
                            aary = areas.toArray(new String[i]);
                        }
                        //
                        //  Toast.makeText(getContext(), ""+listItems, Toast.LENGTH_SHORT).show();
                    }

                    final ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, areas);

                    spinner.setAdapter(areasAdapter);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                            spinner_item = areasAdapter.getItem(position);
                            // Toast.makeText(getContext(), ""+spinner_item, Toast.LENGTH_SHORT).show();
                            getcashamount();
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

    String[] selected_person;

    public void getcashamount() {
        int amount = 0;

        for (int i = 0; i < addEnteries.size(); i++) {

                selected_person = addEnteries.get(i).getSelected_person().split(String.valueOf(','));

                for (String element : selected_person) {
                    if (element.equals(" " + spinner_item)) {

                        amount += Integer.parseInt(addEnteries.get(i).getEach_amount());
                        // Toast.makeText(getContext(), "matches" + addEnteries.get(i), Toast.LENGTH_SHORT).show();
                    } else if (element.equals(spinner_item)) {
                        amount += Integer.parseInt(addEnteries.get(i).getEach_amount());

                    }
                }
            }
        Toast.makeText(getContext(), "Amount Remaining:" + spinner_item + " " + amount, Toast.LENGTH_SHORT).show();

    }


}
