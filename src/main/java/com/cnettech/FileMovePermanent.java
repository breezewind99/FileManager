package com.cnettech;

import com.cnettech.util.Log4j;

/**
 * 영구녹취 이동 클래스
 */
public class FileMovePermanent extends Thread {

    private boolean running;

    public FileMovePermanent() {
        Log4j.log.info(String.format("---2 FileMovePermanent Thread Start"));
    }

    @Override
    public void run() {
        running = true;

        while (running) {

        }

    }
}
