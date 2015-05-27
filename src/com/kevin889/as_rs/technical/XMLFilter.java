package com.kevin889.as_rs.technical;

import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;

/**
 * Created by kevin889 on 16-04-15.
 */
public class XMLFilter extends FileFilter {

    /**
     * Deze class zorgt er voor dat alleen XML bestanden geselcteerd kunnen worden in de fileChooser
     */

    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = getExtension(f);
        if (extension != null) {
            if (extension.equals("xml")) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    //The description of this filter
    public String getDescription() {
        return ".xml";
    }

    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
}
