package com.digitalmusdi.digitalmaintenance;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.digitalmusdi.digitalmaintenance.advances.Constance;
import com.digitalmusdi.digitalmaintenance.advances.SessionManager;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.digitalmusdi.digitalmaintenance.databinding.ActivityHomeBinding;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;
    SessionManager sessionManager;
    Constance constance = new Constance();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessionManager = new SessionManager(this);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarHome.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;


        // Set Navigation
        View headerView = navigationView.getHeaderView(0);
        TextView loginName = headerView.findViewById(R.id.loginName);
        TextView loginDetail = headerView.findViewById(R.id.loginDetail);
        loginName.setText(sessionManager.getUserDetail().get(SessionManager.NAME));
        loginDetail.setText(sessionManager.getUserDetail().get(SessionManager.DEPARTMENT_NAME) +" - "+sessionManager.getUserDetail().get(SessionManager.ROLE_NAME));


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.nav_history)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            new MaterialAlertDialogBuilder(this)
                    .setTitle("Logout")
                    .setMessage("Are you sure to Logout?")
                    .setNegativeButton("No",(dialog,i)->dialog.cancel())
                    .setPositiveButton("Yes",(dialog,i)->LogoutProccess(this)).show();
            return true;
        }else{
            return super.onOptionsItemSelected(item);
        }
    }



    private void LogoutProccess(Activity activity){
        AndroidNetworking.post(constance.getLogout())
                .addHeaders("Authorization",sessionManager.getUserDetail().get(SessionManager.TOKEN))
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            boolean success = response.getBoolean("success");
                            String message = response.getString("message");
                            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                            if(success){
                                sessionManager.logoutSession();
                                Intent i = new Intent(activity, MainActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(i);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        anError.printStackTrace();
                        Toast.makeText(activity, anError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}