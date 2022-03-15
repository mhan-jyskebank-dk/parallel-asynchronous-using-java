package com.learnjava.util;

import org.apache.commons.lang3.time.StopWatch;

import static com.learnjava.util.Logger.log;
import static java.lang.Thread.sleep;

public class Timer {

    public static StopWatch stopWatch = new StopWatch();

    public static void startTimer(){
        stopWatch.reset();
        stopWatch.start();
    }

    public static void timeTaken(){
        stopWatch.stop();
        log("Total time taken: " + stopWatch.getTime());
    }

    public static void delay(long delayMilliSeconds)  {
        try{
            sleep(delayMilliSeconds);
        } catch (Exception e) {
            log("Exception is :" + e.getMessage());
        }
    }
}