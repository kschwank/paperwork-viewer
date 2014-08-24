package org.ako.pwv;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import org.ako.pwv.controller.DocumentAdapter;
import org.ako.pwv.model.Document;
import org.ako.pwv.model.DocumentList;

import java.io.File;
import java.util.Date;

public class BrowseDocs extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.browse_view);

        DocumentList documents = new DocumentList();
        for (int i = 0; i<20; i++) {
            Document doc = new Document();
            doc.date = new Date(100000*i);
            doc.path = new File("/somewhere");
            documents.getList().add(i, doc);
        }

        DocumentAdapter documentAdapter = new DocumentAdapter(this, documents);
        ListView docListView = (ListView)findViewById(R.id.doc_list_view);

        docListView.setAdapter(documentAdapter);
    }
}
