package com.antfact.twitter.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;


@Slf4j
public class AgentList {

    @Value("${isOpenAgen}")
    private Boolean isOpenAgen;

    public boolean getIsOpenAgen() {
        if (isOpenAgen == null) {
            return false;
        }
        return isOpenAgen;
    }

    private static final ArrayList<String> agentList; // 所有代理


    static {
        agentList = new ArrayList();
        String str = "";
        File file = new File("Data/agentlist.txt");
        BufferedReader read = null;
        try {
            read = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        } catch (FileNotFoundException e) {
            log.error("代理文件读取问题", e);
        }
        try {
            while ((str = read.readLine()) != null) {
                String sk = str.trim();
                if (!sk.isEmpty()) {
                    agentList.add(sk);
                }
            }
            read.close();
        } catch (IOException e) {

            log.error("", e);
        }

        for (int i = 0; i < agentList.size(); i++) {
            log.info(agentList.get(i));
        }
        log.info("在文件中导入agent:" + agentList.size());
    }

    public static String getAgent() {
        int size= agentList.size();
        if(size==1){
            return agentList.get(0);
        } else {
            Random ra =new Random();
            int flag=ra.nextInt(size);
            return agentList.get(flag);
        }
    }

}
