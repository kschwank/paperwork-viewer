package org.ako.pwv;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import org.ako.pwv.controller.PagesAdapter;
import org.ako.pwv.model.Document;

public class ViewDocs extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pages_view);

        if (getIntent().getExtras() != null) {
            Document document = (Document)getIntent().getExtras().getSerializable("document");

            if (document != null) {
                PagesAdapter pagesAdapter = new PagesAdapter(this, document.imageFiles);
                ListView pageListView = (ListView)findViewById(R.id.pages_list);
                pageListView.setAdapter(pagesAdapter);
            }
        }
    }
}
