package java0.conc0303;

import java.util.concurrent.*;
import java.lang.reflect.*;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 *
 * 一个简单的代码参考：
 */
public class Homework03 {
//    static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) {

        long start = System.currentTimeMillis();

        // 在这里创建一个线程或线程池
//        ExecutorService service = Executors.newSingleThreadExecutor();
        ExecutorService service = Executors.newCachedThreadPool();
        // 异步执行 下面方法
        // 1. FutureTask
        FutureTask<Integer> futureTask = new FutureTask(() -> sum());
        service.submit(futureTask);
        try {
            int result = futureTask.get().intValue();

//        int result = sum(); //这是得到的返回值

            // 确保  拿到result 并输出
            System.out.println("1 : FutureTask异步计算结果为：" + result);
            System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // 2. Callable + Future
        Future future = service.submit(() -> sum());
        try {
            int result = (Integer) future.get();

            // 确保  拿到result 并输出
            System.out.println("2 : Future + Callable异步计算结果为：" + result);
            System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            // 关闭cached线程池, 以便结束JVM进程, 否则需要60秒空闲才能没有线程
            service.shutdown();
        }

        // 3. Thread + Future + Callable
        ComputeThread task3 = new ComputeThread();
        FutureTask<Integer> futureTask3 = new FutureTask<>(task3);
        new Thread(futureTask3).start();
        try {
            int result = (Integer)futureTask3.get();

            // 确保  拿到result 并输出
            System.out.println("3 : Thread + FutureTask + Callable异步计算结果为："+result);
            System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // 4. Thread + FutureTask + Callable + join()
        ComputeThread task4 = new ComputeThread();
        FutureTask<Integer> futureTask4 = new FutureTask<>(task4);
        Thread thread = new Thread(futureTask4);
        try {
            thread.start();
            thread.join();
            int result = (Integer)futureTask4.get();

            // 确保  拿到result 并输出
            System.out.println("4 : Thread + FutureTask + Callable + join()异步计算结果为："+result);
            System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // 5. CompletableFuture.get() + Callable
//        CountDownLatch countDownLatch = new CountDownLatch(1);
        CompletableFuture<Integer> cfSum = CompletableFuture.supplyAsync(()-> {
            int result =  sum();
//            countDownLatch.countDown();
            return result;
        });
        try {
            int result = cfSum.get();
            cfSum.complete(100);
            System.out.println("5 : CompletableFuture.get() + Callable异步计算结果为："+result);
            System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
//            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // 6. CompletableFuture.join() + Callable
        CompletableFuture<Integer> cfSum6 = CompletableFuture.supplyAsync(()-> {
            int result =  sum();
            return result;
        });
        int result = cfSum6.join();
        System.out.println("6 : CompletableFuture.join() + Callable异步计算结果为："+result);
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
        // 然后退出main线程
    }
    
    private static int sum() {
        return fibo(36);
    }
    
    private static int fibo(int a) {
        if ( a < 2) 
            return 1;
        return fibo(a-1) + fibo(a-2);
    }

}

class ComputeThread implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        Method method1 = Homework03.class.getDeclaredMethod("sum");
        method1.setAccessible(true);
        Integer res = (Integer)method1.invoke(null);
        return  res.intValue();
    }
}

class ComputeTask implements Callable<Integer> {
    private CountDownLatch countDownLatch;
    public ComputeTask(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }
    @Override
    public Integer call() throws Exception {
        Method method1 = Homework03.class.getDeclaredMethod("sum");
        method1.setAccessible(true);
        Integer res = (Integer)method1.invoke(null);
        countDownLatch.countDown();
        return  res.intValue();
    }
}