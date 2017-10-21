package com.zhi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xukezhi on 17/9/15.
 */
@RunWith(JUnit4.class)
public class ThreadTest2 {
    private static volatile long  testCount=200;
    private static AtomicInteger atomicInteger = new AtomicInteger(200);

    private static List<String> test1;



    public static class lockThread implements Runnable{
        @Override
        public void run() {
            // TODO Auto-generated method stub
            try{
//                while (test1.size()==5){
                    test1.set(3,"haha");
                    System.out.println("hahahahahahah");
//                }

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
    test1 = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
        test1.add(String.valueOf(i));
    }
    new Thread(new lockThread(),"haha").start();
    Iterator a = test1.iterator();
    while (a.hasNext()){
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(a.next().toString());
    }

}

}
