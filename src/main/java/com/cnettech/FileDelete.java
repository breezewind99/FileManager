package com.cnettech;

import com.cnettech.util.Log4j;

/**
 * 파일 삭제 클래스
 */
public class FileDelete extends Thread {

    private boolean running;

    public FileDelete() {
        Log4j.log.info(String.format("---2 FileDelete Thread Start"));
    }

    @Override
    public void run() {
        running = true;

        while (running) {

        }

    }
}
