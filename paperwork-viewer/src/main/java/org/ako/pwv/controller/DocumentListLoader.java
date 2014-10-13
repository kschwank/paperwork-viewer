package org.ako.pwv.controller;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import org.ako.pwv.model.Document;
import org.ako.pwv.model.Documents;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class DocumentListLoader {

   static final Pattern documentDir = Pattern.compile("[0-9]{8}_[0-9]{4}_[0-9]{2}.*");
   static final Pattern thumbnailFile = Pattern.compile("paper\\.[0-9]+\\.thumb\\.jpg");
   static final Pattern pageFile = Pattern.compile("paper\\.[0-9]\\.jpg");
   static final Pattern wordsFile = Pattern.compile("paper\\.[0-9]\\.words");

   public static Documents load(String path) throws ParseException {

       Documents documents = new Documents();
       File root = new File(path);

       SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

       for (File docPath : root.listFiles(new FileFilter() {
           @Override
           public boolean accept(File path) {
               return path.isDirectory() && documentDir.matcher(path.getName()).matches();
           }
       })) {
           Document doc = new Document();
           doc.setDate(format.parse(docPath.getName().substring(0, 8)));
           doc.setThumbnailFiles(docPath.listFiles(new FileFilter() {
               @Override
               public boolean accept(File path) {
                   return path.isFile() && thumbnailFile.matcher(path.getName()).matches();
               }
           }));
           doc.setImageFiles(docPath.listFiles(new FileFilter() {
               @Override
               public boolean accept(File path) {
                   return path.isFile() && pageFile.matcher(path.getName()).matches();
               }
           }));
           List<File> wordsFiles = new ArrayList<>();
           wordsFiles.addAll(Arrays.asList(docPath.listFiles(new FileFilter() {
               @Override
               public boolean accept(File path) {
                   return path.isFile() && wordsFile.matcher(path.getName()).matches();
               }
           })));
           String words = "";
           for (File file : wordsFiles) {
               try {
                   words += " " + Joiner.on(' ').join(com.google.common.io.Files.readLines(file, Charsets.UTF_8));
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
           doc.setText(words.toLowerCase());

           File labelFiles = new File(docPath.getAbsolutePath() + "/labels");
           if (labelFiles.exists()) {
               try (BufferedReader fileReader = new BufferedReader(new FileReader(labelFiles))) {
                   List<String> tagsList = new ArrayList<>();
                   while (fileReader.ready()) {
                       String line = fileReader.readLine();
                       String tag = line.substring(0, line.indexOf(','));
                       tagsList.add(tag);
                   }
                   doc.setTags(tagsList);
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
           documents.add(doc);
       }
       return documents;
   }
}
