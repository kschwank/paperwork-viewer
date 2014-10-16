package org.ako.pwv;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import org.ako.pwv.controller.DocumentAdapter;
import org.ako.pwv.controller.DocumentListLoader;
import org.ako.pwv.model.Document;
import org.ako.pwv.model.Documents;

import java.io.File;
import java.text.ParseException;

public class BrowseDocs extends Activity implements AdapterView.OnItemClickListener, TextWatcher, View.OnClickListener {

    private static final int ACTIVITY_CHOOSE_DIR = 3;

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

        Intent chooseFile;
        chooseFile = new Intent("com.estrongs.action.PICK_DIRECTORY");
        chooseFile.putExtra("com.estrongs.intent.extra.TITLE", "Select Paperwork Directory");
        startActivityForResult(chooseFile, ACTIVITY_CHOOSE_DIR);

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case ACTIVITY_CHOOSE_DIR:
                if (data.getData() != null) {
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                    sharedPreferences.edit().putString("paperwork_dir", data.getData().getPath()).apply();
                    reloadDocuments();
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent;

        Document document = documentAdapter.getItem(position);
        if (document.getImageFiles().get(0).getPath().toLowerCase().endsWith("pdf")) {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(document.getImageFiles().get(0)), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        } else {
            intent = new Intent(this, ViewDocs.class);
            intent.putExtra("document", documentAdapter.getItem(position));
        }
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No application available to view PDF", Toast.LENGTH_SHORT).show();
        }
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

        ImageButton clearButton = (ImageButton)findViewById(R.id.clear_search_button);
        clearButton.setOnClickListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        this.documentAdapter.getFilter().filter(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {}

    @Override
    public void onClick(View v) {
        EditText searchTextView = (EditText)findViewById(R.id.search_text_view);
        searchTextView.setText("");
    }
}
