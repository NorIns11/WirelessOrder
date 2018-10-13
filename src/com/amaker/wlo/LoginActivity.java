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
	//������¼��ȡ����ť
	private Button cancelBtn,loginBtn;
	//�����û��������������
	private EditText userEditText,pwdEditText;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//���ñ���
		setTitle("�׵㱦-�û���¼");
		//���õ�ǰActivity���沼��
		setContentView(R.layout.login_system);
		//ͨ��findViewById����ʵ�������
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
						showDialog("�����û���������������������������!");
					}
				}
			}
		});
		cancelBtn.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View v) {
				//������ǰActivity
				finish();
			}
		});
	}
	
	// ����һ����ʾ��ʾ��Ϣ�ĶԻ���
	public void showDialog (String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(msg).setCancelable(false).setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int id) {
				// TODO Auto-generated method stub			
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	// ���û����ƺ�������зǿ���֤
	private boolean validate () {
		String username = userEditText.getText().toString();
		if(username.equals("")){
			showDialog("�����������û����ƣ�");
			return false;
		}
		String pwd = pwdEditText.getText().toString();
		if(pwd.equals("")){
			showDialog("�������������룡");
			return false;
		}
		return true;
	}
	
	// ͨ���û����ƺ�������в�ѯ������Post���󣬻����Ӧ���
	private String query (String username, String pwd) {
		// ��ѯ�ַ���
		String queryString = "username = " + username + "&password = " + pwd;
		// ��ѯURL
		String url = HttpUtil.Base_URL + "servlet/LoginServlet?" + queryString;
		// ��ѯ�����ؽ��
		return HttpUtil.queryStringForPost(url);
	}
	
	// ���û���Ϣ���浽�����ļ�
	private void saveUserMsg (String msg) {
		// �û����
		String id ="";
		// �û�����
		String name="";
		// �����Ϣ����
		String[] msgs = msg.split(";");
		// "="������λ��
		int index = msgs[0].indexOf("=");
		id = msgs[0].substring(index + 1);
		index = msgs[1].indexOf("=");
		name = msgs[1].substring(index + 1);
		// ������Ϣ  
		//SharedPreferences��Androidƽ̨��һ���������Ĵ洢�࣬��������Ӧ�õ�һЩ�������ã�����Activity״̬��Activity��ͣʱ������activity��״̬���浽SharedPereferences�У�
		//��Activity���أ�ϵͳ�ص�����onSaveInstanceStateʱ���ٴ�SharedPreferences�н�ֵȡ����
		//user_msgΪ�ļ�����MODE_WORLD_WRITEABLE����ʾ��ǰ�ļ����Ա�����Ӧ��д��
		SharedPreferences pre = getSharedPreferences("user_msg",MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = pre.edit();
		// ִ��д��������ύ
		editor.putString("id", id);
		editor.putString("name", name);
		editor.commit();
	}
	
	// ��¼����	
	private boolean login () {
		String username = userEditText.getText().toString();
		String pwd = pwdEditText.getText().toString();
		String result = query(username,pwd);
		if (result != null && result.equals("0")) {
			return false;
		} else {
			saveUserMsg(result);			// �����Զ��巽�������û���Ϣ���浽�����ļ�
			return true;
		}
	}
}
