package com.antfact.twitter.bean;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;


@Slf4j
public class VpsList {
    private static final ArrayList<String> vpsList; // 所有代理

    static {
        vpsList = new ArrayList();
        String str = "";
        File file = new File("Data/vpslist.txt");
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
                    vpsList.add(sk);
                }
            }
            read.close();
        } catch (IOException e) {

            log.error("", e);
        }

        for (int i = 0; i < vpsList.size(); i++) {
            log.info(vpsList.get(i));
        }
        log.info("在文件中导入company:" + vpsList.size());
    }

    public static String getVps() {
        int size= vpsList.size();
        if(size==1){
            return vpsList.get(0);
        } else {
            Random ra =new Random();
            int flag=ra.nextInt(size);
            return vpsList.get(flag);
        }
    }
}
