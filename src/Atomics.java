import java.util.concurrent.atomic.AtomicInteger;

public class Atomics {
    /**
     Java Crash Course Part 7
     Atomic Variables
     */
    public static void main(String[] args) {
        new Atomics().atomics();
    }

    /**
     Previously (in Threads2) we saw that we need to be very careful when multiple threads
     are reading and setting the same variable. In that lesson we used synchronized to control
     access to the variable. Now, we'll instead use atomics.

     An atomic variable has special operations that are guaranteed to complete "in a single moment".
     That is, their operations cannot be interleaved with other atomic operations. Let's go ahead
     and create an atomic integer with a value of 0:
     */
    private final AtomicInteger c = new AtomicInteger(0);
    private void atomics() {
        /**
         We'll now do something very similar to Threads2, where we create two threads that will
         repeatedly increment and decrement c. Note the usage of a lambda (this works because Runnable
         is a functional interface).
         */
        Runnable counter = () -> {
            for (int i = 0; i < 100000; i++) {
                c.incrementAndGet();
                c.decrementAndGet();
            }
        };
        /**
         Start and wait for two threads to finish:
         */
        Thread c1, c2;
        c1 = new Thread(counter);
        c2 = new Thread(counter);
        c1.start();
        c2.start();
        try {
            c1.join();
            c2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /**
         And the final value is...
         */
        System.out.println(c);
    }
}
