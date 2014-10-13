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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class DocumentAdapter extends BaseAdapter {

    SimpleDateFormat DateFormat = new SimpleDateFormat("dd.MM.yyyy");

    Context context;
    Documents documents;
    ArrayList<Document> filteredDocuments;

    DocumentFilter filter;

    public DocumentAdapter(Context context, Documents documents) {
        this.context = context;
        this.documents = documents;
        filteredDocuments = new ArrayList<>();
        filteredDocuments.addAll(documents.getList());
    }

    @Override
    public int getCount() {
        return filteredDocuments.size();
    }

    @Override
    public Document getItem(int position) {
        return filteredDocuments.get(position);
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
        viewHolder.thumbnailImage.setImageBitmap(item.getThumbnailFiles().size() > 0 ?
                BitmapFactory.decodeFile(item.getThumbnailFiles().get(0).getAbsolutePath())
                : null);
        viewHolder.dateText.setText(DateFormat.format(item.getDate()));
        viewHolder.tagsText.setText(Arrays.deepToString(item.getTags().toArray()));

        return convertView;
    }

    public Filter getFilter() {
        if (filter == null) {
            filter = new DocumentFilter();
        }
        return filter;
    }

    private class DocumentFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            constraint = constraint.toString().toLowerCase(Locale.getDefault());
            FilterResults result = new FilterResults();

            if (constraint.toString().length() > 0) {
                ArrayList<Document> matchingDocuments = new ArrayList<>();
                for (Document document : documents.getList()) {
                    if (document.getText().contains(constraint)) {
                        matchingDocuments.add(document);
                    } else {
                        for (String tag : document.getTags()) {
                            if (tag.toLowerCase(Locale.getDefault()).contains(constraint)) {
                                matchingDocuments.add(document);
                                break;
                            }
                        }
                    }
                }
                result.values = matchingDocuments;
                result.count = matchingDocuments.size();
            } else {
                synchronized (this) {
                    ArrayList<Document> allDocuments = new ArrayList<>();
                    allDocuments.addAll(documents.getList());
                    result.values = allDocuments;
                    result.count = allDocuments.size();
                }
            }
            return result;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            filteredDocuments.clear();
            filteredDocuments.addAll((ArrayList<Document>) results.values);
            notifyDataSetChanged();
        }
    }
}
