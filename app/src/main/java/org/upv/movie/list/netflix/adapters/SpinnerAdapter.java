package org.upv.movie.list.netflix.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.upv.movie.list.netflix.R;

/**
 * Created by Lionel on 10/12/2017.
 */

public class SpinnerAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private String[] mContentArray;
    private Integer[] mImageArray;

    public SpinnerAdapter(Context context, int resource, String[] objects, Integer[] imageArray){
        super(context, R.layout.spinner_item_layout, R.id.spinnerTextView, objects);
        mContext = context;
        mContentArray = objects;
        mImageArray = imageArray;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent){
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        return getCustomView(position, convertView, parent);
    }


    public View getCustomView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.spinner_item_layout, parent, false);

        TextView textView = (TextView) row.findViewById(R.id.spinnerTextView);
        textView.setText(mContentArray[position]);

        ImageView imageView = (ImageView)row.findViewById(R.id.spinnerImage);
        imageView.setImageResource(mImageArray[position]);

        return row;
    }
}
