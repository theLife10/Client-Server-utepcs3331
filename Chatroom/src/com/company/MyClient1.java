package com.company;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * This class is to connect to the server and listen for input
 */
public class MyClient1{

    ClientConnection cc;

    /**
     * main starts client
     * @param args
     */
    public static void main(String [] args){
        new MyClient1();
    }

    public MyClient1(){
        try{
            Socket s = new Socket("localhost",10);
            cc = new ClientConnection(s,this);
            cc.start();
            listenForInput();
        }
        catch (UnknownHostException e){
            e.getStackTrace();
            cc.close();
        }
        catch(IOException e){
            e.getStackTrace();
            cc.close();
        }
    }

    /**
     * waits for to listens for input and sends the
     * string to server
     */
    public void listenForInput(){
        Scanner console = new Scanner(System.in);

        while (true){
            while(!console.hasNextLine()){
                try{
                    Thread.sleep(1);
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                    cc.close();
                }
            }
            String input = console.nextLine();

            if(input.toLowerCase().equals("quit")){
                break;
            }
            cc.sendStringToServer(input);
        }
        cc.close();
    }
}