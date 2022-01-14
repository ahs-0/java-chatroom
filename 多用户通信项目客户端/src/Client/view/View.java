package Client.view;

import Client.service.MessageClientServer;
import Client.service.UserClientService;
import Client.utils.Utility;

/**
 * 客户端的菜单界面
 */
@SuppressWarnings({"all"})
public class View {
    private boolean loop = true;//控制是否显示菜单
    private String key = "";//接收用户的键盘输入
    private UserClientService userClientService = new UserClientService();//对象是用于登录服务/注册用户
    private MessageClientServer messageClientServer = new MessageClientServer();//对象用于私聊/群聊

    public static void main(String[] args) {
        new View().mainMenu();
        System.out.println("网络通信系统退出.....");
        System.exit(0);
    }

    //显示主菜单
    private void mainMenu() {
        while (loop) {
            System.out.println("==========欢迎登录网络通信系统==========");
            System.out.println("\t\t1.登录系统");
            System.out.println("\t\t2.注册");
            System.out.println("\t\t9.退出系统");
            System.out.print("请输入你的选择：");
            key = Utility.readString(1);

            //根据用户的输入，来处理不同的逻辑
            switch (key) {
                case "1":
                    System.out.print("请输入用户号：");
                    String userId = Utility.readString(50);
                    System.out.print("请输入密 码：");
                    String pwd = Utility.readString(50);
                    //这里还没写完，这里要与服务器进行交互，验证密码是否正确，是否登录成功，比较复杂
                    if (userClientService.checkUser(userId, pwd)) {
                        System.out.println("==========欢迎（用户" + userId + "登录成功）==========");
                        //进入二级菜单
                        while (loop) {
                            System.out.println("\n======网络通信系统二级菜单（用户" + userId + "）======");
                            System.out.println("\t\t 1 显示在线用户列表");
                            System.out.println("\t\t 2 群发消息");
                            System.out.println("\t\t 3 私聊消息");
//                            System.out.println("\t\t 4 发送文件");
                            System.out.println("\t\t 9 退出系统");
                            System.out.print("请输入你的选择：");
                            key = Utility.readString(1);
                            switch (key) {
                                case "1":
//                                    System.out.println("显示在线用户列表");
                                    userClientService.onlineFrienfList();
                                    break;
                                case "2":
//                                    System.out.println("群发消息");
                                    System.out.print("请输入想要说的话：");
                                    String content1 = Utility.readString(100);
                                    messageClientServer.endMessageToALL(content1, userId);
                                    break;
                                case "3":
//                                    System.out.println("私聊消息");
                                    System.out.print("请输入想聊天的用户号（在线）：");
                                    String getterId = Utility.readString(50);
                                    System.out.print("请输入想要说的话：");
                                    String content2 = Utility.readString(100);
                                    //编写一个方法，将消息发送给服务器端
                                    messageClientServer.sendMessageToOne(content2, userId, getterId);
                                    break;
//                                case "4":
//                                    System.out.println("发送文件");
//                                    break;
                                case "9":
                                    //调用方法，给服务端发送退出请求
                                    userClientService.logout();
                                    loop = false;
                                    break;
                            }
                        }
                    } else {
                        System.out.println("=====登录失败=====");
                    }
                    break;
                case "2":
                    while(true) {
                        System.out.print("输入你的账号：");
                        String cUserId = Utility.readString(64);
                        System.out.print("请设置你的密码：");
                        String cpwd = Utility.readString(64);
                        System.out.print("请确认你的密码：");
                        String rpwd = Utility.readString(64);
                        if (cpwd.equals(rpwd) != true) {
                            System.out.println("输入错误");
                        }else{
                            if(userClientService.createUser(cUserId, cpwd)) {
                                break;
                            }else{
                                System.out.println("注册出错");
                            }
                        }
                    }
                case "9":
                    loop = false;
                    break;
            }
        }
    }
}
