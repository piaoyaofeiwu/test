package com.zhi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xukezhi on 17/9/15.
 */
@RunWith(JUnit4.class)
public class ThreadTest {
    private static volatile long  testCount=200;
    private static AtomicInteger atomicInteger = new AtomicInteger(200);

    private static final long  test1;

    static  {
        test1 = 200L;
    }
    public static class WorkerThread implements Runnable{

        private static CyclicBarrier barrier;

        public WorkerThread(CyclicBarrier b){
            this.barrier = b;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            try{
//                barrier.await();
                int i = atomicInteger.get();
                atomicInteger.compareAndSet(i,++i);
                System.out.println(Thread.currentThread().getId()+"当前数据"+atomicInteger);
            }catch(Exception e){
                e.printStackTrace();
            }
        }

    }

    public static class lockThread implements Runnable{

        Object barrier;
        public lockThread(Object b){
            this.barrier = b;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            try{
                synchronized (barrier){
                    System.out.println(Thread.currentThread()+"hold loc notify@"+ testCount+"   "+ Instant.now());
                    testCount--;
                    Thread.sleep(400);
                    barrier.notifyAll();
                }

            }catch(Exception e){
                e.printStackTrace();
            }
        }

    }


    public static class waitThread implements Runnable{

        Object barrier;

        Long sleepTime;
        public waitThread(Object b,Long sleepTime){
            this.barrier = b;
            this.sleepTime= sleepTime;
        }
        @Override
        public void run() {
            // TODO Auto-generated method stub
            try{
                synchronized (barrier){
                    while (testCount==200){
                        System.out.println(Thread.currentThread()+"wai testCount is "+testCount+"slepptime   "+sleepTime);
                        barrier.wait(sleepTime);
                        testCount = 199;
                        System.out.println(Thread.currentThread()+"haha is new "+testCount+ Instant.now().toEpochMilli());
//                        Thread.sleep(100);
                    }

                }

            }catch(Exception e){
                e.printStackTrace();
            }
        }

    }
@Test
public void test(){
    Object info = new Object();
    CyclicBarrier barrier = new CyclicBarrier(50);
    WorkerThread thread = new WorkerThread(barrier);
//    lockThread thread1 = new lockThread(info);
//    waitThread thread2 = new waitThread(info,400L);
//    waitThread thread3 = new waitThread(info,600L);
//    waitThread thread4 = new waitThread(info,300L);
//    waitThread thread5 = new waitThread(info,200L);
    ThreadPoolExecutor executor = new ThreadPoolExecutor(10,150,30, TimeUnit.MINUTES,new ArrayBlockingQueue<Runnable>(10));
//    executor.execute(thread2);
//    executor.execute(thread3);
//    executor.execute(thread4);
//    executor.execute(thread5);
//    executor.execute(thread1);
    for(int i=0;i<99;i++){
        executor.execute(new Thread(thread,String.valueOf(i)));

    }
//    try {
//        cb.await();
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
    System.out.println("atomicInteger"+atomicInteger.get());
    executor.shutdown();
    try {
        Thread.sleep(1000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

}

}
