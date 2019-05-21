package com.jboost.study.concurrency;

import java.util.concurrent.CountDownLatch;

/***
 * volatile保证可见性、有序性，但不保证原子性，对自增场景及不等式判断场景不适用
 */
public class VolatileTest2 {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        NumberRange numberRange = new NumberRange();
        ThreadDemo td1 = new ThreadDemo(numberRange, countDownLatch);
        ThreadDemo2 td2 = new ThreadDemo2(numberRange, countDownLatch);
        numberRange.print();
        new Thread(td1).start();
        new Thread(td2).start();
        countDownLatch.await();
        numberRange.print();


    }

    static class ThreadDemo implements Runnable{
        private NumberRange numberRange;
        private CountDownLatch countDownLatch;

        public ThreadDemo(NumberRange numberRange, CountDownLatch countDownLatch){
            this.numberRange = numberRange;
            this.countDownLatch = countDownLatch;
        }
        public void run(){
            numberRange.setLower(4);
            countDownLatch.countDown();
        }


    }

    static class ThreadDemo2 implements Runnable{
        private NumberRange numberRange;
        private CountDownLatch countDownLatch;

        public ThreadDemo2(NumberRange numberRange, CountDownLatch countDownLatch){
            this.numberRange = numberRange;
            this.countDownLatch = countDownLatch;
        }
        public void run(){
            numberRange.setUpper(3);
            countDownLatch.countDown();
        }


    }

    static class NumberRange {
        private volatile int lower = 0;
        private volatile int upper = 5;

        public int getLower() { return lower; }
        public int getUpper() { return upper; }

        public void setLower(int value) {
            if (value > upper)
                throw new IllegalArgumentException("value cannot bigger than upper");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lower = value;
        }

        public void setUpper(int value) {
            if (value < lower)
                throw new IllegalArgumentException("value cannot smaller than lower");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            upper = value;
        }

        public void print(){
            System.out.println("lower: " + lower + ", upper: "+ upper);
        }
    }
}
