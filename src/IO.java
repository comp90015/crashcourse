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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class IO {
/**
    Java Crash Course Part 1
    Input & Output: Streams
 */
    public static void main(String[] args) {
        /**
         This file is both a Java program and a tutorial. Each function explains and usually demos
         a feature of Java. Later functions build on material in earlier ones, so you can read
         this file from top to bottom.
         */
        streamInput();
        streamOutput();
    }

    static void streamInput() {
        /**
         Input can come from many sources: a console (from the user), a file or even the network.
         Java unifies these into Streams so we can treat them all the same.

         For reading text, the InputStreamReader class provides basic functionality to read characters
         from a sources. It's quite low level though. Here's how we might use InputStreamReader to access
         the console:
         */
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        // Read a single character from the console.
        // you might have to press Enter after typing a character (why?)
        System.out.println("Please enter a character");
        int inputChar = 0;
        try {
            // Use an int not a char because this might return -1 if we're at the end of the stream.
            inputChar = inputStreamReader.read();
            if (inputChar == -1) {
                return;
            }
            inputStreamReader.read(); // Get rid of that Enter character
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.format("Read character: %c\n", (char)inputChar);



        /**
         We don't want to read a Stream one character at a time though. That would be slow and difficult.
         Let's use a BufferedReader, which provides extra functionality on top of an InputStreamReader.
         Notice how we still use the InputStreamReader by passing it to the BufferedReader constructor.
         */
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        /**
         Now we can read an entire line of text at once!
         The readLine method will read characters until it finds a newline character.
         */
        System.out.println("Please enter some text");
        String inputString = "";
        try {
            inputString = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.format("Your line of text was: %s\n", inputString);
        /**
         Why is it called "Buffered" Reader?
         */
    }

    static void streamOutput() {
        /**
         Output with Streams is similar, but we can make our life a little easier. The PrintWriter class provides
         some useful functions for formatting text that we'll make use of.

         Conveniently, we can wrap a PrintWriter directly around an output Stream.
         */
        PrintWriter printWriter = new PrintWriter(System.out);
        /**
         Now we can write strings using functions you might be familiar with:
         */
        printWriter.println("Hello, world");
        printWriter.format("This is a number: %d\n", 10);

        /**
         Exercise: look up the documentation for BufferedWriter and use it instead of PrintWriter (your code
         will look very similar to the BufferedReader example). When and why might we use BufferedWriter?
         */

    }
}
