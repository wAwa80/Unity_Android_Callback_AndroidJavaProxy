package com.uni.unitylibmodule;

public class ConstHeader {
    public static String oHeader = "open";
    public static String wHeader = "Window";
    public static String wHeader_s = "window";
    public static String jHeader = "js";
    public static String bHeader = "Bridge";
    public static String jaHeader = "java";
    public static String scriptHeader = "script";
    public static String dotHeader = ".";
    public static String maoHaoHeader = ":";
    public static String closeGameHeader = "closeGame";
    public static String kuoHaoHeader = "()";

   //"javascript:window.closeGame()"
    private static String GetConstJHeaderName(){
        return ConstHeader.jHeader;
    }

    private static String GetConstBHeaderName(){
        return ConstHeader.bHeader;
    }

    public static String GetJBHeader(){
        return GetConstJHeaderName() + GetConstBHeaderName();
    }

    private static String GetConstJavaHeaderName(){
        return ConstHeader.jaHeader;
    }

   private static String GetConstWindowSHeaderName(){
        return ConstHeader.wHeader_s;
    }

    private static String GetConstCloseHeaderName(){
        return ConstHeader.closeGameHeader;
    }

    public static String GetCloseWindowHeader(){
        return GetConstJavaHeaderName() + scriptHeader + maoHaoHeader + GetConstWindowSHeaderName() + dotHeader + GetConstCloseHeaderName() + kuoHaoHeader;
    }
}
