package com.cnettech;

import com.cnettech.util.Common;
import com.cnettech.util.Log4j;
import sun.misc.Signal;
import sun.misc.SignalHandler;
import java.util.Properties;

/**
 * 파일 관리자
 */
public class FileManager
{
    public static boolean bExit = false;
    public static void main( String[] args )
    {
        Log4j.log.info("---- Program START ----");
        try {
            final Properties pros = Common.getProperties();

            // 파일삭제 프로세스 시작
            Log4j.log.info("1--- FileDelete Thread Call ");
            FileDelete fileDelete = new FileDelete();
            fileDelete.start();

            // 영구 녹취 이동 프로세스 시작
            Log4j.log.info("1--- FileMovePermanent Thread Call ");
            FileMovePermanent fileMovePermanent = new FileMovePermanent();
            fileMovePermanent.start();

            // 녹취 이동 프로세스 시작
            Log4j.log.info("1--- FileMove Thread Call ");
            FileMove fileMove = new FileMove();
            fileMove.start();

            SignalHandler signalHandler = new SignalHandlerImpl( );
            Signal.handle(new Signal("INT"), signalHandler); //Ctrl-C
            Signal.handle(new Signal("TERM"), signalHandler);

            Log4j.log.info("1--- Main Thread Loop ");
            while(!bExit) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Log4j.log.info("1--- Main Thread Stop ");
                }
            }

            fileDelete.stop();
            fileMovePermanent.stop();
            fileMove.stop();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class SignalHandlerImpl implements SignalHandler
    {
        @Override
        public void handle(Signal signal)
        {
            bExit = true;
        }
    }
}
