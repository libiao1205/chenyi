/*
Copyright [2020] [https://www.xiaonuo.vip]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

XiaoNuo采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：

1.请不要删除和修改根目录下的LICENSE文件。
2.请不要删除和修改XiaoNuo源码头部的版权声明。
3.请保留源码和相关描述文件的项目出处，作者声明等。
4.分发源码时候，请注明软件出处 https://gitee.com/xiaonuo/xiaonuo-vue
5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/xiaonuo/xiaonuo-vue
6.若您的项目无法满足以上几点，可申请商业授权，获取XiaoNuo商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package com.cn.sundeinfo.core.consts;

/**
 * 通用常量
 *
 * @author xuyuxiang
 * @date 2020/3/11 16:51
 */
public interface CommonConstant {

    /**
     * id
     */
    String ID = "id";

    /**
     * parentId
     */
    String PARENTID = "parentId";

    /**
     * 名称
     */
    String NAME = "name";

    /**
     * 编码
     */
    String CODE = "code";

    /**
     * 值
     */
    String VALUE = "value";

    /**
     * 默认标识状态的字段名称
     */
    String STATUS = "status";

    /**
     * 默认逻辑删除的状态值
     */
    String DEFAULT_LOGIC_DELETE_VALUE = "2";

    /**
     * 用户代理
     */
    String USER_AGENT = "User-Agent";

    /**
     * 请求头token表示
     */
    String AUTHORIZATION = "Authorization";

    /**
     * token名称
     */
    String TOKEN_NAME = "token";

    /**
     * token类型
     */
    String TOKEN_TYPE_BEARER = "Bearer";

    /**
     * 首页提示语
     */
    String INDEX_TIPS = "Welcome To Chenyishujuku";

    /**
     * 未知标识
     */
    String UNKNOWN = "Unknown";

    /**
     * 默认包名
     */
    String DEFAULT_PACKAGE_NAME = "com.cn.sundeinfo";

    /**
     * 默认密码
     */
    String DEFAULT_PASSWORD = "123456";

    /**
     * 请求号在header中的唯一标识
     */
    String REQUEST_NO_HEADER_NAME = "Request-No";

    /**
     * 数据库链接URL标识
     */
    String DATABASE_URL_NAME = "DATABASE_URL_NAME";

    /**
     * 团队名称
     */
    public static final String TEAM_NAME = "TEAM";

    /**
     * 团队邮箱
     */
    public static final String TEAM_EMAIL = "TEAM@xiaonuo.cn";

    /** 应用启动信息常量 **/
    public static final String APP_START_INFO =
            "\n"+
            "==============================================================\n"+
            "\tApp:\t{}\n"+
            "\tState:\tapp is running\n"+
            "\tPID:\t{}\n"+
            "\tDate:\tstarted at {}\n"+
            "\tAuth:\t"+TEAM_NAME+"\n"+
            "\tEmail:\t"+TEAM_EMAIL+"\n"+
            "\tURLs:\thttp://{}:{}{}\n"+
            "==============================================================";

    /**
     * 滑块验证码
     */
    String IMAGE_CODE_TYPE = "blockPuzzle";
    /**
     * 点选验证码
     */
//    String IMAGE_CODE_TYPE = "clickWord";

}
