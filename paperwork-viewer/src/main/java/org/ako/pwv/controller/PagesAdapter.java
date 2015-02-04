package org.ako.pwv.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.preference.PreferenceScreen;
import android.view.*;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;
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
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);

        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int maxWidth = size.x;

        int sampleSize = 1;
        if (options.outWidth > maxWidth) {
            sampleSize = (int)Math.pow(2, (int)Math.ceil(Math.log(maxWidth / (double)options.outWidth) / Math.log(0.5)));
        }

        options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inDither = true;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inSampleSize = sampleSize;
        Bitmap srcBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
        viewHolder.imageView.setImageBitmap(srcBitmap);

        return convertView;
    }
}
