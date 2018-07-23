package com.example.jxd.myapplication;

import android.app.Activity;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 头条登陆界面
 */
public class layout_toutiao extends AppCompatActivity implements View.OnClickListener {

    private int countSeconds = 60;//读秒倒计时
    private EditText mobil_login,yanzhengma;
    private TextView sendcode;
    private Button btn_login;
    private Context mContext;
    private String usersuccess;
    private Handler mCountHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (countSeconds > 0){
                --countSeconds;
                sendcode.setText("(" + countSeconds + ")后获取验证码");
                mCountHandler.sendEmptyMessageDelayed(0,1000);
            }else{
                countSeconds = 0;
                sendcode.setText("请重新获取验证码");
                startCountBack();

            }
        }
    };
    private String userinfomsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_toutiao);
        initView();
        initEvent();
        initData();
    }

    private void initData() {



    }

    private void initEvent() {
        sendcode.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    private void initView() {
        mobil_login = findViewById(R.id.mobil_login);
        sendcode = findViewById(R.id.sendcode);
        yanzhengma = findViewById(R.id.yanzhengma);
        btn_login = findViewById(R.id.btn_login);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sendcode:
                if (countSeconds == 60){
                    String mobile = mobil_login.getText().toString();
                    Log.e("tag","mobile == " + mobile);
                    getMobile(mobile);
                }else {
                    Toast.makeText(layout_toutiao.this,"不能重复发送验证码",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_login:
                if ("".equals(yanzhengma)){
                    Toast.makeText(layout_toutiao.this,"请输入验证码",Toast.LENGTH_SHORT).show();
                }else if (yanzhengma.getText().length() < 6){
                    Toast.makeText(layout_toutiao.this,"验证码错误",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(layout_toutiao.this,"登陆成功",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    //获取验证码信息，进行验证码请求
    private void getMobile(String mobile) {
        if("".equals(mobile)){
            Log.e("tag","mobile == " + mobile);
            findViewById(R.id.sendcode).isEnabled();
            new AlertDialog.Builder(mContext).setTitle("提示").setMessage("手机号码不存在").show();
        }else if (isMobileNO(mobile) == false){
            new AlertDialog.Builder(mContext).setTitle("提示").setMessage("请输入正确的手机号码").show();
        }else{
            Log.e("tag","正在向您发送验证信息");

            startCountBack();
        }
    }


    //使用正则表达式判断电话号码
    public static boolean isMobileNO(String tel) {
        Pattern p = Pattern.compile("^(13[0-9]|15([0-3]|[5-9])|14[5,7,9]|17[1,3,5,6,7,8]|18[0-9])\\d{8}$");
        Matcher m = p.matcher(tel);
        System.out.println(m.matches() + "---");
        return m.matches();
    }
    //获取验证码信息,进行计时操作
    private void startCountBack() {
        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                sendcode.setText(countSeconds + "");
                mCountHandler.sendEmptyMessage(0);
            }
        });
    }
}
