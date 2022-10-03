package com.cnettech;

import com.cnettech.util.Common;
import com.cnettech.util.Log4j;

import java.net.SocketException;
import java.util.Properties;

/**
 * Hello world!
 *
 */
public class FileManager
{
    public static void main( String[] args )
    {
        Log4j.log.info("---- Program START ----");
        try {
            final Properties pros = Common.getProperties();

            // 파일삭제 프로세스 시작
            Log4j.log.info("---1 FileDelete Thread Call ");
            FileDelete fileDelete = new FileDelete();
            fileDelete.start();

            // 영구 녹취 이동 프로세스 시작
            Log4j.log.info("---1 FileMovePermanent Thread Call ");
            FileMovePermanent fileMovePermanent = new FileMovePermanent();
            fileMovePermanent.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
