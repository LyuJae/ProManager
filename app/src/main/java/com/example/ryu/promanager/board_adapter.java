package com.example.ryu.promanager;

/**
 * Created by YM on 2017-11-28.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by YM on 2017-11-28.
 */
public class board_adapter extends BaseAdapter {
    Context context;
    ArrayList<board_item> list_itemArrayBoard;

    TextView board_editor;
    TextView board_title;
    TextView board_text;


    public board_adapter(Context context, ArrayList<board_item> list_itemArrayBoard) {
        this.context = context;
        this.list_itemArrayBoard = list_itemArrayBoard;
    }

    @Override
    public int getCount() {
        return this.list_itemArrayBoard.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list_itemArrayBoard.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.board_item, null);

            board_editor = (TextView) convertView.findViewById(R.id.board_editor);
            board_title = (TextView) convertView.findViewById(R.id.board_title);
            board_text = (TextView) convertView.findViewById(R.id.board_text);
        }
        board_editor.setText(list_itemArrayBoard.get(position).getBoard_editor());
        board_title.setText(list_itemArrayBoard.get(position).getBoard_title());
        board_text.setText(list_itemArrayBoard.get(position).getBoard_text());

        return convertView;
    }
}

