package org.ako.pwv.controller;

import android.widget.Toast;
import org.ako.pwv.model.Document;
import org.ako.pwv.model.Documents;

import java.io.File;
import java.io.FileFilter;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DocumentListLoader {

   public static Documents load(String path) throws ParseException {

       Documents documents = new Documents();
       File root = new File(path);
       assert root.isDirectory();

       SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

       for (File docPath : root.listFiles(new FileFilter() {
           @Override
           public boolean accept(File pathname) {
               return pathname.isDirectory() && pathname.getName().matches("[0-9]{8}_[0-9]{4}_[0-9]{2}.*");
           }
       })) {
           Document doc = new Document();
           doc.path = docPath;
           doc.date = format.parse(docPath.getName().substring(0,7));
           doc.thumbnailFiles = docPath.listFiles(new FileFilter() {
               @Override
               public boolean accept(File pathname) {
                   return pathname.isFile() && pathname.getName().matches("paper\\.[0-9]+\\.thumb\\.jpg");
               }
           });
           doc.text = "Dummy DOC Text";
           doc.tags = new String[] { "Dummy Tag" };
       }
       return documents;
   }
}
