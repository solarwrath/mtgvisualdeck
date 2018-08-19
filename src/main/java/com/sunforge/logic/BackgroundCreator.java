package com.sunforge.logic;

import java.awt.image.BufferedImage;

public class BackgroundCreator {
    public static BufferedImage createBackground(int givenRows){
        //TODO Calculate the proper variables
        //
        return new BufferedImage(3000, givenRows * 150 + 1500, 1);
    }
}
