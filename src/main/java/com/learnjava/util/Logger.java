package com.learnjava.util;

public class Logger {

    public static void log(String message){
        System.out.println("[" + Thread.currentThread().getName() +"] - " + message);
    }
}
