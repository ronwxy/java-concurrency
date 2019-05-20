package com.jboost.study.concurrency;

import java.util.concurrent.Semaphore;

/***
 * @Desc  资源的多副本的并发访问控制，信号量Semaphore即是其中的一种
 * @Author wuxy
 * @Date 2019/5/16 19:09   
 */
public class SemaphoreTest {

    //初始化3个柜台
    private final static Semaphore SEMAPHORE = new Semaphore(3);

    public static void main(String[] args) {
        // 8个人争夺资源
        for (int i = 0; i < 8; i++) {
            final String name = "人员" + i;
            new Thread(() -> {
                    try {
                        SEMAPHORE.acquire();
                        System.out.println(name + "占用一个柜台处理...");
                        Thread.sleep(2000);
                        System.out.println(name + "释放一个柜台");
                        SEMAPHORE.release();
                    } catch (InterruptedException e) {
                        System.out.println(name + "争夺柜台失败");
                    }
            }).start();
        }
    }
}
