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

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Sockets {
    /**
     Java Crash Course Part 3
     Sockets
     */
    public static void main(String[] args) {
        /**
         This file is both a Java program and a tutorial. Each function explains and usually demos
         a feature of Java. Later functions build on material in earlier ones, so you can read
         this file from top to bottom.
         */
        theoryTime();
        client();
        server();
    }

    static void theoryTime() {
        /**
         Okay, we're going to need a bit of theory first. Remember that a TCP connection is always between
         a client and a server, with each end having an address and port:

         +------+                                                    +------+
         |Client|  198.51.100.106:39482 -----------> 233.252.0.70:80 |Server|
         +------+                                                    +------+

         Each of the client and server will use a Socket to encapsulate the details of the connection.
         */
    }

    static void client() {
        /**
         Let's go ahead and make a Socket. Here we're acting as a client. To construct the Socket we need to
         specify the remote address and port of the server we'd like to connect to:
         */
        String remoteHostName = "example.com";
        int remotePort = 80;
        /**
         We don't need to specify the local address or port. Why not?

         Now we can make a connection:
         */
        try {
            Socket socket = new Socket(remoteHostName, remotePort);
            /**
             We saw in the IO tutorial that we could use PrintWriter and BufferedReader to write and read
             streams. Luckily, a socket can behave like a stream so everything we learnt still applies:
             */
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            /**
             From here reading and writing data in just like writing to the console. Let's send a very simple
             HTTP request and read the first like of the server response:
             */
            writer.print("GET / HTTP/1.1\nHost: example.com\n\n");
            writer.flush(); //What happens if you remove this line?
            String response = reader.readLine();
            System.out.format("Response from server: %s\n", response);
            socket.close();
            /**
             Exercise: if there's an error in the code above, the socket.close() might not get executed.
             Look up the "finally" clause of try..catch and think about how it might be used to prevent
             that occurring.
             */
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    static void server() {
        /**
         Servers also use Sockets to read and write data to clients, but they setup things a little differently.
         Instead of creating a Socket directly, we want to listen for any incoming connections from a client.

         To listen for connections we use a ServerSocket. Don't be confused by the name! This is quite a different
         object to a Socket, and has a different purpose.

         To make a ServerSocket, we need to tell it what port it should listen on. This is the port clients will
         connect to (e.g. 80 for a web server).
         */
        int listeningPort = 2612; // Exercise for Linux/Mac users: what happens if you make this number < 1024?
        try {
            ServerSocket serverSocket = new ServerSocket(listeningPort);
            System.out.format("Now listening on port %d\n", listeningPort);
            /**
             So far we haven't connected to anything, we've just told the operating system that we're interested in
             TCP connections arriving on port 2612.

             Next we ask the operating system to accept an incoming connection if there is one.
             */
            Socket socket = serverSocket.accept(); // What if there's no client trying to connect? Check the docs
            // for the accept() method.
            /**
             The socket we have now is exactly like the socket we had in the client example. Let's get ready to read
             and write:
             */
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            /**
             All we'll do is read a line from the client and send it back:
             */
            String dataIn = reader.readLine();
            writer.println(dataIn);
            writer.flush();
            socket.close();
            /**
             Exercise: add a while(true) loop to the above code so that it can continue to process clients
             after it's finished with the first one.
             */
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
