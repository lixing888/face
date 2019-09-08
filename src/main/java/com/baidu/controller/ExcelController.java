package com.baidu.controller;

import com.baidu.service.ExcelService;
import com.baidu.util.ExcelUtils;
import com.baidu.vo.BusClick;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author lixing
 * @description
 * @date 2018/9/17 11:24
 **/
@RestController
@RequestMapping("/excel")
@Api(tags = "excel导入导出",value = "excel导入导出", description = "excel导入导出")
public class ExcelController extends BaseController {

    @Autowired
    ExcelService excelService;

    @ApiOperation(value = "导出execl",notes = "导出execl")
    @RequestMapping(value = "/exportExcel", method = RequestMethod.GET)
    public void exportExcel()  throws IOException {

        HttpServletResponse response = getHttpResponse();
        List<BusClick> resultList = excelService.getBusClick();
        long t1 = System.currentTimeMillis();
        ExcelUtils.writeExcel(response, resultList, BusClick.class);
        long t2 = System.currentTimeMillis();
        System.out.println(String.format("write over! cost:%sms", (t2 - t1)));
    }

    private HttpServletResponse getHttpResponse() {
        return null;
    }


    @ApiOperation(value = "读取execl",notes = "读取execl")
    @RequestMapping(value = "/readExcel", method = RequestMethod.POST)
    public void readExcel(MultipartFile file){

        long t1 = System.currentTimeMillis();
        List<BusClick> list = ExcelUtils.readExcel("", BusClick.class, file);
        long t2 = System.currentTimeMillis();
        System.out.println(String.format("read over! cost:%sms", (t2 - t1)));
        list.forEach(
                b -> System.out.println(b.getCityCode())
        );
    }
}
