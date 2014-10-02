package org.ako.pwv.model;

import java.io.File;
import java.io.Serializable;
import java.util.*;

public class Document implements Serializable, Comparable<Document> {

    Date date;
    String text;
    List<String> tags = Collections.emptyList();
    List<File> thumbnailFiles = Collections.emptyList();
    List<File> imageFiles = Collections.emptyList();

    private static FileNameComparator filenameComparator = new FileNameComparator();

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getTags() {
        return tags;
    }

    public List<File> getThumbnailFiles() {
        return thumbnailFiles;
    }

    public List<File> getImageFiles() {
        return imageFiles;
    }

    @Override
    public int compareTo(Document another) {
        return date.compareTo(another.date);
    }

    public void setThumbnailFiles(File[] thumbnailFiles) {
        this.thumbnailFiles = Arrays.asList(thumbnailFiles);
        Collections.sort(this.thumbnailFiles, filenameComparator);
    }

    public void setImageFiles(File[] imageFiles) {
        this.imageFiles = Arrays.asList(imageFiles);
        Collections.sort(this.imageFiles, filenameComparator);
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
        Collections.sort(tags);
    }

    private static class FileNameComparator implements Comparator<File> {

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
