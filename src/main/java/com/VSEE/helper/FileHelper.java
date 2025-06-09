package com.VSEE.helper;

import java.io.File;

public class FileHelper {
    /**
     * get correct type of json file;
     *
     * @param jsonFilePath
     * @return
     */
    public static String getCorrectJsonFilePath(String jsonFilePath) {
        String correctXlFilePath = System.getProperty("user.dir") + File.separator + jsonFilePath;
        return correctXlFilePath;
    }
}
