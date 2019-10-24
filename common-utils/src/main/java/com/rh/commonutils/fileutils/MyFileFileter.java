package com.rh.commonutils.fileutils;

import java.io.File;
import java.io.FileFilter;

public class MyFileFileter implements FileFilter{

    @Override
    public boolean accept(File pathname) {
        if(pathname.isDirectory()){
            return true;
        }
        return false;
    }

}