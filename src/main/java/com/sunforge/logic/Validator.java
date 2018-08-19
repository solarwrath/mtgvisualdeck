package com.sunforge.logic;

import java.io.File;
import java.io.IOException;

public class Validator {
    public static boolean isValidFilePath(String givenPath) {

        //Checking wheteher we can operate with given file path by attempting to create a new file.

        File f = new File(givenPath);
        try {
            f.createNewFile();
            f.delete();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
