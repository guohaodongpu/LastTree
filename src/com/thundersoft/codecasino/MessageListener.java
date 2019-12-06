package com.thundersoft.codecasino;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MessageListener implements Runnable {
    Socket socket;
    static int sFlagForMessageReceived = -1;
    InputStreamReader inputStreamReader;
    static int sFlagTypeReceived = 0;
    static int sFlagTypeForOK = 0;

    final static int TYPE_OK = 1;
    final static int TYPE_ERROR = 2;
    final static int TYPE_START = 3;
    final static int TYPE_ROUNDOVER = 4;
    final static int TYPE_GAMEOVER = 5;
    final static int TYPE_MAP = 6;

    final static int TYPE_FOR_OK_KEY = 1;
    final static int TYPE_FOR_OK_CONTROL = 2;
    final static int TYPE_FOR_OK_HEART = 3;



    public MessageListener(Socket s) {
        this.socket = s;
        try {
            this.inputStreamReader = new InputStreamReader(socket.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("Listener.run");

        while (true) {
            if (isInputStreamReady()) {
                readFromStream();

                try {

                } catch (ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public boolean isInputStreamReady() {
        try {
            return this.inputStreamReader.ready();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void readFromStream() {
        System.out.println("readFromStream");
        try {
            int ch_int_1 = inputStreamReader.read();
            StringBuffer stringBuffer = new StringBuffer();
            char ch1 = (char) ch_int_1;
            stringBuffer.append(ch1);
            int ch_int_2 = inputStreamReader.read();
            char ch2 = (char) ch_int_2;
            stringBuffer.append(ch2);
            char temp;
            while (isInputStreamReady()) {
                stringBuffer.append((char) inputStreamReader.read());
            }
            System.out.println(": "+ stringBuffer);
            switch (ch2) {
                case (char) 79: {// O
                    sFlagTypeReceived = TYPE_OK;
                    if (sFlagTypeForOK == TYPE_FOR_OK_KEY){
                        Client.sSendHeartOnOff = 0;
                    }
                    break;
                }
                case (char) 69: {// E
                    sFlagTypeReceived = TYPE_ERROR;
                    break;
                }
                case (char) 83: {// S
                    sFlagTypeReceived = TYPE_START;
                    Client.sOnOff = 1;
                    sFlagTypeForOK = TYPE_FOR_OK_CONTROL;
                    MessageSender.sSendType = MessageSender.TYPE_FOR_SEND_READY;
                    MapUtil.parseMapSize(stringBuffer.toString());
                    break;
                }
                case (char) 82: {// R
                    sFlagTypeReceived = TYPE_ROUNDOVER;
                    break;
                }
                case (char) 71: {// G
                    sFlagTypeReceived = TYPE_GAMEOVER;
                    Client.sOnOff = 1;
                    MessageSender.sSendType = MessageSender.TYPE_FOR_SEND_QUIT;
                    break;
                }
                case (char) 77: {// M
                    sFlagTypeReceived = TYPE_MAP;
                    Client.sOnOff = 1;
                    MapUtil.parseMapInfo(stringBuffer.toString());
                    MessageSender.sSendType = MessageSender.TYPE_FOR_SEND_CONTROL;

                    break;
                }
                default: {
                    break;
                }
            }
//            System.out.println("FlagTypeReceived: " + sFlagTypeReceived + "\n" + stringBuffer);
//            switch (ch1) {
//                case (char) 79:
//                    ch2 = (char) inputStreamReader.read();
//                    stringBuffer.append(ch2);
//                    sFlagForMessageReceived = 1;
//                    break;
//                case (char) 91:
//                    for (int i = 0; i <= 225; i++) {
//                        ch2 = (char) inputStreamReader.read();
//                        stringBuffer.append(ch2);
//                    }
//                    sFlagForMessageReceived = 2;
//                    break;
//                default:
//                    while (isInputStreamReady()) {
//                        stringBuffer.append((char) inputStreamReader.read());
//                    }
//                    System.out.println(stringBuffer.toString());
//                    System.exit(0);
//                    sFlagForMessageReceived = 3;
//                    break;
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
