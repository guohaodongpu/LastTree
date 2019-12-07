package com.thundersoft.codecasino;

import java.io.PrintWriter;
import java.net.Socket;

/**
 * Author: Hadon
 * Date: 2019/12/6 14:29
 * Content:
 */
public class MessageSender implements Runnable {
    static boolean isKeySend = false;
    private Socket socket;

    static int sSendType = 0;
    String mCommand;
    final static int TYPE_FOR_SEND_READY = 1;
    final static int TYPE_FOR_SEND_HEART = 2;
    final static int TYPE_FOR_SEND_CONTROL = 3;
    final static int TYPE_FOR_SEND_QUIT = 4;

    final static String REDAY_STR = "(READY)";
    final static String HEART_STR = "(H)";
    final static String QUIR_STR = "(QUIT)";
    final static String NO_ACTION_STR = "";

    public MessageSender(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {

            PrintWriter printWriter1 = new PrintWriter(socket.getOutputStream());
            while (true) {

                if (!isKeySend) {
                    printWriter1.write("(feb9945c4df3434aa9b8b202e7541b1e)");//feb9945c4df3434aa9b8b202e7541b1e
                    printWriter1.flush();
                    isKeySend = true;
                    MessageListener.sFlagTypeForOK = MessageListener.TYPE_FOR_OK_KEY;
                    System.out.println("key : " + Client.sOnOff + " isKeySend = " + isKeySend);
                }

                synchronized ((Object) Client.sOnOff) {
                    if (Client.sOnOff == 1) {
                        switch (sSendType) {
                            case TYPE_FOR_SEND_READY: {
                                mCommand = REDAY_STR;
                                Client.sSendHeartOnOff = 0;
                                break;
                            }
                            case TYPE_FOR_SEND_CONTROL: {
//                                mCommand = NO_ACTION_STR;
                                mCommand = MapUtil.getCommand();
                                break;
                            }
                            case TYPE_FOR_SEND_QUIT: {
                                mCommand = QUIR_STR;
                                Client.sSendHeartOnOff = 0;
                                break;
                            }
                            default: {
                                break;
                            }
                        }
                        System.out.println(mCommand);
                        printWriter1.write(mCommand);
                        printWriter1.flush();
                        Client.sOnOff = 2;

                    }


                }
                //System.out.println("key : "+sOnOff);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

}


