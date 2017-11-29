package com.example.ryu.promanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by YM on 2017-11-29.
 */
public class schedule_adapter extends BaseAdapter {
    Context context;
    ArrayList<schedule_item> list_itemArraySchedule;

    TextView schedule_name;
    TextView schedule_work;
    TextView schedule_date;


    public schedule_adapter(Context context, ArrayList<schedule_item> list_itemArraySchedule) {
        this.context = context;
        this.list_itemArraySchedule = list_itemArraySchedule;
    }

    @Override
    public int getCount() {
        return this.list_itemArraySchedule.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list_itemArraySchedule.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.schedule_item, null);

            schedule_name = (TextView) convertView.findViewById(R.id.schedule_name);
            schedule_work = (TextView) convertView.findViewById(R.id.schedule_work);
            schedule_date = (TextView) convertView.findViewById(R.id.schedule_date);
        }
        schedule_name.setText(list_itemArraySchedule.get(position).getSchedule_name());
        schedule_work.setText(list_itemArraySchedule.get(position).getSchedule_work());
        schedule_date.setText(list_itemArraySchedule.get(position).getSchedule_date());

        return convertView;
    }
}
