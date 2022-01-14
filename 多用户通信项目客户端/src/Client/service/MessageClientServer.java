package Client.service;

import common.Message;
import common.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

/**
 * 该类/对象，提供和消息相关的服务方法
 */
public class MessageClientServer {
    /**
     * @param content  消息内容
     * @param senderId 发送用户id
     */
    public void endMessageToALL(String content, String senderId) {
        //构建message对象
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_TO_ALL_MES);//普通的聊天消息
        message.setSender(senderId);
        message.setContent(content);
        message.setSendTime(new Date().toString());//发送时间设置到message对象
        System.out.println(senderId + "对" + "所以人说：" + content);
        //发送给客户端
        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream
                            (ManageClientConnectServerThread
                                    .getClientConnectServerThread
                                            (senderId).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param content  内容
     * @param senderId 发送用户id
     * @param getterId 接收用户id
     */
    public void sendMessageToOne(String content, String senderId, String getterId) {
        //构建message对象
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_COMM_MES);//普通的聊天消息
        message.setSender(senderId);
        message.setGetter(getterId);
        message.setContent(content);
        message.setSendTime(new Date().toString());//发送时间设置到message对象
        System.out.println(senderId + "对" + getterId + "说：" + content);
        //发送给客户端
        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream
                            (ManageClientConnectServerThread
                                    .getClientConnectServerThread
                                            (senderId).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
