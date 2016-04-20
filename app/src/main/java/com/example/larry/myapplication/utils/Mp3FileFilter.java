package com.example.larry.myapplication.utils;

import java.io.File;
import java.io.FileFilter;

/**
 * Created by liaoxiang on 2016/4/19.
 */
public class Mp3FileFilter implements FileFilter {
    @Override
    public boolean accept(File pathname) {
        String filename = pathname.getPath();
        if (pathname.isDirectory())
            return true;
        if(filename.endsWith(".mp3"))
            return true;
        else
            return false;
    }
}
