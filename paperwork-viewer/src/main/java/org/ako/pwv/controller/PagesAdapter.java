package org.ako.pwv.controller;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import org.ako.pwv.R;
import org.ako.pwv.view.PageViewHolder;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class PagesAdapter extends BaseAdapter {

    Context context;
    List<File> imageFiles = Collections.emptyList();

    public PagesAdapter(Context context, List<File> imageFiles) {
        this.context = context;
        this.imageFiles = imageFiles;
    }

    @Override
    public int getCount() {
        return imageFiles.size();
    }

    @Override
    public Object getItem(int position) {
        return imageFiles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PageViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.pages_list_item, parent, false);
            viewHolder = new PageViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.page_item_image);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (PageViewHolder)convertView.getTag();
        }

        File imageFile = (File)getItem(position);
        viewHolder.imageView.setImageBitmap(BitmapFactory.decodeFile(imageFile.getAbsolutePath()));

        return convertView;
    }
}
