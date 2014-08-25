package org.ako.pwv;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.ako.pwv.controller.DocumentAdapter;
import org.ako.pwv.controller.DocumentLoader;
import org.ako.pwv.model.Document;
import org.ako.pwv.model.DocumentList;

import java.io.File;
import java.text.ParseException;
import java.util.Date;

public class BrowseDocs extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.browse_view);

        Toast.makeText(this, "Loading documents", Toast.LENGTH_LONG);
        DocumentList documents = null;
        try {
            documents = DocumentLoader.load("/sdcard/documents/BTSync/Paperwork");
        } catch (ParseException e) {
            Toast.makeText(this, "Loading failed", Toast.LENGTH_LONG);
        }

        DocumentAdapter documentAdapter = new DocumentAdapter(this, documents);
        ListView docListView = (ListView)findViewById(R.id.doc_list_view);

        docListView.setAdapter(documentAdapter);
    }
}
