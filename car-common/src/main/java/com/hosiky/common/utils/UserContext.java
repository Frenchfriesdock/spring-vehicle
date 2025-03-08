package com.hosiky.common.utils;

/**
 * 这段代码定义了一个名为 UserContext 的类，主要用来在多线程环境中存储和获取当前用户的ID
 * setCurrentId(Long id) 方法用来将当前线程的用户ID设置为指定的ID。这个方法会将 id 存储到当前线程的 threadLocal 变量中。
 * getCurrentId() 方法用来获取当前线程的用户ID。它从当前线程的 threadLocal 变量中获取存储的ID，并返回。
 * removeCurrentId() 方法用来移除当前线程的用户ID。这个方法会清除当前线程的 threadLocal 变量中存储的ID。
 */
public class UserContext {

    /**
     * ThreadLocal 是 Java 中用来实现线程局部变量的机制。它提供了一种线程安全的方式来存储每个线程独有的数据，这些数据对于同一线程在多个方法调用之间是共享的。
     * 在 UserContext 类中，定义了一个静态的 ThreadLocal<Long> 对象 threadLocal，用来存储用户的ID。
     */
    public static final ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    public static Long getCurrentId() {
        return threadLocal.get();
    }

    public static void removeCurrentId() {
        threadLocal.remove();
    }

}
