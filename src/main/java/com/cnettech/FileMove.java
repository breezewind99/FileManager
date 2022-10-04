package com.cnettech;

import com.cnettech.util.Log4j;

/**
 * 녹취 이동 클래스
 */
public class FileMove extends Thread {

    private boolean running;

    public FileMove() {
        Log4j.log.info(String.format("-2-- FileMove Thread Create"));
    }

    @Override
    public void run() {
        running = true;
        Log4j.log.info(String.format("-2-- FileMove Thread Start"));
        try {
            while (running) {
                Process();
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void Process() {
        Log4j.log.info(String.format("--3- FileMove Process"));
    }
}
