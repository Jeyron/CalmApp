package com.example.jeiro.calmapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

public class Visualizar extends AppCompatActivity {

    ImageView GalleryPreviewImg;
    String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        path = intent.getStringExtra("path");
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        set_content();

    }

    private void set_content()
    {
        setContentView(R.layout.activity_visualizar);
        GalleryPreviewImg = (ImageView) findViewById(R.id.preview_images);
        Glide.with(Visualizar.this)
                .load(new File(path)) // Uri of the picture
                .into(GalleryPreviewImg);
    }
}
