import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 Java Crash Course Part 6
 Threads 3
 */
public class Threads3 {
    public static void main(String[] args) {
        /**
         It turns out that creating a new thread every time we want to do some work isn't very efficient.
         A thread pool gives us a group of threads that are waiting for us to assign them work. It also lets
         us limit the number of threads we will use (imagine if we were making a new thread for every incoming
         network connection and 1000000 connections arrives at once).
         */
        new Threads3().threadPool();
    }

    private void threadPool() {
        /**
         Let's adapt the example from Threads2, now using a thread pool to run calculations. First we create
         our pool with 4 threads:
         */
        ExecutorService pool = Executors.newFixedThreadPool(4);
        /**
         Now create some Adder objects that we can use repeatedly. Depending on your application, you might
         instead want to create a new Runnable for each job (for example, if you're processing different
         data in each job).
         */
        Runnable add3, add5;
        add3 = new Adder(this, 3);
        add5 = new Adder(this, 5);
        /**
         Request that the pool run our Runnable task. The fixed thread pool will try to run the Adders with one
         of its threads; if it can't, it will put it into a queue until a thread is free.

         Timing calculations: add3 and add5 take about 0.03s and 0.05s respectively to do their job.
         We invoke each 100 times, so with no concurrency the work would take (0.03+0.05)*100 = 8s to complete.
         If we have n threads though, it should take (8/n) seconds to complete.
         */
        for (int i = 0; i < 100; i++) {
            pool.execute(add3);
            pool.execute(add5);
        }

        /**
         Ask the pool to stop accepting new jobs and finish up current jobs (and its queue).
         */
        pool.shutdown();
        try {
            /**
             awaitTermination() will return true only once all the jobs have finished. Note
             the timeout value, and consider what an appropriate value is.
             */
            while (!pool.awaitTermination(1, TimeUnit.SECONDS)) {
                System.out.println("Waiting for jobs to finish...");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.format("Final value is %d\n", this.c);
    }

    private volatile int c = 0;
    public synchronized void increment() {
        c++;
    }

    /**
     This class adds the number supplied to the constructor by calling the parent's
     increment() method many times. It also does it slowly.
     */
    static class Adder implements Runnable {
        private final Threads3 parent;
        private final int constant;

        public Adder(Threads3 parent, int constant) {
            this.parent = parent;
            this.constant = constant;
        }

        @Override
        public void run() {
            for (int i = 0; i < constant; i++) {
                parent.increment();
                try {
                    Thread.sleep(10); //Artificially slow down this thread
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }
}
