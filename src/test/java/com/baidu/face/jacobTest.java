package com.baidu.face;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 文字转语音测试
 * jdk bin文件中需要导入jacob-1.18-M2-x64.dll
 *
 * @author lixing
 * @date: 2019年8月25日 上午10:05:21
 */
@Slf4j
public class jacobTest {
    public static void main(String[] args) {
        ActiveXComponent ax = null;
        //String str = "请A9527号到1号窗口";
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        System.out.println("现在时间是：" + dateString);
        String str = "现在时间是" + dateString +
                "雪压枝头低,虽低不着泥。一朝红日出,依旧与天齐。OK！" +
                "未曾清贫难成人，不经打击老天真。自古英雄出炼狱，从来富贵入凡尘。" +
                "醉生梦死谁成器，拓马长枪定乾坤。挥军千里山河在，立名扬威传后人。" +
                "世人都晓神仙好\n" +
                "唯有功名忘不了\n" +
                "古今将相在何方\n" +
                "荒冢一堆草没了\n" +
                "世人都晓神仙好\n" +
                "只有金银忘不了\n" +
                "终朝只恨聚无多\n" +
                "及到多时眼闭了" +
                "陋室空堂，当年笏满床；衰草枯杨，曾为歌舞场。" +
                "蛛丝儿结满雕梁，绿纱今又糊在蓬窗上。" +
                "说什么脂正浓、粉正香，如何两鬓又成霜？" +
                "昨日黄土垅头送白骨，今宵红绡帐底卧鸳鸯。" +
                "金满箱，银满箱，转眼乞丐人皆谤。" +
                "正叹他人命不长，哪知自己归来丧！" +
                "训有方，保不定日后做强梁；择膏粱，谁承望流落在烟花巷！" +
                "因嫌纱帽小，致使锁枷扛；昨怜破袄寒，今嫌紫蟒长。" +
                "乱烘烘你方唱罢我登场，反认他乡是故乡。甚荒唐，到头来都是为他人做嫁衣裳。";
        try {
            ax = new ActiveXComponent("Sapi.SpVoice");
            //运行时输出语音内容
            Dispatch spVoice = ax.getObject();
            // 音量 0-100
            ax.setProperty("Volume", new Variant(100));
            // 语音朗读速度 -10 到 +10
            ax.setProperty("Rate", new Variant(-2));
            // 执行朗读
            Dispatch.call(spVoice, "Speak", new Variant(str));

            //下面是构建文件流把生成语音文件
            ax = new ActiveXComponent("Sapi.SpFileStream");
            Dispatch spFileStream = ax.getObject();

            ax = new ActiveXComponent("Sapi.SpAudioFormat");
            Dispatch spAudioFormat = ax.getObject();

            //设置音频流格式
            Dispatch.put(spAudioFormat, "Type", new Variant(22));
            //设置文件输出流格式
            Dispatch.putRef(spFileStream, "Format", spAudioFormat);
            //调用输出 文件流打开方法，创建一个.wav文件
            Dispatch.call(spFileStream, "Open", new Variant("F:\\test.wav"), new Variant(3), new Variant(true));
            //设置声音对象的音频输出流为输出文件对象
            Dispatch.putRef(spVoice, "AudioOutputStream", spFileStream);
            //设置音量 0到100
            Dispatch.put(spVoice, "Volume", new Variant(100));
            //设置朗读速度
            Dispatch.put(spVoice, "Rate", new Variant(-2));
            //开始朗读
            Dispatch.call(spVoice, "Speak", new Variant(str));

            //关闭输出文件
            Dispatch.call(spFileStream, "Close");
            Dispatch.putRef(spVoice, "AudioOutputStream", null);

            spAudioFormat.safeRelease();
            spFileStream.safeRelease();
            spVoice.safeRelease();
            ax.safeRelease();
            log.info("文字转语音成功!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
