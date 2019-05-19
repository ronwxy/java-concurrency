package com.jboost.study.concurrency;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTest {

    public static void main(String[] args){
        final ReadWriteLockDemo rw = new ReadWriteLockDemo();

        // 一个线程进行写
        new Thread(new Runnable(){
            public void run(){
                rw.set((int)(Math.random()*100));
            }
        },"Write:").start();

        // 100个线程进行读操作
        for(int i=0; i<100; i++){
            new Thread(new Runnable(){
                public void run(){
                    rw.get();
                }
            },"Read:").start();
        }
    }

    static class ReadWriteLockDemo{
        private int number = 0;

        private ReadWriteLock lock = new ReentrantReadWriteLock();



        // 读
        public void get(){
            lock.readLock().lock(); // 上锁

            try{
                System.out.println(Thread.currentThread().getName()+":"+number);
            }finally{
                lock.readLock().unlock(); // 释放锁
            }
        }

        // 写
        public void set(int number){

            lock.writeLock().lock();

            try{
                System.out.println(Thread.currentThread().getName());

                this.number = number;
            }finally{
                lock.writeLock().unlock();
            }
        }
    }
}
