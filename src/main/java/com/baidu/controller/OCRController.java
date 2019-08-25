package com.baidu.controller;

import com.baidu.aip.face.AipFace;
import com.baidu.aip.face.MatchRequest;
import com.baidu.aip.ocr.AipOcr;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lixing
 */
@RestController
@RequestMapping("/ocrController")
public class OCRController {
    /**
     * 接口申请免费，请自行申请使用，如果学习使用可以用下
     */
    public static final String APP_ID = "15742445";
    public static final String API_KEY = "LXrztEOzQxfef66DLIDQYpIG";
    public static final String SECRET_KEY = "gbDodnochc8jYjlAHADDgyyas9mrlmkF";

    /**
     * 备用
    private static final String APP_ID = "11275267";
    private static final String API_KEY = "WC1wOLjGjSCVa0X7CDWkdZbz";
    private static final String SECRET_KEY = "dqMAkX80svGFomgBA4LqOcuet7LvaGBx";
     */
    @ResponseBody
    @RequestMapping(value = "/ocrimg", method = RequestMethod.POST)
    public String ocrimg(MultipartFile file) throws IOException {

        HashMap options = new HashMap();
        options.put("language_type", "CHN_ENG");
        //高精度识别一些参数在api文档可以参考
        // options.put("detect_direction", "true");
        // options.put("detect_language", "true");
        // options.put("probability", "true");
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
        byte[] bite = file.getBytes();
        System.out.println(file.getName());
        System.out.println(file.getOriginalFilename() + "====" + file.getResource());
        JSONObject jsonObject = client.basicGeneral(bite, options);
        System.out.println(jsonObject.toString());
        return jsonObject.toString();
    }

    /**
     *
     * <p>Title: go</p>
     * <p>Description: </p>
     * @param baseA 图片a
     * @param baseB	图片b
     */
    public void match(String baseA,String baseB) {

        AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);
        ArrayList<MatchRequest> requests = new ArrayList<MatchRequest>();
        requests.add(new MatchRequest(baseA, "BASE64"));
        requests.add(new MatchRequest(baseB, "BASE64"));
        try {
            JSONObject res = client.match(requests);
            JSONObject jsonObj = new JSONObject(res.toString());
            JSONObject scoreObj = (JSONObject) jsonObj.get("result");
            Double score =(Double)scoreObj.get("score");
            System.out.println(score);
        } catch (JSONException e) {
            System.out.println("认证失败!");
        }
    }

}

