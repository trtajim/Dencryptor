package com.tajim.encryptor;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    BottomNavigationView bNav;
    Toolbar toolbar;
    RelativeLayout main;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);



        bNav = findViewById(R.id.bNav);
        main = findViewById(R.id.main);
        toolbar = findViewById(R.id.toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                showCustomAlert();
                return true;
            }
        });
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_main, new TextFragment());
        fragmentTransaction.commit();
        bNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemid = item.getItemId();
                if (itemid == R.id.txt_banv){

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_main, new TextFragment());
                    fragmentTransaction.commit();

                }else  if (itemid == R.id.img_banv){

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_main, new ImageFragment());
                    fragmentTransaction.commit();

                }else{}
                return true;
            }
        });



    }

    private void showCustomAlert() {
        View alertLayout = getLayoutInflater().inflate(R.layout.alert_custom, null);
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(alertLayout)
                .setCancelable(true)
                .create();

        int widthInPixels = (int) (340 * getResources().getDisplayMetrics().density);
        alertDialog.show();

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(alertDialog.getWindow().getAttributes());
        layoutParams.width = widthInPixels;
        alertDialog.getWindow().setAttributes(layoutParams);

        TextView allowButton = alertLayout.findViewById(R.id.allow_cus);
        TextView declineButton = alertLayout.findViewById(R.id.decline_cus);


        declineButton.setOnClickListener(v -> {

            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.github.com/trtajim/"));
            startActivity(myIntent);



            alertDialog.dismiss();

        });

        allowButton.setOnClickListener(v -> {


            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.trtajim.xyz/"));
            startActivity(myIntent);



            alertDialog.dismiss();


        });
    }



}