package com.cnettech;

import com.cnettech.util.Common;
import com.cnettech.util.Log4j;
import com.cnettech.util.SqlSessionFactoryManager;
import org.apache.ibatis.session.SqlSession;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 파일 삭제 클래스
 */
public class FileDelete extends Thread {

    final Properties pros = Common.getProperties();

    private boolean running;
    private SqlSession sqlSession = null;

    public FileDelete() {
        Log4j.log.info(String.format("-2-- FileDelete Thread Create"));
    }

    @Override
    public void run() {
        running = true;
        Log4j.log.info(String.format("-2-- FileDelete Thread Start"));
        try {
            while (running) {
                ProcessLocal();
                ProcessStorage();
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void ProcessLocal() {
        try {
            long CheckLocalMonth = Long.parseLong(pros.get("delete.local_month").toString());
            Log4j.log.info(String.format("--3- FileDelete Local Process"));
            Map<String, Object> argMap = new HashMap<String, Object>();
            // DB Connection
            if (sqlSession == null)
                sqlSession = SqlSessionFactoryManager.getSqlSessionFactory().openSession(true);

            String CheckDate = java.time.LocalDate.now().minusMonths(CheckLocalMonth).toString();
            argMap.clear();
//        argMap.put("check_date", java.time.LocalDate.now().toString());
            Log4j.log.info(String.format("[FileDelete Local] Check Data : %s", CheckDate));
            argMap.put("check_date", "2022-08-23");

            List<Map<String, Object>> list = sqlSession.selectList("file.SelectLocalList", argMap);

            int index = 0;
            for (Map<String, Object> item : list) {
//                index++;
//                Log4j.log.info("index :" + index
//                        + " filename :" + item.get("FILELIST"));
                String Filename = item.get("FILELIST").toString();
                File file = new File(Filename);
                if(!file.delete()){ // f.delete 파일 삭제에 성공하면 true, 실패하면 false
                    Log4j.log.info(String.format("[FileDelete Local] 파일 삭제 실패 : %s", Filename));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ProcessStorage() {
        try {
            long CheckStorageMonth = Long.parseLong(pros.get("delete.storage_month").toString());
            Log4j.log.info(String.format("--3- FileDelete Storage Process"));
            Map<String, Object> argMap = new HashMap<String, Object>();
            // DB Connection
            if (sqlSession == null)
                sqlSession = SqlSessionFactoryManager.getSqlSessionFactory().openSession(true);

            String CheckDate = java.time.LocalDate.now().minusMonths(CheckStorageMonth).toString();
            argMap.clear();
            Log4j.log.info(String.format("[FileDelete Storage] Check Data : %s", CheckDate));
            argMap.put("check_date", "2022-08-23");

            List<Map<String, Object>> list = sqlSession.selectList("file.SelectStorageList", argMap);

            int index = 0;
            for (Map<String, Object> item : list) {
//                index++;
//                Log4j.log.info("index :" + index
//                        + " filename :" + item.get("FILELIST"));
                String Filename = item.get("FILELIST").toString();
                File file = new File(Filename);
                if(!file.delete()){ // f.delete 파일 삭제에 성공하면 true, 실패하면 false
                    Log4j.log.info(String.format("[FileDelete Storage] 파일 삭제 실패 : %s", Filename));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}