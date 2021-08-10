/**
 Java Crash Course Part 5
 Threads 2
 */
public class Threads2 {
    public static void main(String[] args) {
        /**
         Now we'll see what happens when multiple threads try to access the same region of memory.
         */
        new Threads2().showInterference();
    }

    void showInterference() {
        /**
         Let's start two threads that both execute the Counter class below. We'll start them
         around the same time, then join() them to wait for them to finish. Counter will increment
         and decrement c many times.
         Just to complicate things though, instead of using a class that extends Thread we're using
         a class that implements Runnable.
         */
        // We only need one counter object
        Counter counter = new Counter(this);
        Thread c1, c2;
        // Now create two threads that both execute that object
        c1 = new Thread(counter);
        c2 = new Thread(counter);
        c1.start();
        c2.start();
        try {
            c1.join();
            c2.join();
        } catch (InterruptedException e) {
            return;
        }
        /**
         Now try guess what the output value is!
         */
        System.out.format("Final value is %d\n", this.c);
    }

    private volatile int c = 0;

    /**
     See how the output changes when the 'synchronized' is uncommented. This modifier causes the JVM
     to only allow access to a single synchronized method on this object concurrently.
     So if two threads try to call increment() and decrement(), one of them will be forced to wait.
     */
    public /*synchronized*/ void increment() {
        c++;
    }

    public /*synchronized*/ void decrement() {
        c--;
    }

    /**
     All this class does is repeatedly call the increment() and decrement() methods
     of the parent class. Intuitively we would expect the value of the variable will always
     end on 0.
     */
    class Counter implements Runnable {
        private final Threads2 parent;

        public Counter(Threads2 parent) {
            this.parent = parent;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100000; i++) {
                this.parent.increment();
                this.parent.decrement();
            }
        }
    }
}