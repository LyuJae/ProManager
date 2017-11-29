package com.example.ryu.promanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by YM on 2017-11-07.
 */

public class teammate_adapter extends BaseAdapter {

    Context context;
    ArrayList<teammate_item> list_itemArrayTeammate;

    TextView nickname_textView;
    TextView title_textView;
    TextView content_textView;
    ImageView profile_imageView;

    public teammate_adapter(Context context, ArrayList<teammate_item> list_itemArrayTeammate) {
        this.context = context;
        this.list_itemArrayTeammate = list_itemArrayTeammate;
    }

    @Override
    public int getCount() {
        return this.list_itemArrayTeammate.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list_itemArrayTeammate.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.teammate_item, null);

            nickname_textView = (TextView) convertView.findViewById(R.id.nickname_textView);
            content_textView = (TextView) convertView.findViewById(R.id.content_textView);
            title_textView = (TextView) convertView.findViewById(R.id.title_textView);
            profile_imageView = (ImageView) convertView.findViewById(R.id.profile_imageView);

        }
        nickname_textView.setText(list_itemArrayTeammate.get(position).getName());
        title_textView.setText(list_itemArrayTeammate.get(position).getemail());
        content_textView.setText(list_itemArrayTeammate.get(position).getphone());
        profile_imageView.setImageResource(list_itemArrayTeammate.get(position).getProfile_image());

        return convertView;
    }
}



