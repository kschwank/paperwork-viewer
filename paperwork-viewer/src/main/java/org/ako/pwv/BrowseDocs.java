package org.ako.pwv;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ListView;
import android.widget.Toast;
import org.ako.pwv.controller.DocumentAdapter;
import org.ako.pwv.controller.DocumentListLoader;
import org.ako.pwv.model.Documents;

import java.io.File;
import java.text.ParseException;

public class BrowseDocs extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.browse_view);

        Toast.makeText(this, "Loading documents", Toast.LENGTH_LONG).show();
        Documents documents = null;
        try {
            String docRoot = Environment.getExternalStorageDirectory().getPath()+"/documents/BTSync/Paperwork";
            if (new File(docRoot).exists()) {
                documents = DocumentListLoader.load(docRoot);
            } else {
                Toast.makeText(this, docRoot + " does not exist", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (ParseException e) {
            Toast.makeText(this, "Loading failed", Toast.LENGTH_SHORT).show();
        }

        DocumentAdapter documentAdapter = new DocumentAdapter(this, documents);
        ListView docListView = (ListView)findViewById(R.id.browse_list_view);

        docListView.setAdapter(documentAdapter);
    }
}
