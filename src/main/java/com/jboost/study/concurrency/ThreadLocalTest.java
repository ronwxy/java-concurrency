package com.jboost.study.concurrency;

/***
 * @Desc ThreadLocal： 提供线程内的局部变量，在线程的生命周期内起作用，减少同一个线程内多个函数或者组件之间一些公共变量的传递的复杂度
 * @Author wuxy
 * @Date 2019/5/16 16:15   
 */
public class ThreadLocalTest {

    public static void main(String[] args) throws InterruptedException {
        int i = 100;
        System.out.println("Set main localValue：" + i);
        ThreadLocalUtil.setUserId(i);
        InheritableThreadLocalUtil.setUserId(i);

        MyThread myThread = new MyThread();
        myThread.start();

        System.out.println("Get main localValue：" + ThreadLocalUtil.getUserId());
        ThreadLocalUtil.clear();
//        myThread.join();
    }

    public static class MyThread extends Thread {
        public void run(){
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            init(10);
            read();
            readForInherit();
        }

        private void init(int i){
            System.out.println("Set sub localValue：" + i);
            ThreadLocalUtil.setUserId(i);
        }

        private void read(){
            Integer localValue = ThreadLocalUtil.getUserId();
            System.out.println("Get sub localValue: " + localValue);
            ThreadLocalUtil.clear(); //如果不做清理，可能会导致内存泄漏或者业务出错（在线程池场景下）
        }

        private void readForInherit(){
            Integer localValue = InheritableThreadLocalUtil.getUserId();
            System.out.println("Get sub localValue from main: " + localValue);
            InheritableThreadLocalUtil.clear(); //如果不做清理，可能会导致内存泄漏或者业务出错（在线程池场景下）
        }
    }

    public static class ThreadLocalUtil {
        private static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();

        public static void setUserId(Integer userId){
            threadLocal.set(userId);
        }

        public static Integer getUserId(){
            return threadLocal.get();
        }

        public static void clear(){
            threadLocal.remove();
        }
    }

    public static class InheritableThreadLocalUtil {
        private static InheritableThreadLocal<Integer> threadLocal = new InheritableThreadLocal<>();

        public static void setUserId(Integer userId){
            threadLocal.set(userId);
        }

        public static Integer getUserId(){
            return threadLocal.get();
        }

        public static void clear(){
            threadLocal.remove();
        }
    }



}
