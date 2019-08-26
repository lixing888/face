package com.baidu.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author by Abbot
 * @date 2018/09/13 15:43
 **/
@ApiModel(value = "ServerResponse", description = "统一响应封装对象,所有响应抽象参数对象")
public class ServerResponse<T> extends Message {

    /**
     * 响应状态码
     */
    @ApiModelProperty(value = "响应状态码",position =1)
    private String retCode;

    /**
     * 响应中文描述，用于提示用户
     */
    @ApiModelProperty(value = "响应中文描述，用于提示用户",position =2)
    private String retDesc;

    /**
     * 返回数据
     */
    @ApiModelProperty(value = "响应返回数据",position =3)
    private T rspBody;


    public ServerResponse(String retCode) {
        this.retCode = retCode;
    }

    public ServerResponse(T rspBody) {
        this.rspBody = rspBody;
    }

    public ServerResponse(String retCode, String retDesc, T rspBody) {
        this.retCode = retCode;
        this.retDesc = retDesc;
        this.rspBody = rspBody;
    }

    public ServerResponse(String retCode, String retDesc) {
        this.retCode = retCode;
        this.retDesc = retDesc;
    }

    @Override
    public String toString() {
        return "ServerResponse{" +
                "retCode='" + retCode + '\'' +
                ", retDesc='" + retDesc + '\'' +
                ", rspBody=" + rspBody +
                '}';
    }
}
