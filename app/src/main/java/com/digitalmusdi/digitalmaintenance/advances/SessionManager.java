package com.digitalmusdi.digitalmaintenance.advances;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;
import com.digitalmusdi.digitalmaintenance.model.LoginModel;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import static android.preference.PreferenceManager.*;

public class SessionManager {
    public static final String IS_LOGGED_IN = "IS_LOGGED_IN";
    public static final String ID_USER = "ID_USER";
    public static final String DEPARTMENT_ID = "DEPARTMENT_ID";
    public static final String DEPARTMENT_NAME = "DEPARTMENT_NAME";
    public static final String ROLE_ID = "ROLE_ID";
    public static final String ROLE_NAME = "ROLE_NAME";
    public static final String NAME = "NAME";
    public static final String EMAIL = "EMAIL";
    public static final String PHONE = "PHONE";
    public static final String TOKEN = "TOKEN";

    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    public void createLoginSession(@NotNull LoginModel loginModel){
        editor.putBoolean(IS_LOGGED_IN,true);
        editor.putString(ID_USER, loginModel.getId_user());
        editor.putString(DEPARTMENT_ID, loginModel.getDepartmentId());
        editor.putString(DEPARTMENT_NAME, loginModel.getDepartmentName());
        editor.putString(ROLE_ID, loginModel.getRoleId());
        editor.putString(ROLE_NAME, loginModel.getRoleName());
        editor.putString(NAME, loginModel.getName());
        editor.putString(EMAIL, loginModel.getEmail());
        editor.putString(PHONE,loginModel.getPhone());
        editor.putString(TOKEN, loginModel.getToken());
        editor.apply();
    }

    public HashMap<String,String> getUserDetail(){
        HashMap<String,String> user = new HashMap<>();
        user.put(ID_USER,sharedPreferences.getString(ID_USER,null));
        user.put(DEPARTMENT_ID,sharedPreferences.getString(DEPARTMENT_ID,null));
        user.put(DEPARTMENT_NAME,sharedPreferences.getString(DEPARTMENT_NAME,null));
        user.put(ROLE_ID,sharedPreferences.getString(ROLE_ID,null));
        user.put(ROLE_NAME,sharedPreferences.getString(ROLE_NAME,null));
        user.put(NAME,sharedPreferences.getString(NAME,null));
        user.put(EMAIL,sharedPreferences.getString(EMAIL,null));
        user.put(PHONE,sharedPreferences.getString(PHONE,null));
        user.put(TOKEN,sharedPreferences.getString(TOKEN,null));
        return user;
    }


    public void logoutSession(){
        editor.clear();
        editor.apply();
    }

    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(IS_LOGGED_IN,false);
    }

}
