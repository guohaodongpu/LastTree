package com.thundersoft.codecasino;

import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    Socket socket;
    Thread WriterThread;

    public static int sOnOff = 0; //是否发送
    public static int sSendHeartOnOff = 0; //默认发送心跳值

    public Client(String host, int port) {
        try {
            socket = new Socket(host, port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        WriterThread = new Thread(new MessageSender(socket));
        WriterThread.start();
        new Thread(new HeartSender(socket)).start();
        new Thread(new MessageListener(socket)).start();
    }

    public static class HeartSender implements Runnable {
        Socket socket;

        public HeartSender(Socket socket) {
            this.socket = socket;
        }

        /**
         * @see Thread#run()
         */
        @Override
        public void run() {
            try {
                PrintWriter printWriter1 = new PrintWriter(socket.getOutputStream());
                while (true) {
                    synchronized ((Object) sSendHeartOnOff) {
                        if (sSendHeartOnOff == 1) {
                            System.out.println(sSendHeartOnOff);
                            printWriter1.write(MessageSender.HEART_STR);
                            printWriter1.flush();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        //Client client = new Client("10.0.28.197", 9527);//測試
        Client client = new Client("106.2.164.79", 9527);//正式
        client.start();

    }

}