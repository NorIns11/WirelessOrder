package com.amaker.wlo;

import com.amaker.util.HttpUtil;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity{
	//声明登录、取消按钮
	private Button cancelBtn,loginBtn;
	//声明用户名、密码输入框
	private EditText userEditText,pwdEditText;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//设置标题
		setTitle("易点宝-用户登录");
		//设置当前Activity界面布局
		setContentView(R.layout.login_system);
		//通过findViewById方法实例化组件
		cancelBtn = (Button)findViewById(R.id.cancelButton);
		loginBtn = (Button)findViewById(R.id.loginButton);
		userEditText = (EditText)findViewById(R.id.userEditText);
		pwdEditText = (EditText)findViewById(R.id.pwdEditText);
		loginBtn.setOnClickListener(new View.OnClickListener () {
			@Override
			public void onClick (View v) {
				if (validate()){
					if (login()) {
						Intent intent = new Intent(LoginActivity.this,MainMenuActivity.class);
						startActivity(intent);
					} else {
						showDialog("您的用户名或密码输入有误，请重新输入!");
					}
				}
			}
		});
		cancelBtn.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View v) {
				//结束当前Activity
				finish();
			}
		});
	}
	
	// 定义一个显示提示信息的对话框
	public void showDialog (String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(msg).setCancelable(false).setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int id) {
				// TODO Auto-generated method stub			
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	// 对用户名称和密码进行非空验证
	private boolean validate () {
		String username = userEditText.getText().toString();
		if(username.equals("")){
			showDialog("请填入您的用户名称！");
			return false;
		}
		String pwd = pwdEditText.getText().toString();
		if(pwd.equals("")){
			showDialog("请填入您的密码！");
			return false;
		}
		return true;
	}
	
	// 通过用户名称和密码进行查询，发送Post请求，获得相应结果
	private String query (String username, String pwd) {
		// 查询字符串
		String queryString = "username = " + username + "&password = " + pwd;
		// 查询URL
		String url = HttpUtil.Base_URL + "servlet/LoginServlet?" + queryString;
		// 查询并返回结果
		return HttpUtil.queryStringForPost(url);
	}
	
	// 将用户信息保存到配置文件
	private void saveUserMsg (String msg) {
		// 用户编号
		String id ="";
		// 用户名称
		String name="";
		// 获得信息数组
		String[] msgs = msg.split(";");
		// "="的索引位置
		int index = msgs[0].indexOf("=");
		id = msgs[0].substring(index + 1);
		index = msgs[1].indexOf("=");
		name = msgs[1].substring(index + 1);
		// 共享信息  
		//SharedPreferences是Android平台上一个轻量级的存储类，用来保存应用的一些常用配置，比如Activity状态，Activity暂停时，将此activity的状态保存到SharedPereferences中；
		//当Activity重载，系统回调方法onSaveInstanceState时，再从SharedPreferences中将值取出。
		//user_msg为文件名，MODE_WORLD_WRITEABLE：表示当前文件可以被其他应用写入
		SharedPreferences pre = getSharedPreferences("user_msg",MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = pre.edit();
		// 执行写入操作并提交
		editor.putString("id", id);
		editor.putString("name", name);
		editor.commit();
	}
	
	// 登录方法	
	private boolean login () {
		String username = userEditText.getText().toString();
		String pwd = pwdEditText.getText().toString();
		String result = query(username,pwd);
		if (result != null && result.equals("0")) {
			return false;
		} else {
			saveUserMsg(result);			// 调用自定义方法，将用户信息保存到配置文件
			return true;
		}
	}
}
