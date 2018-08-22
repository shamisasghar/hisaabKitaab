package com.technology.hisabKitab.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.technology.hisabKitab.FrameActivity;
import com.technology.hisabKitab.R;
import com.technology.hisabKitab.SimpleFrameActivity;
import com.technology.hisabKitab.toolbox.ToolbarListener;
import com.technology.hisabKitab.utils.ActivityUtils;

/**
 * Created by Bilal Rashid on 10/10/2017.
 */

public class HomeFragment extends Fragment implements View.OnClickListener{
    private ViewHolder mHolder;
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
            ((ToolbarListener) context).setTitle("Home");
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
//        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
//        toolbar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.cardview_add_person:
                ActivityUtils.startActivity(getActivity(), FrameActivity.class,TabFragment.class.getName(),null);
                break;
            case R.id.cardview_update:
                ActivityUtils.startActivity(getActivity(), SimpleFrameActivity.class,Update_DeleteFragment.class.getName(),null);
                break;
            case R.id.cardview_add_entry:
                ActivityUtils.startActivity(getActivity(), FrameActivity.class,AddEntryFragment.class.getName(),null);
                break;
            case R.id.cardview_histroy:
                ActivityUtils.startActivity(getActivity(), SimpleFrameActivity.class,HistoryDetailFragment.class.getName(),null);
                break;
        }


    }

    public static class ViewHolder {
        CardView cardView_add_person,cardView_update_delete,cardView_add_entery,cardView_history;


        public ViewHolder(View view) {
            cardView_add_person = (CardView) view.findViewById(R.id.cardview_add_person);
            cardView_update_delete = (CardView) view.findViewById(R.id.cardview_update);
            cardView_add_entery = (CardView) view.findViewById(R.id.cardview_add_entry);
            cardView_history = (CardView) view.findViewById(R.id.cardview_histroy);

        }

    }
}
