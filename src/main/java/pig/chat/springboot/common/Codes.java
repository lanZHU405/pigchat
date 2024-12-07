package pig.chat.springboot.common;

import java.sql.SQLTransactionRollbackException;

public class Codes {

    public static final String TWO = "2";
    public static final Integer RESPONSE_OK = 200;
    public static final Integer RESPONSE_NOT_OK = 500;
    public static final String SUCCESS = "请求成功";
    public static final String SYSTEM_ERROR = "系统错误";

    public static final String CODE_FAIL = "验证码错误或失效";
    public static final Integer RESPONSE_NOT_LOGIN = 401;
    public static final String ADD_YES = "添加成功";
    public static final String ADD_NO = "添加失败";
    public static final String DELETE_YES = "删除成功";
    public static final String DELETE_NO = "删除失败";
    public static final String GET_INFO_FAILED = "获取数据失败";
    public static final String UPDATE_YES = "修改成功";
    public static final String UPDATE_NO = "修改失败";
    public static final String NAME_OR_PASSWORD_FIELD = "用户名或密码错误";
    public static final String ROLE_FIELD = "权限错误";
    public static final String LOGIN_YES = "登录成功";
    public static final String USER_EXISTS = "该用户已存在";
    public static final String USER_NOT_EXISTS = "该用户不存在";
    public static final String REGISTER_SUCCESS = "注册成功";
    public static final String REGISTER_FIELD = "注册失败";
    public static final String TYPE_EXISTS = "该类型已存在";
    public static final String ADDRESS_EXISTS = "该地址已存在";
    public static final String NOTICE_EXISTS = "该公告已存在";
    public static final String NOTICE_NOT_EXISTS = "该公告不存在";
    public static final String NOT_LOGIN = "未登录";
    public static final String GOOD_EXISTS = "已收藏该商品";
    public static final String SHOP_GOOD_EXISTS = "已添加该商品";
    public static final String LIKE_YES = "点赞成功";
    public static final String LIKE_NO = "点赞取消";
    public static final String CANCEL_YES = "取消成功";
    public static final String CANCEL_NO = "取消失败";
    public static final String ADMIN = "admin";
    public static final String USER = "user";
    public static final String SELLER = "seller";

}

