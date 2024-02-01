package com.example.myapplication;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityMainBinding;
import com.ibm.cloud.eventnotifications.destination.android.ENPush;
import com.ibm.cloud.eventnotifications.destination.android.ENPushException;
import com.ibm.cloud.eventnotifications.destination.android.ENPushNotificationListener;
import com.ibm.cloud.eventnotifications.destination.android.ENPushResponseListener;
import com.ibm.cloud.eventnotifications.destination.android.ENSimplePushNotification;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        String instanceGUID = "c9d3ad63-ed7f-49ef-91e8-d86bacacca92";
        String destinationID = "2895253d-f8bd-4245-893d-687fe4f785fa";
        String apiKey = "0a6c1zI0i9Vi_gKWgZCJnD0yxjCDeFbyr99XywE6stWe";

        ENPush enPush = ENPush.getInstance();
        enPush.setCloudRegion(ENPush.REGION_SYDNEY); // Set your region

        enPush.initialize(getApplicationContext(),instanceGUID,destinationID, apiKey);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab)
                        .setAction("Action", null).show();
            }
        });

        enPush.registerDeviceWithUserId("federicogrima13@gmail.com",new ENPushResponseListener<String>() {

            @Override
            public void onSuccess(String deviceId) {
                //handle successful device registration here
            }

            @Override
            public void onFailure(ENPushException ex) {
                //handle failure in device registration here
            }
        });

        ENPushNotificationListener notificationListener = new ENPushNotificationListener() {

            @Override
            public void onReceive (final ENSimplePushNotification message){
                // Handle Push Notification
                System.out.println("Notification received succesfully: "+ message.getPayload());
            }
        };

        if(enPush != null) {
            enPush.listen(notificationListener);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}