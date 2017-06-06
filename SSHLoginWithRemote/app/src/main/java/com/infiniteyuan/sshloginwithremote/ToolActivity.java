package com.infiniteyuan.sshloginwithremote;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ToolActivity extends AppCompatActivity {

    private AutoCompleteTextView autoCompleteTextView;
    private EditText oldEdit;
    private EditText passEdit;
    private EditText sureEdit;
    private View mProgressView;
    private View mLoginFormView;
    private Button conBtn;
    private int mID;
    private String mUserName;
    private String mPassword;
    private UpdateTask mAuthTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool);
        mProgressView = findViewById(R.id.login_progress);
        mLoginFormView = findViewById(R.id.login_form);
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.username);
        oldEdit = (EditText) findViewById(R.id.oldpass);
        passEdit = (EditText) findViewById(R.id.password);
        sureEdit = (EditText) findViewById(R.id.surepass);
        conBtn = (Button) findViewById(R.id.confirm);
        readAccout();
        autoCompleteTextView.setText(mUserName);
        autoCompleteTextView.setEnabled(false);
        sureEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
//                    showProgress(true);
//                    mAuthTask = new UpdateTask(mUserName, passEdit.getText().toString());
//                    mAuthTask.execute((Void) null);
//                    return true;
                    attemptLogin();
                }
                return false;
            }
        });
        conBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (oldEdit.getText().toString().equals(mPassword) && passEdit.getText().toString().equals(sureEdit.getText().toString())) {
//                    showProgress(true);
//                    mAuthTask = new UpdateTask(mUserName, passEdit.getText().toString());
//                    mAuthTask.execute((Void) null);
//                }
                attemptLogin();
            }
        });
    }

    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        autoCompleteTextView.setError(null);
        oldEdit.setError(null);
        passEdit.setError(null);
        sureEdit.setError(null);

        // Store values at the time of the login attempt.
        String oldpass = oldEdit.getText().toString();
        String newpass = passEdit.getText().toString();
        String surepass = sureEdit.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if ((TextUtils.isEmpty(oldpass) && !isPasswordValid(oldpass)) || !oldpass.equals(mPassword)) {
            oldEdit.setError(getString(R.string.error_incorrect_password));
            focusView = oldEdit;
            cancel = true;
        }
        if (TextUtils.isEmpty(newpass) && !isPasswordValid(newpass)) {
            passEdit.setError(getString(R.string.error_invalid_password));
            focusView = passEdit;
            cancel = true;
        }
        if ((TextUtils.isEmpty(surepass) && !isPasswordValid(surepass)) || !surepass.equals(newpass)) {
            sureEdit.setError(getString(R.string.error_incorrect_password));
            focusView = sureEdit;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UpdateTask(mUserName, newpass);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isUserNameValid(String username) {
        //TODO: Replace this with your own logic
        return username.length() > 2;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 3;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private void readAccout() {
        SharedPreferences sharedPreferences = getSharedPreferences("Account", MODE_PRIVATE);
        mID = sharedPreferences.getInt("userID", 0);
        mUserName = sharedPreferences.getString("username", "username");
        mPassword = sharedPreferences.getString("password", "password");
    }

    public class UpdateTask extends AsyncTask<Void, Void, Boolean> {

        private int mID;
        private final String mUsername;
        private final String mPassword;

        UpdateTask(String username, String password) {
            mUsername = username;
            mPassword = password;
        }

        protected void saveAccout() {
            SharedPreferences sharedPreferences = getSharedPreferences("Account", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("userID", mID);
            editor.putString("username", mUsername);
            editor.putString("password", mPassword);
            editor.commit();
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            // TODO: update user
            String line = null;
            String JsonString = null;
            try {
                URL loginUrl = new URL(ServerInfo.mServerIpAddress + "LoginProjectWithSSH/updateUser.action?userName=" + mUsername + "&password=" + mPassword);
                URLConnection urlConnection = loginUrl.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                bufferedReader.close();
                JsonString = stringBuilder.toString();
                // Simulate network access.
                Thread.sleep(1);
                JSONArray jsonArray = new JSONArray(JsonString);
                JSONObject jsonObject = (JSONObject) jsonArray.get(0);
                if (jsonObject.getString("message").equals("success")) {
                    Log.i("Debug", "LoginSuccess");
                    mID = jsonObject.getInt("id");
                    saveAccout();
                    return true;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                oldEdit.setError(getString(R.string.error_incorrect_password));
                oldEdit.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}
