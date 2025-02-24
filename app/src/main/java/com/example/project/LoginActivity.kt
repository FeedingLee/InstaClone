package com.example.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    var auth : FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        email_login_button.setOnClickListener{
            signinAndSignup()
        }
    }

    fun signinAndSignup(){
        auth?.createUserWithEmailAndPassword(email_edittext.text.toString(),password_edittext.text.toString())
            ?.addOnCompleteListener {
                    task ->
                if(task.isSuccessful) { // 계정만들기
                    moveMainPage(task.result?.user)
                    Toast.makeText(getApplicationContext(), "계정이 생성되었습니다!", Toast.LENGTH_LONG).show();
                }

                else if(task.exception?.message.isNullOrEmpty()) { // 에러메세지 출력
                    Toast.makeText(getApplicationContext(), "아이디 혹은 비밀번호를 확인해주세요", Toast.LENGTH_LONG).show();
                }

                else { // 아이디가있다면 로그인하기
                    signinEmail()
                }
            }
    }

    fun signinEmail(){
        auth?.signInWithEmailAndPassword(email_edittext.text.toString(),password_edittext.text.toString())
            ?.addOnCompleteListener {
                    task ->
                if(task.isSuccessful){ // 로그인하기
                    moveMainPage(task.result?.user)
                }

                else{ // 에러메세지 출력
                    Toast.makeText(getApplicationContext(), "아이디 혹은 비밀번호를 확인해주세요", Toast.LENGTH_LONG).show();
                }
            }
    }

    fun moveMainPage(user:FirebaseUser?){
        if(user != null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }
}
