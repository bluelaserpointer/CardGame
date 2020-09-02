package com.example.accessingdatamysql;

// This class contains all constants referenced by the project.
public class GlobalConstants {
    public static final Integer general_page_size = 10; // the general page size for listing entities.
    public static final boolean DO_DEBUG = false;
    public static void printIfDoDebug(Object object) {
        if(DO_DEBUG) {
            System.out.println(object.toString());
        }
    }
}
