package com.company;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * Created by root on 4/13/17.
 * 
 *
 */
public class MyServer1{
    ServerSocket ss;
    ArrayList<ServerConnection> connections = new ArrayList<ServerConnection>();
    boolean shouldRun=true;

    /**
     * tells that the server has started and creates a new Server socket and
     * opens up port 10.
     * while the server is running it accepts the client then a ServerConnection
     * instance is created and starts the Server conncection and adds the
     * sc to the ArrayList of
     */
    public MyServer1()
    {
        try
        {
            System.out.println("Server Started");
            ss = new ServerSocket(10);
            while (shouldRun) {


                Socket s = ss.accept();
                ServerConnection sc = new ServerConnection(s,this);
                sc.start();
                connections.add(sc);
            }

        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    public static void main (String as[])throws IOException
    {
        new MyServer1();

    }
}
