package com.digitalmusdi.digitalmaintenance;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.digitalmusdi.digitalmaintenance.advances.Constance;
import com.digitalmusdi.digitalmaintenance.advances.SessionManager;
import com.digitalmusdi.digitalmaintenance.model.LoginModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    MaterialButton btnLogin;
    TextInputLayout txtUsernameLogin,txtPasswordLogin;
    Constance constance = new Constance();
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
            } else {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_login);

        txtUsernameLogin = findViewById(R.id.txtUsernameLogin);
        txtPasswordLogin = findViewById(R.id.txtPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);

        sessionManager = new SessionManager(getApplicationContext());

        if(sessionManager.isLoggedIn()){
            startActivity(new Intent(this,HomeActivity.class));
            finish();
        }
        AndroidNetworking.initialize(this);

        btnLogin.setOnClickListener((view)->{
            String username = txtUsernameLogin.getEditText().getText().toString();
            String password = txtPasswordLogin.getEditText().getText().toString();
            if(!username.equals("") && !password.equals("")){
                LoginProccess(this,username,password);
            }else{
                Toast.makeText(this, "Complete a Form", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void LoginProccess(Activity activity,String username,String password){
        ProgressDialog pdg = new ProgressDialog(activity);
        pdg.setCancelable(false);
        pdg.setMessage("Loading....");
        pdg.show();
        AndroidNetworking.post(constance.getLogin())
                .addBodyParameter("email",username)
                .addBodyParameter("password",password)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                            boolean success = response.getBoolean("success");
                            String message = response.getString("message");
                            String token = response.getString("token");
                            if(success){
                                JSONObject main = response.getJSONObject("data");
                                JSONObject depart = main.getJSONObject("fk_department");
                                JSONObject role = main.getJSONObject("fk_role");
                                LoginModel loginModel = new LoginModel(
                                        main.getString("id"),
                                        depart.getString("id"),
                                        depart.getString("name"),
                                        role.getString("id"),
                                        role.getString("name"),
                                        main.getString("name"),
                                        main.getString("email"),
                                        main.getString("phone"),
                                        token
                                );

                                sessionManager.createLoginSession(loginModel);


                                pdg.dismiss();
                                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(activity,HomeActivity.class));
                                finish();

                            }else{
                                pdg.dismiss();
                                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            pdg.dismiss();
                            e.printStackTrace();
                            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        pdg.dismiss();
                        anError.printStackTrace();
                        Toast.makeText(activity, anError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}