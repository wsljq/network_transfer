package com.antfact.transit.bean;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

@Slf4j
public class CompanyList {
    private static final ArrayList<String> companyList; // 所有代理


    static {
        companyList = new ArrayList();
        String str = "";
        File file = new File("Data/companylist.txt");
        BufferedReader read = null;
        try {
            read = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        } catch (FileNotFoundException e) {
            log.error("回传到公司内网的文件读取问题", e);
        }
        try {
            while ((str = read.readLine()) != null) {
                String sk = str.trim();
                if (!sk.isEmpty()) {
                    companyList.add(sk);
                }
            }
            read.close();
        } catch (IOException e) {

            log.error("", e);
        }

        for (int i = 0; i < companyList.size(); i++) {
            log.info(companyList.get(i));
        }
        log.info("在文件中导入company:" + companyList.size());
    }

    public static String getAgent() {
        int size= companyList.size();
        if(size==1){
            return companyList.get(0);
        } else {
            Random ra =new Random();
            int flag=ra.nextInt(size);
            return companyList.get(flag);
        }
    }
}
