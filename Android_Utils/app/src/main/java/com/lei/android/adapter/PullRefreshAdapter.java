package com.lei.android.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lei.android.android_utils.R;

public class PullRefreshAdapter extends PullRefreshBaseAdapter<String> {

    public PullRefreshAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder mHolder;
        if (view==null){
            view = inflater.inflate(R.layout.item_pullrefresh,parent,false);
            mHolder = new ViewHolder(view);
            view.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) view.getTag();
        }

        String item = itemList.get(position);

        mHolder.tv_text.setText(item);

        return view;
    }

    class ViewHolder {
        public TextView tv_text;
        public ViewHolder(View v) {
            this.tv_text = (TextView) v.findViewById(R.id.tv_item_pullfresh);
        }
    }
}
