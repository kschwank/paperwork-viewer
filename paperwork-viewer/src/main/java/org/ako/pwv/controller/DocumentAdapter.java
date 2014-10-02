package org.ako.pwv.controller;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import org.ako.pwv.R;
import org.ako.pwv.model.Document;
import org.ako.pwv.model.Documents;
import org.ako.pwv.view.DocumentViewHolder;

import java.text.SimpleDateFormat;
import java.util.Arrays;

public class DocumentAdapter extends BaseAdapter {

    SimpleDateFormat DateFormat = new SimpleDateFormat("dd.MM.yyyy");

    Context context;
    Documents documents;

    public DocumentAdapter(Context context, Documents documents) {
        this.context = context;
        this.documents = documents;
    }

    @Override
    public int getCount() {
        return documents.getList().size();
    }

    @Override
    public Document getItem(int position) {
        return documents.getList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DocumentViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.browse_list_item, parent, false);
            viewHolder = new DocumentViewHolder();
            viewHolder.thumbnailImage = (ImageView) convertView.findViewById(R.id.browse_list_item_thumbnail);
            viewHolder.dateText = (TextView) convertView.findViewById(R.id.browse_list_item_date);
            viewHolder.tagsText = (TextView) convertView.findViewById(R.id.browse_list_item_tags);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (DocumentViewHolder)convertView.getTag();
        }

        Document item = getItem(position);
        viewHolder.thumbnailImage.setImageBitmap(item.thumbnailFiles.size() > 0 ?
                BitmapFactory.decodeFile(item.thumbnailFiles.get(0).getAbsolutePath())
                : null);
        viewHolder.dateText.setText(DateFormat.format(item.date));
        viewHolder.tagsText.setText(item.tags == null ? "" : Arrays.deepToString(item.tags.toArray()));

        return convertView;
    }
}
