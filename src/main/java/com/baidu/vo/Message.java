package com.baidu.vo;

import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

/**
 * @author lixing
 */
@Data
public class Message {

    private String retCode;
    private String retDesc;
    private T rspBody;
}
