package com.technology.hisabKitab.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.technology.hisabKitab.Model.AddEntery;
import com.technology.hisabKitab.R;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {
    Context c;
    public ArrayList<AddEntery> usersList=new ArrayList<>();
    Dialog specfic_item;
    TextView title,remarks,total_amount,each_amount,person_included,payed_by;
    Button btn_done;
    public ArrayList<AddEntery> selected_usersList=new ArrayList<>();
    public HistoryAdapter(Context c, ArrayList<AddEntery> spacecrafts, ArrayList<AddEntery> selectedList) {
        this.c = c;
        this.usersList = spacecrafts;
        this.selected_usersList = selectedList;
    }

    @Override
    public HistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_adapter, parent, false);
        return new HistoryAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryAdapter.MyViewHolder holder, final int position) {

        holder.nameTxt.setText(usersList.get(position).getDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showdialog(usersList.get(position).getDate(),usersList.get(position).getPayed_person_name(),
                        usersList.get(position).getTotal_amount(),usersList.get(position).getEach_amount()
                        ,usersList.get(position).getSelected_person(),usersList.get(position).getRemarks());

            }
        });

        if(selected_usersList.contains(usersList.get(position)))
            holder.ll_listitem.setBackgroundColor(ContextCompat.getColor(c, R.color.colorButtonTintUnChecked));
        else
            holder.ll_listitem.setBackgroundColor(ContextCompat.getColor(c, R.color.colorPrimary));



    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nameTxt ;
        TextView propTxt ;
        public CardView ll_listitem;

        public MyViewHolder(View itemView) {

            super(itemView);

            nameTxt = (TextView) itemView.findViewById(R.id.fname);
            propTxt = (TextView) itemView.findViewById(R.id.lname);
            ll_listitem=(CardView) itemView.findViewById(R.id.ll_listitem);

        }
    }
    private void showdialog(final String date, String payed_person, String total_Amount,String Each_amount,String selected_person,String Remarks)
    {
        specfic_item = new Dialog(c);
        specfic_item.setContentView(R.layout.dialog_specfic_item);
        title = (TextView) specfic_item.findViewById(R.id.txt_title);
        payed_by= (TextView) specfic_item.findViewById(R.id.txt_payed_by);
        total_amount = (TextView) specfic_item.findViewById(R.id.txt_total_amount);
        each_amount = (TextView) specfic_item.findViewById(R.id.txt_each_amount);
        person_included = (TextView) specfic_item.findViewById(R.id.txt_person_included);
        remarks = (TextView) specfic_item.findViewById(R.id.txt_remarks);
        btn_done = (Button) specfic_item.findViewById(R.id.button_done);

        title.setText(date);
        payed_by.setText(payed_person);
        total_amount.setText(total_Amount);
        each_amount.setText(Each_amount);
        person_included.setText(selected_person);
        remarks.setText(Remarks);

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                specfic_item.dismiss();
            }
        });

        specfic_item.setCanceledOnTouchOutside(true);
        specfic_item.show();
        specfic_item.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        specfic_item.setCancelable(false);

    }
}
