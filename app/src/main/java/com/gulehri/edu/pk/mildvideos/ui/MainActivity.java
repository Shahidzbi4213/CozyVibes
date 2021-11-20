package com.gulehri.edu.pk.mildvideos.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;

import com.google.android.material.tabs.TabLayout;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.gulehri.edu.pk.mildvideos.R;
import com.gulehri.edu.pk.mildvideos.adapter.VAdapter;
import com.gulehri.edu.pk.mildvideos.databinding.ActivityMainBinding;
import com.gulehri.edu.pk.mildvideos.databinding.EmailDailogBinding;
import com.gulehri.edu.pk.mildvideos.utils.DataSaver;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private ActivityMainBinding binding;
    private VAdapter adapter;
    private boolean flag;
    private SearchView searchView;
    private DataSaver dataSaver;
    private AlertDialog dialog;
    private final int UPDATE_REQUEST_CODE = 421;
    private AppUpdateManager appUpdateManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = new VAdapter(this);
        binding.vPager.setAdapter(adapter);
        binding.vPager.setUserInputEnabled(false);
        binding.vPager.clearAnimation();
        binding.tabLayout.addOnTabSelectedListener(this);
        setToolBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataSaver = new DataSaver(MainActivity.this);

        if (appUpdateManager != null) {
            appUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
                @Override
                public void onSuccess(AppUpdateInfo appUpdateInfo) {

                    if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {

                        try {
                            appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.IMMEDIATE, MainActivity.this, UPDATE_REQUEST_CODE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

    }

    private void setToolBar() {
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        final int pos = tab.getPosition();
        binding.vPager.setCurrentItem(pos);

        if ((pos == 0) || (pos == 1)) {
            adapter.notifyDataSetChanged();
        }
        if (pos == 1) {
            searchView.setVisibility(View.GONE);
            binding.textView.setVisibility(View.VISIBLE);
        } else {
            searchView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

        if (tab.getPosition() == 0) {
            if (!searchView.isIconified()) {
                searchView.onActionViewCollapsed();
                searchView.setIconified(true);
                binding.textView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        final int ID = item.getItemId();
        if (ID == R.id.nav_more) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/dev?id=5079268634401972877")));
        } else if (ID == R.id.feedBack) {
            openDialog();
        } else if (ID == R.id.nav_update) {
            checkUpdates();
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkUpdates() {
        appUpdateManager = AppUpdateManagerFactory.create(this);

        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(appUpdateInfo -> {

            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {

                try {
                    appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.IMMEDIATE, MainActivity.this, UPDATE_REQUEST_CODE);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_NOT_AVAILABLE) {
                Toast.makeText(this, "Not Updates Available", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> Log.e("TAG", "onFailure: " + e.getMessage()));

    }

    private void openDialog() {
        final EmailDailogBinding dialogBinding = EmailDailogBinding.inflate(LayoutInflater.from(this));
        dialog = new AlertDialog.Builder(this).create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(this, android.R.color.transparent));
        dialog.setCancelable(false);
        dialog.setView(dialogBinding.getRoot());


        dialogBinding.btnSend.setOnClickListener(v -> {
            String subject = Objects.requireNonNull(dialogBinding.edSubject.getEditText()).getText().toString().trim();
            String message = Objects.requireNonNull(dialogBinding.edMessage.getEditText()).getText().toString();

            if (subject.isEmpty()) {
                dialogBinding.edSubject.getEditText().setError("Enter Subject");
            } else if (message.isEmpty()) {
                dialogBinding.edMessage.getEditText().setError("Enter Message");
            } else {
                sendEmail(subject, message);
            }
        });
        dialogBinding.btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();

    }

    private void sendEmail(@NonNull String subject, String message) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"shahidzbi@outlook.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.setType("email/rfc822");
        startActivity(intent);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();

            }
        }, 2000);
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = MainActivity.this.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(MainActivity.this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @SuppressLint({"NotifyDataSetChanged", "RestrictedApi"})
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (menu instanceof MenuBuilder)
            ((MenuBuilder) menu).setOptionalIconsVisible(true);
        getMenuInflater().inflate(R.menu.search, menu);
        final MenuItem item = menu.findItem(R.id.search);
        searchView = (SearchView) item.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("Search Videos");


        //Changing Search Icon Color
        ImageView searchIcon = searchView.findViewById(androidx.appcompat.R.id.search_button);
        searchIcon.setColorFilter(getResources().getColor(R.color.txtColor), PorterDuff.Mode.SRC_IN);

        //cancel text Color
        ImageView searchCrossIcon = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        searchCrossIcon.setColorFilter(getResources().getColor(R.color.txtColor), PorterDuff.Mode.SRC_IN);


        //Edit Text
        EditText hintColor = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        hintColor.setTextColor(ContextCompat.getColor(this, R.color.white));

        searchView.setOnQueryTextFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                // searchView expanded
                binding.textView.setVisibility(View.GONE);
                Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public boolean onQueryTextSubmit(String query) {
                dataSaver.saveData(query.trim());
                binding.vPager.setAdapter(null);
                binding.vPager.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                hideKeyboard();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnCloseListener(() -> {
            dataSaver.saveData("");
            binding.vPager.setAdapter(null);
            binding.vPager.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            binding.textView.setVisibility(View.VISIBLE);
            searchView.onActionViewCollapsed();
            return true;
        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStop() {
        dataSaver.saveData("");
        super.onStop();
    }


    @Override
    public void onBackPressed() {
        if (flag) {
            dataSaver.saveData("");
            super.onBackPressed();
            return;
        }


        if (!searchView.isIconified()) {
            searchView.onActionViewCollapsed();
            searchView.setIconified(true);
            binding.textView.setVisibility(View.VISIBLE);
        } else {
            this.flag = true;
            Toast.makeText(this, "Click again to exit", Toast.LENGTH_SHORT).show();
            new Handler(Looper.getMainLooper()).postDelayed(() -> flag = false, 2000);
        }

    }
}