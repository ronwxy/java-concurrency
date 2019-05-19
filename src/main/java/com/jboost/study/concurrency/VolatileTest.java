package com.jboost.study.concurrency;

/***
 * volatile保证可见性、有序性，但不保证原子性，对自增场景不适用
 */
public class VolatileTest {

    public static void main(String[] args){
        ThreadDemo td = new ThreadDemo();
        new Thread(td).start();

        while(true){
            synchronized (td) { //方法一：使用同步锁，保证可见性
                if (td.isFlag()) {
                    System.out.println("########");
                    break;
                }
            }
        }
    }

    static class ThreadDemo implements Runnable{
        private boolean flag = false;
        //方法二：使用volatile
//        private volatile boolean flag = false;

        public void run(){
            try{
                // 该线程 sleep(200), 导致了程序无法执行成功
                Thread.sleep(200);
            }catch(InterruptedException e){
                e.printStackTrace();
            }

            flag = true;

            System.out.println("flag="+isFlag());
        }

        public boolean isFlag(){
            return flag;
        }

        public void setFlag(boolean flag){
            this.flag = flag;
        }
    }
}
