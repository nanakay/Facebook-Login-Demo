package com.kacobdemos.facebooklogin;

import java.util.Arrays;

import com.facebook.widget.LoginButton;

import fragments.FacebookSigninFragment;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

public class MainActivity extends FragmentActivity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if (savedInstanceState == null) {
			getSupportFragmentManager()
			.beginTransaction()
			.add(android.R.id.content, new FacebookSigninFragment())
			.commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
