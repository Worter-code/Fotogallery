package com.example.fotogallary;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;


public class FullScreen extends FragmentActivity {

    ViewPager pager;
    PagerAdapter pagerAdapter;


    ArrayList<String> fileList = new ArrayList<>();

    public boolean visible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        fileList = getIntent().getStringArrayListExtra("data");
        pager = (ViewPager) findViewById(R.id.pager);

        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());

        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem((int) getIntent().getSerializableExtra("position"));


        //////////////СЛУШАТЕЛИ ДЛЯ КНОПОК В ТУЛБАРЕ///////////////////

        //удаление изображения
        final ImageButton deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder deleteDialog = new AlertDialog.Builder(FullScreen
                        .this);
                deleteDialog.setTitle("Delete");
                deleteDialog.setMessage("Do you really want to delete it?");
                deleteDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new File(fileList.get((int) getIntent().getSerializableExtra(
                                "position"))).delete();
                        fileList.remove((int) getIntent().getSerializableExtra("position"));
                    }
                });
                deleteDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                deleteDialog.show();
            }
        });

        //переслать изображение
        final ImageButton shareButton = findViewById(R.id.shareButton);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String filePath = fileList.get((int) getIntent().getSerializableExtra(
                        "position"));
                final Uri imageUri = Uri.parse("file://" + filePath);
                final Intent intent = new Intent(Intent.ACTION_SEND);
                if (filePath.endsWith(".png"))
                    intent.setType("image/png");
                else
                    intent.setType("image/jpeg");
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.putExtra(Intent.EXTRA_STREAM, imageUri);
                startActivity(Intent.createChooser(intent, "Share"));
            }
        });
        //показать информацию
        final ImageButton infoButton = findViewById(R.id.infoButton);
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder showInfoDialog = new AlertDialog.Builder(FullScreen
                        .this);
                showInfoDialog.setTitle("Image Info");
                final File imageFile = new File(fileList.get((int) getIntent()
                        .getSerializableExtra("position")));
                final Date lastModDate = new Date(imageFile.lastModified());
                final long fileLenght = imageFile.length()/1024;
                String fileLenghtString;
                if (fileLenght > 1024)
                    fileLenghtString = String.valueOf(fileLenght/1024) + " MB";
                else
                    fileLenghtString = String.valueOf(fileLenght) + " KB";
                String info = "Location: " + fileList.get((int) getIntent().getSerializableExtra(
                        "position")) + "\n\n" +
                        "Last Modified: " + lastModDate.toString() + "\n\n" +
                        "Image Size: " + fileLenghtString;
                        ;
                showInfoDialog.setMessage(info);
                showInfoDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                showInfoDialog.show();
            }
        });


    }
    ///////////////////////////////////////////////////////////

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter{
        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fileList.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return new PageFragment(fileList.get(position));
        }

        @Override
        public int getCount() {
            return fileList.size();
        }
    }


}
