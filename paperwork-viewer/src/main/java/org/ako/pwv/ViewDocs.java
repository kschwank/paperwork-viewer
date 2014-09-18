package org.ako.pwv;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import org.ako.pwv.model.Document;

public class ViewDocs extends Activity {

    Document document = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.document_view);

        if (getIntent().getExtras() != null) {
            document = (Document)getIntent().getExtras().getSerializable("document");

            if (document != null) {
                ImageView imageView = (ImageView)findViewById(R.id.doc_single_view);

                if (document.imageFiles.length > 0) {
                    imageView.setImageBitmap(BitmapFactory.decodeFile(document.imageFiles[0].getAbsolutePath()));
                } else {
                    imageView.setImageBitmap(null);
                }
            }
        }
    }
}
