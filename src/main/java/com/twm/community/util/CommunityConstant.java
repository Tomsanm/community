package com.twm.community.util;


// 常用常量: 用于邮件激活的逻辑

public interface CommunityConstant {
    int ACTIVATION_SUCCESS = 0;

    int ACTIVATION_PEPEAT = 1;

    int ACTIVATION_FAILURE = 2;

    int REMEMBER_EXPIRED_TIME = 1000*3600*24;

    int EXPIRED_TIME = 1000*3600*1;;

}
