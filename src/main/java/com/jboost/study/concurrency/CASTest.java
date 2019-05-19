package com.jboost.study.concurrency;

public class CASTest {

    public static void main(String[] args){
        final CompareAndSwap cas = new CompareAndSwap();

        for(int i=0; i<10; i++){
            // 创建10个线程,模拟多线程环境
            new Thread(new Runnable(){
                @Override
                public void run(){
                    int expectedValue = cas.get();

                    boolean b = cas.compareAndSet(expectedValue, (int)(Math.random()*100));
                    System.out.println(b);
                }
            }).start();
        }
    }

     // 模拟CAS 算法
     static class CompareAndSwap{
        private int value;

        // 获取内存值
        public synchronized int get(){
            return value;
        }

        // 无论更新成功与否,都返回修改之前的内存值
        public synchronized int compareAndSwap(int expectedValue, int newValue){
            // 获取旧值
            int oldValue = value;

            if(oldValue == expectedValue){
                this.value = newValue;
            }

            // 返回修改之前的值
            return oldValue;
        }

        // 判断是否设置成功
        public synchronized boolean compareAndSet(int expectedValue, int newValue){
            return expectedValue == compareAndSwap(expectedValue, newValue);
        }
    }

}
