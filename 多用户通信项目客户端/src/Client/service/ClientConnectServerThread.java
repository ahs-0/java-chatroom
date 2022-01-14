package Client.service;


import common.Message;
import common.MessageType;

import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientConnectServerThread extends Thread {
    //该线程必须只有Socket
    private Socket socket;

    public ClientConnectServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        //应为Thread需要在后台和服务器通信，因此做一个while循环
        while (true) {
            try {
                System.out.println("客户端线程，等待从服务端读取消息");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                //如果服务器没有发送Message对象，线程会阻塞在这里
                Message message = (Message) ois.readObject();
                //注意后面，需要去使用message
                //判断这个message类型，然后做相应的业务处理
                //如果是读取到的是 服务端返回的在线用户列表
                if (message.getMesType().equals(MessageType.MESSAGE_RET_ONLINE_FRIEND)) {
                    //取出在线列表信息，并显示
                    //规定
                    String[] onlineUsers = message.getContent().split(" ");
                    System.out.println("\n==========当前在线用户列表==========");
                    for (int i = 0; i < onlineUsers.length; i++) {
                        System.out.println("用户：" + onlineUsers[i]);
                    }
                } else if (message.getMesType().equals(MessageType.MESSAGE_COMM_MES)) {//普通聊天
                    //把从服务器转发的消息，显示到控制台即可
                    System.out.println("\n" + message.getSendTime() + "\t" + message.getSender() + "对：" + message.getGetter() + message.getContent());
                } else if (message.getMesType().equals(MessageType.MESSAGE_TO_ALL_MES)) {//群聊天
                    System.out.println("\n" + message.getSendTime() + "\t" + message.getSender() + "说：" + message.getContent());
                } else {
                    System.out.println("是其他类型的massage，暂时不处理");
                    //TODO
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //为了更方便的得到Socket
    public Socket getSocket() {
        return socket;
    }
}
