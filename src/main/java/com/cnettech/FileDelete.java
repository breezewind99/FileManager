package com.cnettech;

import com.cnettech.util.Log4j;

/**
 * 파일 삭제 클래스
 */
public class FileDelete extends Thread {

    private boolean running;

    public FileDelete() {
        Log4j.log.info(String.format("-2-- FileDelete Thread Create"));
    }

    @Override
    public void run() {
        running = true;
        Log4j.log.info(String.format("-2-- FileDelete Thread Start"));
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
        Log4j.log.info(String.format("--3- FileDelete Process"));
    }
}
