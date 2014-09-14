package org.ako.pwv.model;

import android.media.Image;

import java.io.File;
import java.util.Date;

public class Document {

    public File path;
    public Date date;
    public String text;
    public String[] tags;
    public File[] thumbnailFiles;
    public File[] imageFiles;
}
