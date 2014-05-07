package fragments;

import java.util.Arrays;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.kacobdemos.facebooklogin.DisplayActivity;
import com.kacobdemos.facebooklogin.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

@SuppressLint("NewApi")
public class FacebookSigninFragment extends Fragment {
	private static final String TAG = "FacebookLogin";
	Context mContext;
	LoginButton mLoginBtn;

	//session status callback variable
	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};
	
	//manages ui flow
	private UiLifecycleHelper uiHelper;
	
	@Override
	public View onCreateView(LayoutInflater inflater, 
	        ViewGroup container, 
	        Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.fragment_facebook_login, container, false);
	    
	    mContext = getActivity();
	    
	    
	    mLoginBtn = (LoginButton) view.findViewById(R.id.authButton);
	    mLoginBtn.setFragment(this);
	    mLoginBtn.setReadPermissions(Arrays.asList("public_profile","email"));
	    
	    
	    
	    return view;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    uiHelper = new UiLifecycleHelper(getActivity(), callback);
	    uiHelper.onCreate(savedInstanceState);
	}
	
	@Override
	public void onResume() {
	    super.onResume();
	    Session session = Session.getActiveSession();
	    if (session != null &&
	           (session.isOpened() || session.isClosed()) ) {
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
//	    fini
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
	
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
	    if (state.isOpened()) {
	        Log.i(TAG, "Logged in...");
	        mLoginBtn.setText("Logging you in...");
	        makeMeRequest(session);
	    } else if (state.isClosed()) {
	        Log.i(TAG, "Logged out...");
	        Log.i(TAG, "Facebook Exception " + exception);
	    }
	}
	
	private void makeMeRequest(final Session session) {
	    // Make an API call to get user data and define a 
	    // new callback to handle the response.
	    Request request = Request.newMeRequest(session, 
	            new Request.GraphUserCallback() {
	        @Override
	        public void onCompleted(GraphUser user, Response response) {
	            // If the response is successful
	            if (session == Session.getActiveSession()) {
	                if (user != null) {
	                	
	                    String fullName = user.getName();
	                    String firstName = user.getFirstName();
	                    String lastName = user.getLastName();
	                    String fbId = user.getId();
	                    String username = user.getUsername();
	                    String email = (String) user.asMap().get("email");
	                    
	                    /*
	                     * At this point you can do whatever you want with the data that you get
	                     * Maybe sign up or sign in the user
	                     * In this case I'll just start a new activity and display some of that data
	                     */
	                    Intent intent = new Intent(mContext, DisplayActivity.class);
	                    intent.putExtra("username", username);
	                    intent.putExtra("full_name", fullName);
	                    intent.putExtra("fbId", fbId);
	                    
	                    startActivity(intent);
	                }
	            }
	            if (response.getError() != null) {
	                // Handle errors, will do so later.
	            }
	        }
	    });
	    
	    request.executeAsync();
	} 
	
}
