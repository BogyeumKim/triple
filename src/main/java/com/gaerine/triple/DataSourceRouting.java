package com.gaerine.triple;


public class DataSourceRouting {

    private static final ThreadLocal<String> routing = new ThreadLocal<>();

    public static void setDbType(String dbType) {
        routing.set(dbType);
    }

    public static String getDbType() {
        return (String) routing.get();
    }

    public static void clearDbType() {
        routing.remove();
    }
}
