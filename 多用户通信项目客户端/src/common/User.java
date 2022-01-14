package common;

import java.io.Serializable;

/**
 * 表示一个用户/客户信息
 */
//该类在服务端和客户端之间传递（使用对象流方式），需要被序列化
@SuppressWarnings({"all"})
public class User implements Serializable {
    private String userId;//用户Id/用户名
    private String passWord;//用户密码
    private String create;//用于注册用户
    private static final long serialVersionUID = 1L;

    public User() {
    }

    public User(String userId, String passWord) {
        this.userId = userId;
        this.passWord = passWord;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getCreate() {
        return create;
    }

    public void setCreate(String create) {
        this.create = create;
    }
}
