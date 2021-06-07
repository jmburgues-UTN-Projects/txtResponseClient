package com.company;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Socket s = new Socket();

        DataInputStream dataIn = null;
        DataOutputStream dataOut = null;

        Scanner keyboard = new Scanner(System.in);

        boolean connected = false;

        while (!connected) {
            System.out.println("Enter host IP address: ");
            String host = keyboard.next();
            host = (host == null) ? "127.0.0.1" : host;
            System.out.println("Enter host port number: ");
            Integer port = keyboard.nextInt();
            port = (port == null) ? 3000 : port;

            try {
                s.connect(new InetSocketAddress(host, port));
                System.out.println("Connected");
                connected = true;
            }
            //Host not found
            catch (UnknownHostException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        dataOut = new DataOutputStream(s.getOutputStream());
        dataIn = new DataInputStream(s.getInputStream());

        //Get response from server
        Boolean continueSending = true;
        String response;
        String message = "";
        try {
            do {
                response = dataIn.readUTF();
                System.out.println(response);

                if(response.equals("Bye."))
                    continueSending = false;

                if(continueSending){
                    while(message.equals("")) {
                        message = keyboard.nextLine();
                    }
                    dataOut.writeUTF(message);
                    message = "";
                }
            }while (continueSending && !s.isClosed());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}