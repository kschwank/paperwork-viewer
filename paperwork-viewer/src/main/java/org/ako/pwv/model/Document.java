package org.ako.pwv.model;

import java.io.File;
import java.io.Serializable;
import java.util.*;

public class Document implements Serializable, Comparable<Document> {

    public File path;
    public Date date;
    public String text;
    public List<String> tags = Collections.emptyList();
    public List<File> thumbnailFiles = Collections.emptyList();
    public List<File> imageFiles = Collections.emptyList();

    @Override
    public int compareTo(Document another) {
        return date.compareTo(another.date);
    }

    public void setThumbnailFiles(File[] thumbnailFiles) {
        this.thumbnailFiles = Arrays.asList(thumbnailFiles);
        Collections.sort(this.thumbnailFiles, new FileNameComparator());
    }

    public void setImageFiles(File[] imageFiles) {
        this.imageFiles = Arrays.asList(imageFiles);
        Collections.sort(this.imageFiles, new FileNameComparator());
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
        Collections.sort(tags);
    }

    private class FileNameComparator implements Comparator<File> {

        @Override
        public int compare(File lhs, File rhs) {
            return lhs.getAbsoluteFile().compareTo(rhs.getAbsoluteFile());
        }

        @Override
        public boolean equals(Object object) {
            return this.equals(object);
        }
    }
}
