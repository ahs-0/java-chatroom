package QQServer.server;


import common.Message;
import common.MessageType;
import common.User;
import date.Command;
import date.InsertCommand;
import date.QueryCommand;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * 这是服务器，在监听9999，等待客户端的连接，并保持通信
 */
@SuppressWarnings({"all"})
public class Server {
    private ServerSocket ss = null;
    private Command command=null;

    //验证用户是否有效
    private boolean checkUser(User u) {
        command=new QueryCommand();
        return command.execute(u);
    }
    //验证注册是否正确
    private boolean createUser(User u){
        Command command=new InsertCommand();
        return command.execute(u);
    }

    public Server() {
        try {
            System.out.println("服务端在9999端口监听...");
            ss = new ServerSocket(9999);
            //当和某个客户连接后，会继续监听，因此while
            while (true) {
                Socket socket = ss.accept();
                //得到socket关联的对象输入流
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                //得到socket关联的对象输出流
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                User u = (User) ois.readObject();//读取客户端发送的User对象
                Message message = new Message();
                if(u.getCreate()==null){
                    if (checkUser(u)) {
                        //登录成功发送Message对象
                        message.setMesType(MessageType.MESSAGE_LOGIN_SUCCEED);
                        oos.writeObject(message);
                        //创建一个线程，和客户端保持通信，该线程需要持有socket对象
                        ServerConnectClientThread serverConnectClientThread = new ServerConnectClientThread(socket, u.getUserId());
                        //启动该线程
                        serverConnectClientThread.start();
                        //把线程对象，放入到一个集合中，进行管理
                        ManageClientThreads.addClientThread(u.getUserId(), serverConnectClientThread);
                    } else {

                        System.out.println("用户 id=" + u.getUserId() + "pwd=" + u.getPassWord() + "验证失败");
                        //登录失败发送Message对象
                        message.setMesType(MessageType.MESSAGE_LOGIN_FAIL);
                        oos.writeObject(message);
                        //关闭socket
                        socket.close();
                    }
                }else if(u.getCreate().equals(MessageType.MESSAGE_CREATE)){
                    if(createUser(u)){
                        //创建成功，发送Message对象
                        message.setMesType(MessageType.MESSAGE_CREATE_SUCCEED);
                        oos.writeObject(message);
                    }else{
                        message.setMesType(MessageType.MESSAGE_CREATE_FAIL);
                        oos.writeObject(message);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭ServerSocket资源
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
























