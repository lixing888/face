package com.baidu.controller;

import com.baidu.aip.face.AipFace;
import com.baidu.aip.face.MatchRequest;
import com.baidu.aip.ocr.AipOcr;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * @author lixing
 */
@RestController
@RequestMapping("/ocrController")
public class OCRController {
    /**
     * 接口申请免费，请自行申请使用，如果学习使用可以用下
     */
    /**
     * public static final String APP_ID = "15742445";
     * public static final String API_KEY = "LXrztEOzQxfef66DLIDQYpIG";
     * public static final String SECRET_KEY = "gbDodnochc8jYjlAHADDgyyas9mrlmkF";
     */
    private static final String APP_ID = "11275267";
    private static final String API_KEY = "WC1wOLjGjSCVa0X7CDWkdZbz";
    private static final String SECRET_KEY = "dqMAkX80svGFomgBA4LqOcuet7LvaGBx";

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("detect_direction", "true");
        options.put("detect_risk", "false");
        // 识别身份证正面（正面图片为本地图片，即D:\1.png）
        JSONObject frontres = client.idcard("D:\\front.png", "front", options);
        System.out.println("正面图片:" + frontres.toString(2));
        // 识别身份证反面（反面图片为本地图片，即D:\2.png）
        JSONObject backres = client.idcard("D:\\back.jpg", "back", options);
        System.out.println("反面图片:" + backres.toString(2));
    }

    /**
     * @param faceBase
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/ocrimg", method = RequestMethod.POST)
    public Boolean ocrimg(String faceBase) throws IOException {
        System.out.println(faceBase);
        return match(faceBase, faceBase);
    }

    /**
     * <p>Title: go</p>
     * <p>Description: </p>
     *
     * @param baseA 图片a
     * @param baseB 图片b
     */
    public boolean match(String baseA, String baseB) {
        AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);
        ArrayList<MatchRequest> requests = new ArrayList<>();
        requests.add(new MatchRequest(baseA, "BASE64"));
        requests.add(new MatchRequest(baseB, "BASE64"));
        try {
            JSONObject res = client.match(requests);
            JSONObject jsonObj = new JSONObject(res.toString());
            JSONObject scoreObj = (JSONObject) jsonObj.get("result");
            Integer score = (Integer) scoreObj.get("score");
            System.out.println("分数:" + score);
            return score > 80;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }
}
