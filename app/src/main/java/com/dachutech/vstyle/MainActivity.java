package com.dachutech.vstyle;

import com.google.android.gms.vision.face.FaceDetector;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper dbAccessor;

    ImageButton myImageButton;
    ImageButton myImageButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FaceDetector faceDetector = new
                FaceDetector.Builder(getApplicationContext()).setTrackingEnabled(false)
                .build();
        if(!faceDetector.isOperational()){
            new AlertDialog.Builder(this).setMessage("Could not set up the face detector!").show();
            return;
        }


        ImageButton myImageButton = (ImageButton) findViewById(R.id.cameraButton);
        ImageButton myImageButton1 = (ImageButton) findViewById(R.id.galleryButton);
        ImageButton myImageButton2 = (ImageButton) findViewById(R.id.profileButton);
        ImageButton myImageButton3 = (ImageButton) findViewById(R.id.helpButton);
        ImageButton myImageButton4 = (ImageButton) findViewById(R.id.homeButton);


        myImageButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent (MainActivity.this, DesignActivity2.class);
                startActivity(intent);

            }
        } );

        myImageButton2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent (MainActivity.this, ProfileActivity.class);
                startActivity(intent);

            }
        } );

        myImageButton3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent (MainActivity.this, HelpActivity.class);
                startActivity(intent);

            }
        } );

        myImageButton4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent (MainActivity.this, MainActivity.class);
                startActivity(intent);

            }
        } );

        myImageButton1.setOnClickListener((new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent1= new Intent (MainActivity.this, GalleryActivity.class);
                startActivity(intent1);
                //To change body of implemented methods use File | Settings | File Templates.
            }
        }));
       /* /*//***********************************************
        //Start HookUp
        dbAccessor = new DatabaseHelper(this);

        dbAccessor.setActivePackageName("ng.edu.baze.bazeurocks.dbstatuscheckercom313");
        //dbAccessor.setCurrentDatabaseName("theDuchess");
        dbAccessor.setCurrentDatabaseName("vstyle.db");
        //if exported fileName not set, current database name in assert
        //folder is used; else exported file name in assert folder is used.
        dbAccessor.setExportedFileName("myface.sql");     //provide exported DDL
        dbAccessor.setCloneFromExport(false); //Set to true
        //This next call will create and open database;
        //Connection and DB object is stored in static variable
        //for subsequent use from anywhere in the app via the
        //dbAccessor.(methods)
        dbAccessor.overridePackageNameAndDatabaseName();
        try {
            dbAccessor.createDataBase();
            String userMessage = "Rah, you have sucessfully integrated SQLite";
            Toast myToast = Toast.makeText(MainActivity.this, "Congratulations" + userMessage, Toast.LENGTH_SHORT);
            myToast.show();
        } catch (IOException e) {
            e.printStackTrace();
            //enforce hard stop:
            *//* below is c-sharp hard stop code, need to code Java
            builder.SetTitle("Error");
            builder.SetMessage("Application suddenly stopped working. do you want to cancel this program?");
            builder.SetPositiveButton("ok", (s, e) => {
            this.Finish(); });
            builder.SetNegativeButton("no keep working", (s, e) => { });
            builder.Create().Show();
            *//*
        }
        //end database hook up
        /*//***************************************/





    }





}
