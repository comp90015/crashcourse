import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MoreConcurrency {
    /**
     Java Crash Course Part 8
     More Concurrency

     This is a small program that generates two streams of numbers, and combines the two streams
     to produce output. Each of the stream generations and the combination happens concurrently
     with separate threads.
     */
    public static void main(String[] args) {
        new MoreConcurrency().process();
    }

    private final int QUEUE_CAPACITY = 20;

    private void process() {
        /**
         First we'll create two queues. These will hold the data produced by the generators.
         We also choose to specify a maximum capacity, this is the most number of elements
         the queue can hold.
         */
        BlockingQueue<Integer> leftQueue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);
        BlockingQueue<Integer> rightQueue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);
        /**
         Now we'll create some threads to generate our data.
         */
        Thread leftThread = new Thread(new Generator(leftQueue));
        leftThread.setDaemon(true);
        // Typing out type names is tiring. Meet 'var'.
        var rightThread = new Thread(new Generator(rightQueue));
        rightThread.setDaemon(true);

        /**
         Finally we'll create out Processor, which will consume data from both queues.
         */
        var processor = new Thread(new Processor(leftQueue, rightQueue));
        processor.setDaemon(true);

        /**
         Start it all up!
         */
        leftThread.start();
        rightThread.start();
        processor.start();

        /**
         Let's wait some time, then print out the contents of the queues.
         If we tried to do this with regular queues, we'd likely get ConcurrentModificationException.
         */
        cheatingSleep(3);
        System.err.print("Left queue:");
        for (Integer i : leftQueue) {
            System.err.format(" %d", i);
        }
        System.err.println();
        System.err.print("Right queue:");
        for (Integer i : rightQueue) {
            System.err.format(" %d", i);
        }
        System.err.println();
        /**
         Let things run for another few seconds
         */
        cheatingSleep(3);
        /**
         Notice the program exits here because all the other threads are daemon threads.
         */
    }

    private class Generator implements Runnable {
        Random rng;
        BlockingQueue<Integer> outputQueue;

        public Generator(BlockingQueue<Integer> outputQueue) {
            this.rng = new Random();
            this.outputQueue = outputQueue;
        }

        @Override
        public void run() {
            while (!Thread.interrupted()) {
                // Forever add random numbers to the queue, until we are interrupted
                try {
                    // add(), offer() and put() all insert items at the back of the queue.
                    // What's the difference?
                    this.outputQueue.put(this.rng.nextInt() % 10);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }

    private class Processor implements Runnable {
        private final BlockingQueue<Integer> leftQueue;
        private final BlockingQueue<Integer> rightQueue;

        public Processor(BlockingQueue<Integer> leftQueue, BlockingQueue<Integer> rightQueue) {
            this.leftQueue = leftQueue;
            this.rightQueue = rightQueue;
        }

        @Override
        public void run() {
            int left, right;
            while (!Thread.interrupted()) {
                try {
                    left = leftQueue.take();
                    right = rightQueue.take();
                    System.out.println(left - right);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }

    private void cheatingSleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        }
        catch (InterruptedException e) {
            return;
        }
    }
}