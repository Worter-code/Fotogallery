package com.example.fotogallary;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

   GridView gridView;
    public List<String> fileList;
    public boolean isGallaryInitialazed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_main);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && arePermissionsDenied()){
            requestPermissions(PERMISSIONS, REQUEST_PERMISSIONS);
            return;
        }
        if (!isGallaryInitialazed){
            fileList = new ArrayList<>();
            addImagesFrom(String.valueOf(Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)));
            addImagesFrom(String.valueOf(Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)));
            addImagesFrom(String.valueOf(Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)));





            GalleryAdapter mAdapter = new GalleryAdapter(this);
            mAdapter.setData(fileList);

            gridView = (GridView) findViewById(R.id.grid_view);
            gridView.setAdapter(mAdapter);

            isGallaryInitialazed = true;
        }

    }

    private static final int REQUEST_PERMISSIONS = 1234;

    private void addImagesFrom(String dirPath) {
        final File imageDir = new File(dirPath);
        if (!imageDir.exists())
            imageDir.mkdir();
        final File[] files = imageDir.listFiles();

        for (File file : files) {
            final String path = file.getAbsolutePath();
            if (path.endsWith(".jpg") || path.endsWith(".jpg") || path.endsWith(".jpg") || path.endsWith(".mp4"))
                fileList.add(path);
        }
    }

    private static final String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static final int PERMISSION_COUNT = 2;

    @SuppressLint("NewApi")
    private boolean arePermissionsDenied(){
        for(int i = 0; i < PERMISSION_COUNT; i++){
            if(checkSelfPermission(PERMISSIONS[i]) != PackageManager.PERMISSION_GRANTED){
                return true;
            }
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, final String[] permissions,
                                            final int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_PERMISSIONS && grantResults.length >0){
            if(arePermissionsDenied()){
                ((ActivityManager) Objects.requireNonNull(this.getSystemService(ACTIVITY_SERVICE)))
                        .clearApplicationUserData();
                recreate();
            }
            else {
                onResume();
            }
        }
    }


    @Override
    protected void onResume(){

        super.onResume();

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("position", gridView.getFirstVisiblePosition());
        super.onSaveInstanceState(savedInstanceState);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        gridView.setSelection(savedInstanceState.getInt("position"));
    }
}
