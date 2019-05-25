
package com.example.test;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

public class fb_login extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authListener;
    private String userUID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fb_login);
        auth = FirebaseAuth.getInstance();
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(
                    @NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user!=null) {
                    Log.d("onAuthStateChanged", "登入:"+
                            user.getUid());
                    userUID =  user.getUid();
                }else{
                    Log.d("onAuthStateChanged", "已登出");
                }
            }
        };
    }
    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }
    @Override
    protected void onStop() {
        super.onStop();
        auth.removeAuthStateListener(authListener);
    }
    public void login(View v){
        final String email = ((EditText)findViewById(R.id.fbemail))
                .getText().toString();
        final String password = ((EditText)findViewById(R.id.fbpassword))
                .getText().toString();
        Log.d("AUTH", email+"/"+password);
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("onComplete", "onComplete");
                        if (!task.isSuccessful()){
                            Log.d("onComplete", "登入失敗");
                            register(email, password);
                        }
                        else if(task.isSuccessful()){
//                            fb_login.this.finish();
                            new AlertDialog.Builder(fb_login.this)
                                    .setTitle("登入")
                                    .setIcon(R.mipmap.ic_launcher)
                                    .setMessage("成功連接至 Facebook 帳號密碼")
                                    .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(fb_login.this,"創建帳號密碼成功",Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent (fb_login.this, email_login.class);
                                            startActivity(intent);
                                        }
                                    })
                                    .setNegativeButton("取消",null)
                                    .show();
//                            Intent intent = new Intent(fb_login.this, favorite_main_interface.class);
//                            startActivity(intent);
                        }
                    }
                });
    }
    private void register(final String email, final String password) {
        new AlertDialog.Builder(fb_login.this)
                .setTitle("登入問題")
                .setMessage("無此帳號，是否要以此帳號與密碼註冊?")
                .setPositiveButton("註冊", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        createUser(email, password);
                    }
                })
                .setNeutralButton("取消", null)
                .show();
    }
    private void createUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                String message =
                                        task.isComplete() ? "註冊成功" : "註冊失敗";
                                new AlertDialog.Builder(fb_login.this)
                                        .setMessage(message)
                                        .setPositiveButton("OK", null)
                                        .show();
                            }
                        });
    }
}