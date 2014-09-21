package org.ako.pwv.model;

import java.io.File;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

public class Document implements Serializable, Comparable<Document> {

    public File path;
    public Date date;
    public String text;
    public String[] tags;
    public File[] thumbnailFiles;
    public File[] imageFiles;

    @Override
    public int compareTo(Document another) {
        return date.compareTo(another.date);
    }
}
