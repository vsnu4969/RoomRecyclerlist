/**
 * Package : com.example.vichu.roomrecyclerlist
 * File Name: TraceLog.java
 * Brief: Used to get Tracelog functionalities
 * RoomRecyclerList Project is Strictly Used for Study Purpose Only.
 * Author : Vishnu Muraleedharan.
 **/

package com.example.vichu.roomrecyclerlist.log;

import android.util.Log;


/**
 * @brief    Used to get Tracelog functionalities.
 *
 */
public class TraceLog {

    private static final String TAG = "[VISHNU] ";

    public static void entryLog() {
        StackTraceElement element = Thread.currentThread().getStackTrace()[3];
        String className = element.getClassName();
        className = className.substring(className.lastIndexOf(".") + 1, className.length());
        String methodName = element.getMethodName();
        Log.i(TAG + className, methodName + " >>> Entry");
    }

    public static void exitLog() {
        StackTraceElement element = Thread.currentThread().getStackTrace()[3];
        String className = element.getClassName();
        className = className.substring(className.lastIndexOf(".") + 1, className.length());
        String methodName = element.getMethodName();
        Log.i(TAG + className, methodName + " <<< Exit");
    }

    public static void log(String message) {
        StackTraceElement element = Thread.currentThread().getStackTrace()[3];
        String className = element.getClassName();
        className = className.substring(className.lastIndexOf(".") + 1, className.length());
        String methodName = element.getMethodName();
        Log.i(TAG + className, methodName + " : " + message);
    }

    public static void log(int logPriority, String message) {
        StackTraceElement element = Thread.currentThread().getStackTrace()[3];
        String className = element.getClassName();
        className = className.substring(className.lastIndexOf(".") + 1, className.length());
        String methodName = element.getMethodName();
        Log.println(logPriority, TAG + className + methodName, message);
    }

    public static void log(int logPriority, String tag, String message) {
        StackTraceElement element = Thread.currentThread().getStackTrace()[3];
        String className = element.getClassName();
        className = className.substring(className.lastIndexOf(".") + 1, className.length());
        String methodName = element.getMethodName();
        Log.println(logPriority, TAG + className + methodName + " : " + tag, message);
    }

    public static void exceptionLog(Exception e) {
        StackTraceElement element = Thread.currentThread().getStackTrace()[3];
        String className = element.getClassName();
        className = className.substring(className.lastIndexOf(".") + 1, className.length());
        String methodName = element.getMethodName();
        Log.println(Log.ASSERT, TAG + className, methodName + " Exception : " + e.getMessage());
    }


}
