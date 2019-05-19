package com.jboost.study.concurrency;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicTest {
    public static void main(String[] args){

        AtomicDemo ad = new AtomicDemo();
        //解决方法：使用AtomicInteger
//        AtomicDemo2 ad = new AtomicDemo2();

        for(int i=0; i < 10; i++){
            new Thread(ad).start();
        }
    }

    static class AtomicDemo implements Runnable{
        private int serialNumber = 0;

        public void run(){

            try{
                Thread.sleep(200);
            }catch(InterruptedException e){

            }

            System.out.println(Thread.currentThread().getName() + ":" + getSerialNumber());
        }

        public int getSerialNumber(){
            return serialNumber++;
        }
    }

    static class AtomicDemo2 implements Runnable{
        private AtomicInteger serialNumber = new AtomicInteger();

        public void run(){

            try{
                Thread.sleep(200);
            }catch(InterruptedException e){

            }

            System.out.println(Thread.currentThread().getName() + ":" + getSerialNumber());
        }

        public int getSerialNumber(){
            return serialNumber.getAndIncrement();
        }
    }
}
