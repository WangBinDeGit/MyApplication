package com.example.jxd.myapplication.base;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Administrator on 2018\7\16 0016.
 */

public class BaseActivity extends AppCompatActivity {



    public void toastShort(String str){
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
    }

}
