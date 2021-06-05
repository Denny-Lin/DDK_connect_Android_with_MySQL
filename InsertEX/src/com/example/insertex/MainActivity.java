package com.example.insertex;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import android.os.StrictMode;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	String name;
	String id;
	InputStream is=null;
	String result=null;
	String line=null;
	int code;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final EditText e_id=(EditText) findViewById(R.id.editText1);
        final EditText e_name=(EditText) findViewById(R.id.editText2);
        Button insert=(Button) findViewById(R.id.button1);
        //TextView textView1 = (TextView)findViewById(R.id.textView1);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
        .detectDiskReads()
        .detectDiskWrites()
        .detectNetwork()   // or .detectAll() for all detectable problems
        .penaltyLog()
        .build());
        
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
        .detectLeakedSqlLiteObjects()
        .penaltyLog()
        .penaltyDeath()
        .build());
	
	 insert.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					
				id = e_id.getText().toString();
				name = e_name.getText().toString();
				
				insert();
			}
	 });
}
public void insert()
{
	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

	nameValuePairs.add(new BasicNameValuePair("id",id));
	nameValuePairs.add(new BasicNameValuePair("name",name));
	
	try
	{
	HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://10.0.2.2/AndroidConnectDB/insert.php");
        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));//發出伺服器請求
        HttpResponse response = httpclient.execute(httppost); //取得HTTP RESPONSE
        HttpEntity entity = response.getEntity();//取得資料實體
        is = entity.getContent();
        Log.e("pass 1", "connection success ");
}
    catch(Exception e)
{
    	Log.e("Fail 1", e.toString());//將錯誤碼e轉成字串
    	Toast.makeText(getApplicationContext(), "Invalid IP Address",
		Toast.LENGTH_LONG).show();
}     
    
    try
    {
        BufferedReader reader = new BufferedReader
        (new InputStreamReader(is,"iso-8859-1"),8);//利用bufferedreader取得字串
        StringBuilder sb = new StringBuilder();//利用stringbulider轉成字串
        while ((line = reader.readLine()) != null)//readLine()方法會傳回使用者在按下Enter鍵之前的所有字元輸 入
    {
            sb.append(line + "\n");
        }
        is.close();
        result = sb.toString();
    Log.e("pass 2", "connection success ");
}
    catch(Exception e)
{
        Log.e("Fail 2", e.toString());
}     
   
try
{
        JSONObject json_data = new JSONObject(result);
        code=(json_data.getInt("code"));
		
        if(code==1)
        {
        	TextView textView1 = (TextView)findViewById(R.id.textView1);
        	textView1.setText("Inserted Successfully");
        	Toast.makeText(getBaseContext(), "Inserted Successfully",
        	Toast.LENGTH_SHORT).show();
        }
        else
        {
	 Toast.makeText(getBaseContext(), "Sorry, Try Again",
		Toast.LENGTH_LONG).show();
        }
}
catch(Exception e)
{
        Log.e("Fail 3", e.toString());
}
}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}