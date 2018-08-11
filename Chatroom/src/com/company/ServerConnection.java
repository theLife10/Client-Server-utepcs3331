package com.company;

import java.io.*;
import java.net.*;

/**
 * This class runs a thread and sends the message to the
 * clients
 * Created by root on 4/14/17.
 */
public class ServerConnection extends Thread {
    Socket socket;
    DataInputStream din;
    DataOutputStream dout;
    boolean shouldRun = true;
    MyServer1 server;
    public ServerConnection(Socket socket, MyServer1 server){
        super("Server Connection Thread");
        this.socket=socket;
        this.server=server;
    }

    /**
     * send message to user
     * @param text is the message
     */
    public void sendStringToClient(String text){
        try {
            dout.writeUTF(text);
            dout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * sends message to all users
     * @param text is the message that everyone is going to get
     */
    public void sendStringToAllClients(String text){
        for(int index =0; index < server.connections.size(); index++){
            ServerConnection sc = server.connections.get(index);
            sc.sendStringToClient(text);
        }
    }

    /**
     * this runs and waits to recieve and message and prints it
     * in the interations pane
     */
    public void run(){

        try {
            din = new DataInputStream(socket.getInputStream());
            dout= new DataOutputStream(socket.getOutputStream());
            while(shouldRun){
                while(din.available() == 0){
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                String textIn =din.readUTF();
                sendStringToAllClients(textIn);
            }
            din.close();
            dout.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
