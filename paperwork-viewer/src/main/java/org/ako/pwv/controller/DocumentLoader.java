package org.ako.pwv.controller;

import org.ako.pwv.model.Document;
import org.ako.pwv.model.DocumentList;

import java.io.File;
import java.io.FileFilter;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DocumentLoader {

   public static DocumentList load(String path) throws ParseException {

       DocumentList documentList = new DocumentList();
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
       }
       return documentList;
   }
}
