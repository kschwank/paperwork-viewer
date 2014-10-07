package org.ako.pwv;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import org.ako.pwv.controller.DocumentAdapter;
import org.ako.pwv.controller.DocumentListLoader;
import org.ako.pwv.model.Documents;

import java.io.File;
import java.text.ParseException;

public class BrowseDocs extends Activity implements AdapterView.OnItemClickListener, TextWatcher {

    Documents documents = null;
    DocumentAdapter documentAdapter = null;
    String docRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.browse_view);

        reloadDocuments();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.browse_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent();
        intent.setClass(BrowseDocs.this, SetPreferences.class);
        startActivityForResult(intent, 0);

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        reloadDocuments();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(this, ViewDocs.class);
        intent.putExtra("document", documentAdapter.getItem(position));
        startActivity(intent);
    }

    private void reloadDocuments() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        docRoot = sharedPreferences.getString("paperwork_dir", Environment.getExternalStorageDirectory().getPath()+"/Paperwork");

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

        documentAdapter = new DocumentAdapter(this, documents);
        ListView docListView = (ListView)findViewById(R.id.browse_list_view);
        docListView.setAdapter(documentAdapter);
        docListView.setTextFilterEnabled(true);
        docListView.setOnItemClickListener(this);

        EditText searchTextView = (EditText)findViewById(R.id.search_text_view);
        searchTextView.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        this.documentAdapter.getFilter().filter(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {}
}
