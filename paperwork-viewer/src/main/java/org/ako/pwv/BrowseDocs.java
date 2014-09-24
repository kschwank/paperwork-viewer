package org.ako.pwv;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import org.ako.pwv.controller.DocumentAdapter;
import org.ako.pwv.controller.DocumentListLoader;
import org.ako.pwv.model.Documents;

import java.io.File;
import java.text.ParseException;

public class BrowseDocs extends Activity implements AdapterView.OnItemClickListener {

    Documents documents = null;

    String docRoot = Environment.getExternalStorageDirectory().getPath()+"/documents/BTSync/Paperwork";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.browse_view);

        Toast.makeText(this, "Loading documents", Toast.LENGTH_LONG).show();
        try {
            if (new File(docRoot).exists()) {
                documents = DocumentListLoader.load(docRoot);
            } else {
                Toast.makeText(this, docRoot + " does not exist", Toast.LENGTH_LONG).show();
                return;
            }
        } catch (ParseException e) {
            Toast.makeText(this, "Loading failed", Toast.LENGTH_SHORT).show();
        }

        DocumentAdapter documentAdapter = new DocumentAdapter(this, documents);
        ListView docListView = (ListView)findViewById(R.id.browse_list_view);
        docListView.setOnItemClickListener(this);
        docListView.setAdapter(documentAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ViewDocs.class);
        intent.putExtra("document", documents.getList().get(position));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.browse_menu, menu);

        return true;
    }
}
