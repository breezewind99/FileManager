package com.cnettech;

import com.cnettech.util.Common;
import com.cnettech.util.Log4j;
import com.cnettech.util.SqlSessionFactoryManager;
import org.apache.ibatis.session.SqlSession;

import java.io.File;
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

    private final String  sMainBackup = pros.getProperty("system.main","0");
    public FileDelete() {
        Log4j.log.info(String.format("-2-- FileDelete Thread Create"));
    }

    @Override
    public void run() {
        running = true;
        Log4j.log.info(String.format("-2-- FileDelete Thread Start"));
        try {
            while (running) {
                String NowDate = java.time.LocalDate.now().toString();
                ProcessLocal(NowDate);
                ProcessStorage(NowDate);
                Thread.sleep(60000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void ProcessLocal(String ProcessDate) {
        try {
            String LocalCheckDate = pros.getProperty("delete.local.date","");
            if (LocalCheckDate.equals(ProcessDate)) return;

            long CheckLocalMonth = Long.parseLong(pros.get("delete.local.month").toString());
            Log4j.log.info(String.format("--3- FileDelete Local Process"));
            Map<String, Object> argMap = new HashMap<String, Object>();
            // DB Connection
            if (sqlSession == null)
                sqlSession = SqlSessionFactoryManager.getSqlSessionFactory().openSession(true);

            String CheckDate = java.time.LocalDate.now().minusMonths(CheckLocalMonth).toString();
            Log4j.log.info(String.format("[FileDelete Local] Check Date : %s", CheckDate));
//            argMap.put("check_date", "2022-08-23");
            argMap.put("check_date", CheckDate);
            argMap.put("main_backup", sMainBackup);

            List<Map<String, Object>> list = sqlSession.selectList("file.SelectLocalList", argMap);

//            int index = 0;
            for (Map<String, Object> item : list) {
//                index++;
//                Log4j.log.info("index :" + index
//                        + " filename :" + item.get("FILELIST"));
                String Filename = item.get("FILELIST").toString();
                File file = new File(Filename);
                if(!file.delete()){ // f.delete 파일 삭제에 성공하면 true, 실패하면 false
                    Log4j.log.warn(String.format("[FileDelete Local] 파일 삭제 실패 : %s", Filename));
                }
            }
            // 하루에 한번 처리
            pros.put("delete.local_date", LocalCheckDate);
        } catch (Exception e) {
            e.printStackTrace();
            Log4j.log.error(e.getMessage());
        }
    }

    public void ProcessStorage(String ProcessDate) {
        try {
            String LocalCheckDate = pros.getProperty("delete.storage.date","");
            if (LocalCheckDate.equals(ProcessDate)) return;

            long CheckStorageMonth = Long.parseLong(pros.get("delete.storage.month").toString());
            Log4j.log.info(String.format("--3- FileDelete Storage Process"));
            Map<String, Object> argMap = new HashMap<String, Object>();
            // DB Connection
            if (sqlSession == null)
                sqlSession = SqlSessionFactoryManager.getSqlSessionFactory().openSession(true);

            String CheckDate = java.time.LocalDate.now().minusMonths(CheckStorageMonth).toString();
            Log4j.log.info(String.format("[FileDelete Storage] Check Date : %s", CheckDate));
//            argMap.put("check_date", "2022-08-23");
            argMap.put("check_date", CheckDate);
            argMap.put("main_backup", sMainBackup);

            List<Map<String, Object>> list = sqlSession.selectList("file.SelectStorageList", argMap);

//            int index = 0;
            for (Map<String, Object> item : list) {
//                index++;
//                Log4j.log.info("index :" + index
//                        + " filename :" + item.get("FILELIST"));
                String Filename = item.get("FILELIST").toString();
                File file = new File(Filename);
                if(!file.delete()){ // f.delete 파일 삭제에 성공하면 true, 실패하면 false
                    Log4j.log.warn(String.format("[FileDelete Storage] 파일 삭제 실패 : %s", Filename));
                }
            }

            // 하루에 한번 처리
            pros.put("delete.storage_date", LocalCheckDate);
        } catch (Exception e) {
            e.printStackTrace();
            Log4j.log.error(e.getMessage());
        }
    }
}