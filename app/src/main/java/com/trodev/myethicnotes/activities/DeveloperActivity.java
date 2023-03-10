package com.trodev.myethicnotes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.trodev.myethicnotes.R;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class DeveloperActivity extends AppCompatActivity {

    private ImageView facebook, instagram, linkedin, privacy; //github, youtube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);

        // action bar title
        getSupportActionBar().setTitle("Developer Activity");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //init view
        facebook = findViewById(R.id.facebook);
        instagram = findViewById(R.id.instagram);
        linkedin = findViewById(R.id.linkedin);
        privacy = findViewById(R.id.policy);
        //  youtube = findViewById(R.id.youtube);
        //  github = findViewById(R.id.github);

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(getOpenFacebookIntent());
            }
        });

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.instagram.com/trodevit/");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.instagram.com/trodevit/")));
                }
            }
        });


        linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("linkedin://you"));

                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/company/84349418"));

                startActivity(intent);
            }
        });

/*        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //  Intent appIntent = new Intent(Intent.ACTION_VIEW);
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.youtube.com/@trodev"));
                try {
                    startActivity(webIntent);
                } catch (ActivityNotFoundException ex) {
                    startActivity(webIntent);
                }
            }
        });*/


/*        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //  Intent appIntent = new Intent(Intent.ACTION_VIEW);
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://github.com/zobayerdev"));
                try {
                    startActivity(webIntent);
                } catch (ActivityNotFoundException ex) {
                    startActivity(webIntent);
                }
            }
        });*/

        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //  Intent appIntent = new Intent(Intent.ACTION_VIEW);
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.app-privacy-policy.com/live.php?token=FArIUG005G3apHaSpqknIJnug6bK6RtI"));
                try {
                    startActivity(webIntent);
                } catch (ActivityNotFoundException ex) {
                    startActivity(webIntent);
                }
            }
        });

    }

    public Intent getOpenFacebookIntent() {
        try {
            getPackageManager().getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/https://www.facebook.com/trodevit"));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/trodevit"));
        }
    }

}