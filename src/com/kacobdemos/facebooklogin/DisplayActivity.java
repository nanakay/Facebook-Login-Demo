package com.kacobdemos.facebooklogin;

import com.koushikdutta.ion.Ion;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display);
		
		Intent intent = getIntent();
		
		String fullName = "";
		String username = "";
		String fbId = "";
		
//		Getting the passed data from intent
		if (intent != null) {
			fullName  = intent.getStringExtra("full_name");
			username = intent.getStringExtra("username");
			fbId = intent.getStringExtra("fbId");
		}
		
		TextView fullNameView = (TextView) findViewById(R.id.full_name);
		TextView usernameView = (TextView) findViewById(R.id.username);
		ImageView pictureView = (ImageView) findViewById(R.id.picture);
		
		fullNameView.setText(fullName);
		usernameView.setText(username);
		
		Ion.with(pictureView)
		.placeholder(R.drawable.ic_launcher)
		.error(R.drawable.ic_launcher)
		.load("http://graph.facebook.com/" + fbId + "/picture?type=large");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display, menu);
		return true;
	}

}
