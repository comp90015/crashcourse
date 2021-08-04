/* Copyright (c)  2021, Luke Ceddia
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

import java.io.IOException;

public class Exceptions {
    /**
     Java Crash Course Part 2
     Exceptions
     */
    public static void main(String[] args) {
        /**
         This file is both a Java program and a tutorial. Each function explains and usually demos
         a feature of Java. Later functions build on material in earlier ones, so you can read
         this file from top to bottom.
         */
        exceptionsIntro();
        uncheckedVsChecked();
        dealingWithExceptions();
    }

    static void exceptionsIntro() {
        /**
         Many methods in Java use exceptions to signal an error condition. You many have seen
         exceptions before in Python, where they work similarly to Java.

         If a method throws an exception, we might want to handle it to somehow resolve the error.
         The basic structure is the try...catch block. Here's an example:
         */
        try {
            // This function always throws an exception
            doSomeIO();
        }
        catch (IOException e) {
            System.out.format("We caught an IO exception! Error message: %s\n", e.getMessage());
        }
        /**
         IOException is a common exception that many standard Java methods will throw if there's
         issues writing to files or network sockets.
         By specifying a catch block for the IOException, Java will jump to it immediately if any
         of the statements in the try block throw that exception.

         We can get more complex with our catches, but in general you should avoid making things too complicated.
         */
        try {
            doSomeIO();
        }
        catch (IOException|ArithmeticException e) { // The | should be read as "OR"
            System.out.println("Caught an IOException or an ArithmeticException");
        }
        // We can have multiple catch blocks to handle different exceptions
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Caught an ArrayIndexOutOfBoundsException");
        }
    }

    static void uncheckedVsChecked() {
        /** There are two kinds of exceptions: Checked and Unchecked

         Unchecked exceptions are like what Python supports. If you don't explicitly catch them,
         they'll be passed to the calling method, and all the way up until it reaches main().
         This category includes many "should never happen" kind of errors like NullPointerException,
         ArrayIndexOutOfBoundsException and ArithmeticException [divide by 0]. Technically, any subclass
         of RuntimeException is unchecked.

         Checked exceptions have some extra rules:
            1) If it's possible for a method to throw a checked exception, it must declare that with a
                "throws FooException" in the method signature. See doSomeIO() below for an example.
            2) If you call a method that throws a checked exception, you must either catch it or also
                be declared as throwing it.
         IOException is a checked exception. Let's look at what that means:
         */
        // Remove the comment on the next line to see what error you get, and what IntelliJ suggests
        // to fix it.
        //doSomeIO();
    }

    static void dealingWithExceptions() {
        /**
         Now that we know that Java forces us to do something with many exceptions, how do we handle them?
         Some of the code in this subject will do a variation on this:
         */
        try {
            doSomeIO();
        } catch (IOException e) {
            System.out.format("Error: %s\n", e.getMessage());
            e.printStackTrace();
        }
        /**
         If you didn't notice that "e" before, it's an object of type IOException that we can access to get
         info about what went wrong. Some exceptions might have a useful error message accessed via getMessage().
         printStackTrace() will hopefully be useful to the programmer.

         Question: how suitable is this for a more complex, production-grade program? What other approaches could
         we take to handling exceptions?
         */
    }

    static void doSomeIO() throws IOException {
        throw new IOException("Something went horribly wrong");
    }
}
