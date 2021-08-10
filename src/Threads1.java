import java.util.ArrayList;
import java.util.List;

public class Threads1 {
    /**
     Java Crash Course Part 4
     Threads 1
     */
    public static void main(String[] args) {
        /**
         This file is both a Java program and a tutorial. Each function explains and usually demos
         a feature of Java. Later functions build on material in earlier ones, so you can read
         this file from top to bottom.
         */
        introduction();
        myFirstThreads();
    }

    static void introduction() {
        /**
         What is a thread?
         In lectures, we talked about a process having one or more threads of execution. Through some
         combination of multiple physical CPUs and multitasking, the Java VM will run each thread concurrently.

         For our purposes, the biggest difference between threads and processes is shared memory. This means
         threads can access the same Java objects, and if we don't coordinate access things can go very wrong.
         */
    }

    static void myFirstThreads() {
        /**
         Here's a very simple class that takes a value, multiplies it by 3 and lets us
         get the result. Note this class extends Thread, and the run() method is an Override.
         */
        class Multiplier extends Thread {
            private int value;

            public Multiplier(int value) {
                this.value = value;
            }

            @Override
            public void run() {
                /**
                 Lookup the documentation for the Thread class and see what it says about the
                 run() method. When we start execution of this thread, run() is called. When run()
                 returns, the thread is finished.
                 */
                this.value = this.value * 3;
            }

            public int getResult() {
                return value;
            }
        }

        /**
         Let's go ahead and spawn some threads! We'll make a list to hold all the threads:
         */
        List<Multiplier> multipliers = new ArrayList<>();
        /**
         And now create a multiplier for each value we want to multiply.
         */
        for (int i = 0; i < 4; i++) {
            Multiplier multiplier = new Multiplier(i);
            multipliers.add(multiplier);
        }
        /**
         At this point our threads aren't actually running yet. We say they are in the "NEW" state.
         To actually make a thread begin, we need to call start().
         */
        for (Multiplier multiplier : multipliers) {
            multiplier.start();
        }
        /**
         Our threads are now in the "RUNNABLE" state which means they are executing, subject to
         JVM and OS scheduling.
         How do we know when a thread is finished? The join() method will wait until a thread
         has finished.
         */
        for (Multiplier multiplier : multipliers) {
            try {
                multiplier.join(); // Wait for the thread to reach the TERMINATED state
            } catch (InterruptedException e) {
                return;
            }
        }
        /**
         All our threads have finished now, so we can collect the result values.
         */
        System.out.println("Multiplier results:");
        for (Multiplier multiplier : multipliers) {
            System.out.println(multiplier.getResult());
        }
    }
}
