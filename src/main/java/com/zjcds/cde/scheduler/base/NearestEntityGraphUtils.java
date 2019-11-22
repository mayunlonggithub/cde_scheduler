package com.zjcds.cde.scheduler.base;

import java.lang.reflect.Method;

/**
 * created date：2017-09-07
 * @author niezhegang
 */
public abstract class NearestEntityGraphUtils {

    public static ThreadLocal<Method> nearestEntityGraph = new ThreadLocal();

    public static void setCurrentInvocationMethod(Method method) {
        nearestEntityGraph.set(method);
    }

    public static Method getCurrentInvocationMethod(){
        return nearestEntityGraph.get();
    }



}