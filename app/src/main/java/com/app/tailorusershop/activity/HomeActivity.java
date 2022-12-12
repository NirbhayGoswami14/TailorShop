
package com.app.tailorusershop.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.tailorusershop.R;
import com.app.tailorusershop.databinding.ActivityHomeBinding;
import com.app.tailorusershop.databinding.DeleteDialogLayoutBinding;
import com.app.tailorusershop.databinding.UserDialogLayoutBinding;
import com.app.tailorusershop.fragment.FragmentGallery;
import com.app.tailorusershop.fragment.FragmentOrderList;
import com.app.tailorusershop.utlis.PrefManager;
import com.google.android.datatransport.backend.cct.BuildConfig;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import io.getstream.avatarview.AvatarView;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Context context = HomeActivity.this;
    private View headerView;
    private boolean isOrderFragOpen = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Home");
        /* getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, binding.navDrawer, R.string.Open, R.string.Close);
        binding.navDrawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        headerView = binding.sideNav.getHeaderView(0);
        AvatarView avatarView = headerView.findViewById(R.id.img1);
        TextView textView=headerView.findViewById(R.id.txt_header_user_name);

        if(PrefManager.getInstance(context).getUserDetails(PrefManager.USER_NM).equals(""))
        {
                textView.setText(PrefManager.getInstance(context).getUserDetails(PrefManager.USER_MOBILE));
                avatarView.setAvatarInitials("T");
        }
        else
        {
            textView.setText(PrefManager.getInstance(context).getUserDetails(PrefManager.USER_NM));
            String avtarS= String.valueOf(PrefManager.getInstance(context).getUserDetails(PrefManager.USER_NM).charAt(0));
            avatarView.setAvatarInitials(avtarS);
        }

        /*binding.linearProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(context,ProfileActivity.class));
                binding.navDrawer.close();
            }
        });

        binding.linearReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, AddReviewActivity.class));
                binding.navDrawer.close();
            }
        });

        binding.linearShareApp.setOnClickListener(view -> {
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                String shareMessage = "\n"+getString(R.string.app_name)+"\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                //shareMessage = shareMessage + "VGreen Under Development Stay Tuned\t\t" + BuildConfig.APPLICATION_ID +"\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
                binding.navDrawer.close();
            } catch (Exception e) {
                e.toString();
            }
        });

        binding.linearLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               *//* PrefManager.getInstance(context).clearPref();
                PrefManager.getInstance(context).setIsLogin(false);
                startActivity(new Intent(context,LoginActivity.class));
                finish();*//*
            }
        });
*/

        loadFragment(new FragmentOrderList());

        binding.homeBottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                   /* case R.id.location:
                        startActivity(new Intent(context,LocationActivity.class));
                        break;*/

                    case R.id.orders:
                        if (!isOrderFragOpen) {
                            loadFragment(new FragmentOrderList());
                            isOrderFragOpen = true;
                        }
                        break;

                    case R.id.gallery:
                        isOrderFragOpen = false;
                        loadFragment(new FragmentGallery());
                        break;

                }
                return true;
            }
        });
        binding.sideNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.user_profile:
                        startActivity(new Intent(context, ProfileActivity.class));
                        binding.navDrawer.close();
                        break;
                    case R.id.user_orders:
                        if (!isOrderFragOpen) {
                            loadFragment(new FragmentOrderList());
                            isOrderFragOpen = true;
                        }
                        binding.navDrawer.close();
                        break;
                    case R.id.contact_us:
                        contactUsDialog();
                        binding.navDrawer.close();
                        break;
                    case R.id.share_app:
                        try {
                            Intent shareIntent = new Intent(Intent.ACTION_SEND);
                            shareIntent.setType("text/plain");
                            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                            String shareMessage = "\n" + getString(R.string.app_name) + "\n\n";
                            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                            //shareMessage = shareMessage + "VGreen Under Development Stay Tuned\t\t" + BuildConfig.APPLICATION_ID +"\n\n";
                            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                            startActivity(Intent.createChooser(shareIntent, "choose one"));
                            binding.navDrawer.close();
                        } catch (Exception e) {
                            e.toString();
                        }
                        break;
                   /* case R.id.review:
                        startActivity(new Intent(context, AddReviewActivity.class));
                        binding.navDrawer.close();
                        break;
                   */ case R.id.faq:
                        break;
                    case R.id.logout:
                        logoutDialog();
                        break;
                }
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        if (item.getItemId() == R.id.add) {
            startActivity(new Intent(context,CategoriesActivity.class));
            //Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();
        }
        /*else if(item.getItemId()==android.R.id.home)
        {
            binding.navDrawer.open();
        }*/
        return super.onOptionsItemSelected(item);
    }

    private void contactUsDialog() {
        UserDialogLayoutBinding binding = UserDialogLayoutBinding.inflate(getLayoutInflater());
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setCancelable(false);
        dialog.setView(binding.getRoot());
        dialog.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        binding.txtTitle.setText("Contact Us");
        binding.txtMobileNo.setText("+917827466910");
        binding.txtMobileNo.setOnClickListener(view ->{
            startActivity(new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:7827466910")));});

        binding.imgWp.setOnClickListener(view -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://api.whatsapp.com/send?phone=+917827466910"));
            context.startActivity(i);
        });
        binding.txtEmail.setText("testemail@gmail.com");
        try {
            binding.txtEmail.setOnClickListener(view -> {
                Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse("mailto:testemail@gmail.com"));
                startActivity(intent);
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        dialog.create();
        dialog.show();

    }

    void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame1, fragment).commit();
    }


    private void logoutDialog() {
        DeleteDialogLayoutBinding binding = DeleteDialogLayoutBinding.inflate(LayoutInflater.from(context));
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setCancelable(false);
        dialog.setView(binding.getRoot());
        binding.txtTitle.setText("Logout");
        binding.imgDialogLogo.setImageResource(R.drawable.ic_baseline_logout_24);
        binding.txtDelMsg.setText("Are you sure you want to Logout ?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                PrefManager.getInstance(context).clearPref();
                PrefManager.getInstance(context).setIsLogin(false);
                startActivity(new Intent(context, LoginActivity.class));
                finish();
                dialogInterface.dismiss();
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.create();
        dialog.show();
    }

}