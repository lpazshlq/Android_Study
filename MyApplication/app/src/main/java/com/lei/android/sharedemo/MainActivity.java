package com.lei.android.sharedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXTextObject;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class MainActivity extends AppCompatActivity {

    private EditText edit_share_message;
    private CheckBox cbox_share_friends;

    private String share_message;

    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, false);
        edit_share_message = (EditText) findViewById(R.id.edit_share_message);
        cbox_share_friends = (CheckBox) findViewById(R.id.cbox_share_friends);
    }

    public void toShareMessage(View v) {
        getShareMessage();
        if (!"".equals(share_message)){
            WXTextObject textObj = new WXTextObject();
            textObj.text = share_message;

            WXMediaMessage msg = new WXMediaMessage();
            msg.mediaObject = textObj;
            msg.description = share_message;

            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = buildTransaction("text");
            req.message = msg;
            req.scene = cbox_share_friends.isChecked()?SendMessageToWX.Req.WXSceneTimeline:SendMessageToWX.Req.WXSceneSession;

            api.sendReq(req);

        }else{
            Toast.makeText(MainActivity.this, "分享内容为空", Toast.LENGTH_SHORT).show();
        }
    }
    
    public void toRegisterApp(View v) {
//        if(api.registerApp(Constants.APP_ID)){
//            Toast.makeText(MainActivity.this, "APP注册成功", Toast.LENGTH_SHORT).show();
//        }else{
//            Toast.makeText(MainActivity.this, "APP注册失败", Toast.LENGTH_SHORT).show();
//        }
        showShare();
    }
    
    private void getShareMessage(){
        share_message = edit_share_message.getText()+"";
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }



    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("分享");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }
}
