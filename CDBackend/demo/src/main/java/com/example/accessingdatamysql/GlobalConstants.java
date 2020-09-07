package com.example.accessingdatamysql;

// This class contains all constants referenced by the project.
public class GlobalConstants {
    public static final Integer general_page_size = 10; // the general page size for listing entities.
    public static boolean doDebug = true;
    public static void printIfDoDebug(Object object) {
        if(doDebug) {
            System.out.println(object.toString());
        }
    }
}
