package com.antfact.transit.job;


public class Statistics {
    private volatile static Long conunt;

    private volatile static Long dayConunt;

    private Statistics() {
    }

    public static Long getConunt() {
        if (conunt == null) {
            synchronized (Statistics.class) {
                if (conunt == null) {
                    conunt = 0L;
                }
            }
        }
        return conunt;
    }
    public static Long add(long addCount) {
        if (conunt == null) {
            synchronized (Statistics.class) {
                if (conunt == null) {
                    conunt = addCount;
                }
            }
        } else if(conunt !=null ){
            conunt=conunt+addCount;
        }
        return conunt;
    }
    public static long setConunt(long setCount) {
        long flag=0l;
        if (conunt == null) {
            synchronized (Statistics.class) {
                if (conunt == null) {
                    conunt = 0L;
                }
            }
        } else if(conunt !=null ){
            flag=conunt;
            setDayConunt(flag);
            conunt=setCount;
        }
        return flag;
    }
    public static long getDayConunt(long setDayCount) {
        long flag=0l;
        if (dayConunt == null) {
            synchronized (Statistics.class) {
                if (dayConunt == null) {
                    dayConunt = 0L;
                }
            }
        } else if(dayConunt !=null ){
            flag=dayConunt;
            dayConunt=setDayCount;
        }
        return flag;
    }
    public static long setDayConunt(long setDayCount) {
        if (dayConunt == null) {
            synchronized (Statistics.class) {
                if (dayConunt == null) {
                    dayConunt = 0L;
                }
            }
        } else if(dayConunt !=null ){
            dayConunt=dayConunt+setDayCount;
        }
        return dayConunt;
    }
}
