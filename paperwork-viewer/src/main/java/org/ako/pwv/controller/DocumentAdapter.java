package org.ako.pwv.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import org.ako.pwv.R;
import org.ako.pwv.model.Document;
import org.ako.pwv.model.Documents;
import org.ako.pwv.view.DocumentViewHolder;

public class DocumentAdapter extends BaseAdapter {

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
            convertView = LayoutInflater.from(context).inflate(R.layout.document_list_item, parent, false);
            viewHolder = new DocumentViewHolder();
            viewHolder.label = (TextView) convertView.findViewById(R.id.doc_list_item);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (DocumentViewHolder)convertView.getTag();
        }

        viewHolder.label.setText(getItem(position).date.toString());

        return convertView;
    }
}
