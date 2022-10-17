package com.cnettech;

import com.cnettech.util.Log4j;

/**
 * 영구녹취 이동 클래스
 */
public class FileMovePermanent extends Thread {

    private boolean running;

    public FileMovePermanent() {
        Log4j.log.info(String.format("-2-- FileMovePermanent Thread Create"));
    }

    @Override
    public void run() {
        running = true;
        Log4j.log.info(String.format("-2-- FileMovePermanent Thread Start"));
        try {
            while (running) {
                Process();
                Thread.sleep(60000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void Process() {
        Log4j.log.info(String.format("--3- FileMovePermanent Process"));
    }

    /**
     * 영구녹취 DB자료 받아오기
     */
    public void GetPermanent(){

    }
}
