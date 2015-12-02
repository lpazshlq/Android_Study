package com.lei.homework;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<Song> list = new ArrayList<>();
    MyAdapter adapter = new MyAdapter();
    String str = "{\n" +
            "\t\"showapi_res_code\": 0,\n" +
            "\t\"showapi_res_error\": \"\",\n" +
            "\t\"showapi_res_body\": {\n" +
            "\t\t\"pagebean\": {\n" +
            "\t\t\t\"allNum\": 196,\n" +
            "\t\t\t\"allPages\": 7,\n" +
            "\t\t\t\"contentlist\": [\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"albumid\": 62660,\n" +
            "\t\t\t\t\t\"albummid\": \"00449cf44ccf8n\",\n" +
            "\t\t\t\t\t\"albumname\": \"Words & Music Final Live With 家驹\",\n" +
            "\t\t\t\t\t\"albumpic_big\": \"http://i.gtimg.cn/music/photo/mid_album_300/8/n/00449cf44ccf8n.jpg\",\n" +
            "\t\t\t\t\t\"albumpic_small\": \"http://i.gtimg.cn/music/photo/mid_album_90/8/n/00449cf44ccf8n.jpg\",\n" +
            "\t\t\t\t\t\"downUrl\": \"http://tsmusic24.tc.qq.com/4833285.mp3\",\n" +
            "\t\t\t\t\t\"m4a\": \"http://ws.stream.qqmusic.qq.com/4833285.m4a?fromtag=46\",\n" +
            "\t\t\t\t\t\"media_mid\": \"001fhSpB0P7buZ\",\n" +
            "\t\t\t\t\t\"singerid\": 2,\n" +
            "\t\t\t\t\t\"singername\": \"BEYOND\",\n" +
            "\t\t\t\t\t\"songid\": 4833285,\n" +
            "\t\t\t\t\t\"songname\": \"海阔天空 (Edited Version)\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"albumid\": 8561,\n" +
            "\t\t\t\t\t\"albummid\": \"002XWx9122oM17\",\n" +
            "\t\t\t\t\t\"albumname\": \"海阔天空\",\n" +
            "\t\t\t\t\t\"albumpic_big\": \"http://i.gtimg.cn/music/photo/mid_album_300/1/7/002XWx9122oM17.jpg\",\n" +
            "\t\t\t\t\t\"albumpic_small\": \"http://i.gtimg.cn/music/photo/mid_album_90/1/7/002XWx9122oM17.jpg\",\n" +
            "\t\t\t\t\t\"downUrl\": \"http://tsmusic24.tc.qq.com/102396.mp3\",\n" +
            "\t\t\t\t\t\"m4a\": \"http://ws.stream.qqmusic.qq.com/102396.m4a?fromtag=46\",\n" +
            "\t\t\t\t\t\"media_mid\": \"002i1vdD1Gv6t7\",\n" +
            "\t\t\t\t\t\"singerid\": 4427,\n" +
            "\t\t\t\t\t\"singername\": \"信乐团\",\n" +
            "\t\t\t\t\t\"songid\": 102396\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"albumid\": 1064074,\n" +
            "\t\t\t\t\t\"albummid\": \"003IpAjl1kDOIm\",\n" +
            "\t\t\t\t\t\"albumname\": \"中国新声代第三季 第5期\",\n" +
            "\t\t\t\t\t\"albumpic_big\": \"http://i.gtimg.cn/music/photo/mid_album_300/I/m/003IpAjl1kDOIm.jpg\",\n" +
            "\t\t\t\t\t\"albumpic_small\": \"http://i.gtimg.cn/music/photo/mid_album_90/I/m/003IpAjl1kDOIm.jpg\",\n" +
            "\t\t\t\t\t\"downUrl\": \"http://tsmusic24.tc.qq.com/103147830.mp3\",\n" +
            "\t\t\t\t\t\"m4a\": \"http://ws.stream.qqmusic.qq.com/103147830.m4a?fromtag=46\",\n" +
            "\t\t\t\t\t\"singerid\": 366577,\n" +
            "\t\t\t\t\t\"singername\": \"汤晶锦\",\n" +
            "\t\t\t\t\t\"songid\": 103147830,\n" +
            "\t\t\t\t\t\"songmid\": \"0023Dp0X3kFGpP\",\n" +
            "\t\t\t\t\t\"songname\": \"海阔天空 (Live)\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"albumid\": 461355,\n" +
            "\t\t\t\t\t\"albummid\": \"001otv1l2V8Sh6\",\n" +
            "\t\t\t\t\t\"albumname\": \"我是歌手第二季 突围赛\",\n" +
            "\t\t\t\t\t\"albumpic_big\": \"http://i.gtimg.cn/music/photo/mid_album_300/h/6/001otv1l2V8Sh6.jpg\",\n" +
            "\t\t\t\t\t\"albumpic_small\": \"http://i.gtimg.cn/music/photo/mid_album_90/h/6/001otv1l2V8Sh6.jpg\",\n" +
            "\t\t\t\t\t\"downUrl\": \"http://tsmusic24.tc.qq.com/5238536.mp3\",\n" +
            "\t\t\t\t\t\"m4a\": \"http://ws.stream.qqmusic.qq.com/5238536.m4a?fromtag=46\",\n" +
            "\t\t\t\t\t\"media_mid\": \"003MkTX747QAXz\",\n" +
            "\t\t\t\t\t\"singerid\": 6621,\n" +
            "\t\t\t\t\t\"singername\": \"曹格\",\n" +
            "\t\t\t\t\t\"songid\": 5238536\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"albumid\": 951206,\n" +
            "\t\t\t\t\t\"albummid\": \"001rFrDm3USHCE\",\n" +
            "\t\t\t\t\t\"albumname\": \"我是歌手第三季 第5期\",\n" +
            "\t\t\t\t\t\"albumpic_big\": \"http://i.gtimg.cn/music/photo/mid_album_300/C/E/001rFrDm3USHCE.jpg\",\n" +
            "\t\t\t\t\t\"albumpic_small\": \"http://i.gtimg.cn/music/photo/mid_album_90/C/E/001rFrDm3USHCE.jpg\",\n" +
            "\t\t\t\t\t\"downUrl\": \"http://tsmusic24.tc.qq.com/102053191.mp3\",\n" +
            "\t\t\t\t\t\"m4a\": \"http://ws.stream.qqmusic.qq.com/102053191.m4a?fromtag=46\",\n" +
            "\t\t\t\t\t\"singerid\": 4419,\n" +
            "\t\t\t\t\t\"singername\": \"韩红\",\n" +
            "\t\t\t\t\t\"songid\": 102053191,\n" +
            "\t\t\t\t\t\"songmid\": \"002SdwDS054C7l\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"albumid\": 433209,\n" +
            "\t\t\t\t\t\"albummid\": \"002WL0KC2U1YmO\",\n" +
            "\t\t\t\t\t\"albumname\": \"快乐男声2013 总决赛\",\n" +
            "\t\t\t\t\t\"albumpic_big\": \"http://i.gtimg.cn/music/photo/mid_album_300/m/O/002WL0KC2U1YmO.jpg\",\n" +
            "\t\t\t\t\t\"albumpic_small\": \"http://i.gtimg.cn/music/photo/mid_album_90/m/O/002WL0KC2U1YmO.jpg\",\n" +
            "\t\t\t\t\t\"downUrl\": \"http://tsmusic24.tc.qq.com/5021487.mp3\",\n" +
            "\t\t\t\t\t\"m4a\": \"http://ws.stream.qqmusic.qq.com/5021487.m4a?fromtag=46\",\n" +
            "\t\t\t\t\t\"singerid\": 163550,\n" +
            "\t\t\t\t\t\"singername\": \"华晨宇\",\n" +
            "\t\t\t\t\t\"songid\": 5021487,\n" +
            "\t\t\t\t\t\"songmid\": \"003oD7zb38xrmk\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"albumid\": 644227,\n" +
            "\t\t\t\t\t\"albummid\": \"003Wx0212XYbFb\",\n" +
            "\t\t\t\t\t\"albumname\": \"中国新声代第二季 第7期\",\n" +
            "\t\t\t\t\t\"albumpic_big\": \"http://i.gtimg.cn/music/photo/mid_album_300/F/b/003Wx0212XYbFb.jpg\",\n" +
            "\t\t\t\t\t\"albumpic_small\": \"http://i.gtimg.cn/music/photo/mid_album_90/F/b/003Wx0212XYbFb.jpg\",\n" +
            "\t\t\t\t\t\"downUrl\": \"http://tsmusic24.tc.qq.com/7063693.mp3\",\n" +
            "\t\t\t\t\t\"m4a\": \"http://ws.stream.qqmusic.qq.com/7063693.m4a?fromtag=46\",\n" +
            "\t\t\t\t\t\"media_mid\": \"004IejcV0gPYgH\",\n" +
            "\t\t\t\t\t\"singerid\": 192193,\n" +
            "\t\t\t\t\t\"singername\": \"余家辉\",\n" +
            "\t\t\t\t\t\"songid\": 7063693\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"downUrl\": \"http://tsmusic24.tc.qq.com/102403.mp3\",\n" +
            "\t\t\t\t\t\"m4a\": \"http://ws.stream.qqmusic.qq.com/102403.m4a?fromtag=46\",\n" +
            "\t\t\t\t\t\"songid\": 102403,\n" +
            "\t\t\t\t\t\"songmid\": \"003Fq3t74dm212\",\n" +
            "\t\t\t\t\t\"songname\": \"想你的夜\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"downUrl\": \"http://tsmusic24.tc.qq.com/102398.mp3\",\n" +
            "\t\t\t\t\t\"m4a\": \"http://ws.stream.qqmusic.qq.com/102398.m4a?fromtag=46\",\n" +
            "\t\t\t\t\t\"songid\": 102398,\n" +
            "\t\t\t\t\t\"songmid\": \"000SARpw3xHa5I\",\n" +
            "\t\t\t\t\t\"songname\": \"千年之恋\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"downUrl\": \"http://tsmusic24.tc.qq.com/102402.mp3\",\n" +
            "\t\t\t\t\t\"m4a\": \"http://ws.stream.qqmusic.qq.com/102402.m4a?fromtag=46\",\n" +
            "\t\t\t\t\t\"songid\": 102402,\n" +
            "\t\t\t\t\t\"songmid\": \"0014FQCN2MLXsO\",\n" +
            "\t\t\t\t\t\"songname\": \"天亮以后说分手\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"albumid\": 8067,\n" +
            "\t\t\t\t\t\"albummid\": \"004CLlFV0mj6fC\",\n" +
            "\t\t\t\t\t\"albumpic_big\": \"http://i.gtimg.cn/music/photo/mid_album_300/f/C/004CLlFV0mj6fC.jpg\",\n" +
            "\t\t\t\t\t\"albumpic_small\": \"http://i.gtimg.cn/music/photo/mid_album_90/f/C/004CLlFV0mj6fC.jpg\",\n" +
            "\t\t\t\t\t\"downUrl\": \"http://tsmusic24.tc.qq.com/95661.mp3\",\n" +
            "\t\t\t\t\t\"m4a\": \"http://ws.stream.qqmusic.qq.com/95661.m4a?fromtag=46\",\n" +
            "\t\t\t\t\t\"media_mid\": \"003vf49P1tPdZJ\",\n" +
            "\t\t\t\t\t\"songid\": 95661,\n" +
            "\t\t\t\t\t\"songname\": \"情人\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"downUrl\": \"http://tsmusic24.tc.qq.com/102399.mp3\",\n" +
            "\t\t\t\t\t\"m4a\": \"http://ws.stream.qqmusic.qq.com/102399.m4a?fromtag=46\",\n" +
            "\t\t\t\t\t\"media_mid\": \"000NQPBR2i0nEf\",\n" +
            "\t\t\t\t\t\"songid\": 102399,\n" +
            "\t\t\t\t\t\"songname\": \"活该\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"downUrl\": \"http://ws.stream.qqmusic.qq.com/TK601f8669f48d8ce044dba9f7f99c7a05dc.mp3?fromtag=0\",\n" +
            "\t\t\t\t\t\"m4a\": \"http://ws.stream.qqmusic.qq.com/0.m4a?fromtag=46\",\n" +
            "\t\t\t\t\t\"singerid\": 0,\n" +
            "\t\t\t\t\t\"songname\": \"面对爱情 (伴奏)\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"downUrl\": \"http://ws.stream.qqmusic.qq.com/TK60a427832e1c660852a926af3fa10aa250.mp3?fromtag=0\",\n" +
            "\t\t\t\t\t\"singername\": \"何嘉莉\",\n" +
            "\t\t\t\t\t\"songname\": \"海阔天空 (伴奏)\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"albumid\": 426489,\n" +
            "\t\t\t\t\t\"albummid\": \"003lqNL61X8KzW\",\n" +
            "\t\t\t\t\t\"albumname\": \"DJ舞曲(华语)系列27\",\n" +
            "\t\t\t\t\t\"albumpic_big\": \"http://i.gtimg.cn/music/photo/mid_album_300/z/W/003lqNL61X8KzW.jpg\",\n" +
            "\t\t\t\t\t\"albumpic_small\": \"http://i.gtimg.cn/music/photo/mid_album_90/z/W/003lqNL61X8KzW.jpg\",\n" +
            "\t\t\t\t\t\"downUrl\": \"http://tsmusic24.tc.qq.com/4798413.mp3\",\n" +
            "\t\t\t\t\t\"m4a\": \"http://ws.stream.qqmusic.qq.com/4798413.m4a?fromtag=46\",\n" +
            "\t\t\t\t\t\"singerid\": 12330,\n" +
            "\t\t\t\t\t\"singername\": \"DJ\",\n" +
            "\t\t\t\t\t\"songid\": 4798413,\n" +
            "\t\t\t\t\t\"songmid\": \"001Lf2WN4gJ6R0\",\n" +
            "\t\t\t\t\t\"songname\": \"海阔天空 (DJ版)\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"albumid\": 91313,\n" +
            "\t\t\t\t\t\"albummid\": \"002i0gTj0Bfbq7\",\n" +
            "\t\t\t\t\t\"albumpic_big\": \"http://i.gtimg.cn/music/photo/mid_album_300/q/7/002i0gTj0Bfbq7.jpg\",\n" +
            "\t\t\t\t\t\"albumpic_small\": \"http://i.gtimg.cn/music/photo/mid_album_90/q/7/002i0gTj0Bfbq7.jpg\",\n" +
            "\t\t\t\t\t\"downUrl\": \"http://tsmusic24.tc.qq.com/4914555.mp3\",\n" +
            "\t\t\t\t\t\"m4a\": \"http://ws.stream.qqmusic.qq.com/4914555.m4a?fromtag=46\",\n" +
            "\t\t\t\t\t\"singerid\": 38603,\n" +
            "\t\t\t\t\t\"songid\": 4914555,\n" +
            "\t\t\t\t\t\"songmid\": \"003IZP3L27gVuE\",\n" +
            "\t\t\t\t\t\"songname\": \"天路\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"downUrl\": \"http://tsmusic24.tc.qq.com/4914554.mp3\",\n" +
            "\t\t\t\t\t\"m4a\": \"http://ws.stream.qqmusic.qq.com/4914554.m4a?fromtag=46\",\n" +
            "\t\t\t\t\t\"songid\": 4914554,\n" +
            "\t\t\t\t\t\"songmid\": \"001OgcQv0MgCSB\",\n" +
            "\t\t\t\t\t\"songname\": \"喀秋莎\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"albumid\": 418538,\n" +
            "\t\t\t\t\t\"albummid\": \"001MoQus1nyrsh\",\n" +
            "\t\t\t\t\t\"albumname\": \"我是歌手第一季 第1期\",\n" +
            "\t\t\t\t\t\"albumpic_big\": \"http://i.gtimg.cn/music/photo/mid_album_300/s/h/001MoQus1nyrsh.jpg\",\n" +
            "\t\t\t\t\t\"albumpic_small\": \"http://i.gtimg.cn/music/photo/mid_album_90/s/h/001MoQus1nyrsh.jpg\",\n" +
            "\t\t\t\t\t\"downUrl\": \"http://tsmusic24.tc.qq.com/4709017.mp3\",\n" +
            "\t\t\t\t\t\"m4a\": \"http://ws.stream.qqmusic.qq.com/4709017.m4a?fromtag=46\",\n" +
            "\t\t\t\t\t\"media_mid\": \"001Ufx8N2GdH9x\",\n" +
            "\t\t\t\t\t\"singerid\": 249,\n" +
            "\t\t\t\t\t\"singername\": \"黄贯中\",\n" +
            "\t\t\t\t\t\"songid\": 4709017\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"albumid\": 422399,\n" +
            "\t\t\t\t\t\"albummid\": \"001gr16p1wikdz\",\n" +
            "\t\t\t\t\t\"albumname\": \"DEAR DIARY\",\n" +
            "\t\t\t\t\t\"albumpic_big\": \"http://i.gtimg.cn/music/photo/mid_album_300/d/z/001gr16p1wikdz.jpg\",\n" +
            "\t\t\t\t\t\"albumpic_small\": \"http://i.gtimg.cn/music/photo/mid_album_90/d/z/001gr16p1wikdz.jpg\",\n" +
            "\t\t\t\t\t\"downUrl\": \"http://tsmusic24.tc.qq.com/4771269.mp3\",\n" +
            "\t\t\t\t\t\"m4a\": \"http://ws.stream.qqmusic.qq.com/4771269.m4a?fromtag=46\",\n" +
            "\t\t\t\t\t\"media_mid\": \"003obJ4l0gxSLz\",\n" +
            "\t\t\t\t\t\"singerid\": 40049,\n" +
            "\t\t\t\t\t\"singername\": \"Robynn & Kendy\",\n" +
            "\t\t\t\t\t\"songid\": 4771269\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"downUrl\": \"http://tsmusic24.tc.qq.com/95658.mp3\",\n" +
            "\t\t\t\t\t\"m4a\": \"http://ws.stream.qqmusic.qq.com/95658.m4a?fromtag=46\",\n" +
            "\t\t\t\t\t\"songid\": 95658,\n" +
            "\t\t\t\t\t\"songmid\": \"004FeGwD0rKBBl\",\n" +
            "\t\t\t\t\t\"songname\": \"爸爸妈妈\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"downUrl\": \"http://tsmusic24.tc.qq.com/95662.mp3\",\n" +
            "\t\t\t\t\t\"m4a\": \"http://ws.stream.qqmusic.qq.com/95662.m4a?fromtag=46\",\n" +
            "\t\t\t\t\t\"songid\": 95662,\n" +
            "\t\t\t\t\t\"songmid\": \"001PeJps37WoGW\",\n" +
            "\t\t\t\t\t\"songname\": \"遥かなる梦に Far away\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"downUrl\": \"http://tsmusic24.tc.qq.com/102405.mp3\",\n" +
            "\t\t\t\t\t\"m4a\": \"http://ws.stream.qqmusic.qq.com/102405.m4a?fromtag=46\",\n" +
            "\t\t\t\t\t\"songid\": 102405,\n" +
            "\t\t\t\t\t\"songmid\": \"001lL2dh1SBqxG\",\n" +
            "\t\t\t\t\t\"songname\": \"无可救药爱上你\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"downUrl\": \"http://tsmusic24.tc.qq.com/95659.mp3\",\n" +
            "\t\t\t\t\t\"m4a\": \"http://ws.stream.qqmusic.qq.com/95659.m4a?fromtag=46\",\n" +
            "\t\t\t\t\t\"songid\": 95659,\n" +
            "\t\t\t\t\t\"songmid\": \"004GsGmy0c4SVi\",\n" +
            "\t\t\t\t\t\"songname\": \"和平与爱\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"downUrl\": \"http://tsmusic24.tc.qq.com/95660.mp3\",\n" +
            "\t\t\t\t\t\"m4a\": \"http://ws.stream.qqmusic.qq.com/95660.m4a?fromtag=46\",\n" +
            "\t\t\t\t\t\"songid\": 95660,\n" +
            "\t\t\t\t\t\"songmid\": \"001S7dLU1pEvvI\",\n" +
            "\t\t\t\t\t\"songname\": \"全是爱\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"downUrl\": \"http://tsmusic24.tc.qq.com/770922.mp3\",\n" +
            "\t\t\t\t\t\"m4a\": \"http://ws.stream.qqmusic.qq.com/770922.m4a?fromtag=46\",\n" +
            "\t\t\t\t\t\"songid\": 770922,\n" +
            "\t\t\t\t\t\"songmid\": \"003v4h1M3NxGli\",\n" +
            "\t\t\t\t\t\"songname\": \"身不由己\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"albumid\": 433799,\n" +
            "\t\t\t\t\t\"albummid\": \"002uyz4k1RJd16\",\n" +
            "\t\t\t\t\t\"albumname\": \"摇滚吧，情歌！\",\n" +
            "\t\t\t\t\t\"albumpic_big\": \"http://i.gtimg.cn/music/photo/mid_album_300/1/6/002uyz4k1RJd16.jpg\",\n" +
            "\t\t\t\t\t\"albumpic_small\": \"http://i.gtimg.cn/music/photo/mid_album_90/1/6/002uyz4k1RJd16.jpg\",\n" +
            "\t\t\t\t\t\"downUrl\": \"http://tsmusic24.tc.qq.com/4988881.mp3\",\n" +
            "\t\t\t\t\t\"m4a\": \"http://ws.stream.qqmusic.qq.com/4988881.m4a?fromtag=46\",\n" +
            "\t\t\t\t\t\"media_mid\": \"003pNX8c2Fc8jq\",\n" +
            "\t\t\t\t\t\"singerid\": 220,\n" +
            "\t\t\t\t\t\"singername\": \"范逸臣\",\n" +
            "\t\t\t\t\t\"songid\": 4988881\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"albumid\": 463933,\n" +
            "\t\t\t\t\t\"albummid\": \"003BsgUS2MH9sS\",\n" +
            "\t\t\t\t\t\"albumname\": \"最美和声第二季 第2期\",\n" +
            "\t\t\t\t\t\"albumpic_big\": \"http://i.gtimg.cn/music/photo/mid_album_300/s/S/003BsgUS2MH9sS.jpg\",\n" +
            "\t\t\t\t\t\"albumpic_small\": \"http://i.gtimg.cn/music/photo/mid_album_90/s/S/003BsgUS2MH9sS.jpg\",\n" +
            "\t\t\t\t\t\"downUrl\": \"http://tsmusic24.tc.qq.com/5453166.mp3\",\n" +
            "\t\t\t\t\t\"m4a\": \"http://ws.stream.qqmusic.qq.com/5453166.m4a?fromtag=46\",\n" +
            "\t\t\t\t\t\"media_mid\": \"003UlMSe4g03Se\",\n" +
            "\t\t\t\t\t\"singerid\": 36878,\n" +
            "\t\t\t\t\t\"singername\": \"谷微\",\n" +
            "\t\t\t\t\t\"songid\": 5453166\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"albumid\": 143982,\n" +
            "\t\t\t\t\t\"albummid\": \"002qotxr2KCGdH\",\n" +
            "\t\t\t\t\t\"albumname\": \"中国好声音第一季 第8期\",\n" +
            "\t\t\t\t\t\"albumpic_big\": \"http://i.gtimg.cn/music/photo/mid_album_300/d/H/002qotxr2KCGdH.jpg\",\n" +
            "\t\t\t\t\t\"albumpic_small\": \"http://i.gtimg.cn/music/photo/mid_album_90/d/H/002qotxr2KCGdH.jpg\",\n" +
            "\t\t\t\t\t\"downUrl\": \"http://tsmusic24.tc.qq.com/1904410.mp3\",\n" +
            "\t\t\t\t\t\"m4a\": \"http://ws.stream.qqmusic.qq.com/1904410.m4a?fromtag=46\",\n" +
            "\t\t\t\t\t\"singerid\": 61946,\n" +
            "\t\t\t\t\t\"singername\": \"李维真\",\n" +
            "\t\t\t\t\t\"songid\": 1904410,\n" +
            "\t\t\t\t\t\"songmid\": \"001kyhYb3CHr9r\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"downUrl\": \"http://tsmusic24.tc.qq.com/480890.mp3\",\n" +
            "\t\t\t\t\t\"m4a\": \"http://ws.stream.qqmusic.qq.com/480890.m4a?fromtag=46\",\n" +
            "\t\t\t\t\t\"songid\": 480890,\n" +
            "\t\t\t\t\t\"songmid\": \"000ghJEJ2gihg1\",\n" +
            "\t\t\t\t\t\"songname\": \"爱不容易说\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"downUrl\": \"http://ws.stream.qqmusic.qq.com/TK60f9d4ab926841725421f2442d371b0a53.mp3?fromtag=0\",\n" +
            "\t\t\t\t\t\"singername\": \"谭锡禧\"\n" +
            "\t\t\t\t}\n" +
            "\t\t\t],\n" +
            "\t\t\t\"currentPage\": 1,\n" +
            "\t\t\t\"maxResult\": 30,\n" +
            "\t\t\t\"notice\": \"\"\n" +
            "\t\t}\n" +
            "\t}\n" +
            "}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main12345);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        new MyAsyncTask().execute();
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.item, parent, false);
            TextView textView = (TextView) convertView.findViewById(R.id.textView);
            textView.setText(list.get(position).getSong_Name());
            return convertView;
        }
    }

    class MyAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPostExecute(Void aVoid) {
            adapter.notifyDataSetChanged();
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                JSONObject jsonObject = new JSONObject(str);
                Log.i("test", jsonObject.toString());
                JSONArray jsonArray = jsonObject.getJSONObject("showapi_res_body").getJSONObject("pagebean").getJSONArray("contentlist");
                list.clear();
                Log.i("test", jsonArray.toString());
                Log.i("test", jsonArray.length() + "");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    Song song = new Song();
                    String song_Name = object.getString("songname");
                    song.setSong_Name(song_Name);
                    list.add(song);
                    Log.e("test", "test" + song.toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
