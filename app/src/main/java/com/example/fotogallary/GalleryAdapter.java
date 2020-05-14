package com.example.fotogallary;



import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class GalleryAdapter extends BaseAdapter{

    private Context mContext;
    public ArrayList<String> data = new ArrayList<>();

    public GalleryAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<String> data){
        if(this.data.size() > 0){
            this.data.clear();
        }
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        Glide.with(mContext).load(data.get(position)).into(imageView);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(340, 350));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(mContext, FullScreen.class);

                intent.putExtra("position", position);
                intent.putStringArrayListExtra("data", data);

                mContext.startActivity(intent);

            }
        });

        return imageView;

    }
}




