package com.company;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
/**
 * this class is to have a a client connection and send out messages to server
 * Created by root on 4/14/17.
 */
public class ClientConnection extends Thread {
    Socket s;
    DataInputStream din;
    DataOutputStream dout;
    boolean shouldRun=true;
    public ClientConnection(Socket socket, MyClient1 client){
        s=socket;
    }

    /**
     * sends the text to the server.
     * @param text the message of the text.
     */
    public void sendStringToServer(String text){
        try {
            dout.writeUTF(text);
            dout.flush();
        } catch (IOException e) {
            e.printStackTrace();
            close();
        }

    }

    /**
     * this method runs the thread
     * gets the input stream and the output stream
     * prints out the input
     */
    public void run() {
        try {
            din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
            while (shouldRun) {
                try {
                    while (din.available() == 0) {
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    String reply = din.readUTF();
                    System.out.println(reply);
                } catch (IOException e) {
                    e.printStackTrace();
                    close();
                }

            }
        }
        catch (IOException e){
            e.getStackTrace();
            close();
        }
    }

    /**
     * closes down the data input, output, and socket
     */
    public void close(){
        try {
            din.close();
            dout.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
