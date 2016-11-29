package com.example.madisonmaddox.prototype_step_by_step;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by madisonmaddox on 11/10/16.
 */

public class ImageAdapter extends BaseAdapter {
    private ArrayList<String> listTaskNames;
    private ArrayList<Integer> listPics;
    private Activity activity;

    public ImageAdapter(Activity activity,ArrayList<String> listTaskNames, ArrayList<Integer> listPics) {
        super();
        this.listTaskNames = listTaskNames;
        this.listPics = listPics;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listTaskNames.size();
    }

    @Override
    public String getItem(int position) {
        // TODO Auto-generated method stub
        return listTaskNames.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public static class ViewHolder {
        public ImageView imgViewPic;
        public TextView txtViewTaskName;
    }

    @Override
    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder view;
        LayoutInflater inflator = activity.getLayoutInflater();

        if(convertView==null)
        {
            view = new ViewHolder();
            convertView = inflator.inflate(R.layout.gridview_row, null);

            view.txtViewTaskName = (TextView) convertView.findViewById(R.id.textView1);
            view.imgViewPic = (ImageView) convertView.findViewById(R.id.imageView1);

            convertView.setTag(view);
        }
        else {
            view = (ViewHolder) convertView.getTag();
        }

        view.txtViewTaskName.setText(listTaskNames.get(position));
        view.imgViewPic.setImageResource(listPics.get(position));

        return convertView;
    }
}
