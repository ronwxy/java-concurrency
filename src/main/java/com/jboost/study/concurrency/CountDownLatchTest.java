package com.jboost.study.concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/***
 * @Desc CountDownLatch: 允许一个或多个线程等待其他线程完成操作后再执行
 * @Author wuxy
 * @Date 2019/5/16 19:41   
 */
public class CountDownLatchTest {
    private final static CountDownLatch countDownLatch = new CountDownLatch(5);
    private final static ExecutorService executorService = Executors.newFixedThreadPool(5);

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            executorService.execute(() -> {
                    try {
                        // 模拟执行任务
                        Thread.sleep(1000);
                        System.out.println(Thread.currentThread().getName() + "执行完任务");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        countDownLatch.countDown();
                    }
            });
        }
        countDownLatch.await();
        System.out.println("主线程等待子线程执行任务完毕，继续执行");
    }
}
