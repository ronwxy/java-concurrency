package com.jboost.study.concurrency;

/***
 * @Desc synchronized: 控制对象锁获取与释放
 * @Author wuxy
 * @Date 2019/5/20 14:58   
 */
public class SynchronizedTest {

    public static void main(String[] args) {
        SynchronizedDemo myThread = new SynchronizedDemo(new Integer(10));
        for (int i = 0; i < 3; i++) {
            new Thread(myThread, "T" + i).start();
        }
    }

    static class SynchronizedDemo extends Thread {
        private Integer count = 10;

        public SynchronizedDemo(Integer count) {
            this.count = count;
        }

        //synchronized加锁
        public synchronized void run() {
            work();
        }

//        public void run(){
//            synchronized (count){
//                work();
//            }
//        }

        public void work() {
            while (count > 0) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(this.currentThread().getName() + "窗口卖出票号：" + count--);
            }
        }

        public static synchronized void countDown() {

        }
    }

}
