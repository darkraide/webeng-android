package com.example.webeng;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObjectList;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

import java.util.Arrays;

public class MainFragment extends Fragment {
	private static final String TAG = "MainFragment";
	private TextView txtName;
	private TextView txtBirthdate;
	private TextView txtGender;
	private TextView txtLocation;
	private TextView txtEmail;
	private TextView txtPassword;
	private TextView txtAdress;
	// private TextView userInfoTextView;

	private UiLifecycleHelper uiHelper;
	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	private interface MyGraphLanguage extends GraphObject {
		// Getter for the ID field
		String getId();

		// Getter for the Name field
		String getName();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.loginfacebook, container, false);
		LoginButton authButton = (LoginButton) view
				.findViewById(R.id.authButton);
		authButton.setFragment(this);
		authButton.setReadPermissions(Arrays.asList("user_about_me",
				"basic_info", "user_location",
				"user_birthday", "user_likes","email"));
		txtName = (TextView) view.findViewById(R.id.txtName);
		txtBirthdate = (TextView) view.findViewById(R.id.txtBirthdate);
		txtGender = (TextView) view.findViewById(R.id.txtGender);

		txtLocation = (TextView) view.findViewById(R.id.txtLocations);
		txtEmail = (TextView) view.findViewById(R.id.txtemail);
		txtPassword = (TextView) view.findViewById(R.id.txtpassword);
		txtAdress = (TextView) view.findViewById(R.id.txtAdress);
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(getActivity(), callback);
		uiHelper.onCreate(savedInstanceState);
	}

	private void onSessionStateChange(Session session, SessionState state,
			Exception exception) {
		if (state.isOpened()) {
			Log.v(TAG,session.getAccessToken());
		
			Request.executeMeRequestAsync(session,
					new Request.GraphUserCallback() {

						@Override
						public void onCompleted(GraphUser user,
								Response response) {
							if (user != null) {
							
								buildUserInfoDisplay(user);
							}
						}
					});
			Log.i(TAG, "Logged in...");
		} else if (state.isClosed()) {
			
			Log.i(TAG, "Logged out...");
		}
	}

	private void buildUserInfoDisplay(GraphUser user) {
	
		txtName.setText(user.getName());
		
		txtGender.setText(user.asMap().get("gender").toString());
		
		Log.v(TAG, user.asMap().toString());
		Log.v(TAG, user.getId());
		txtBirthdate.setText(user.getBirthday());
		
		if(user.getProperty("email").toString() != null){
			txtEmail.setText(user.getProperty("email").toString());
		}
		
		txtAdress.setText(user.getLocation().getProperty("name").toString());

		
		txtLocation.setText(user.getProperty("locale").toString());

		
	}

	private interface MyGraphUser extends GraphUser {
		// Create a setter to enable easy extraction of the languages field
		GraphObjectList<MyGraphLanguage> getLanguages();
	}

	@Override
	public void onResume() {
		super.onResume();
		Session session = Session.getActiveSession();
		if (session != null && (session.isOpened() || session.isClosed())) {
			onSessionStateChange(session, session.getState(), null);
		}
		uiHelper.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}
}
