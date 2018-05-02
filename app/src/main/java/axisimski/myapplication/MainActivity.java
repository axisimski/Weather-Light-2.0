package axisimski.myapplication;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import android.content.Context;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import java.util.Arrays;

import java.util.List;
import java.util.Locale;



public class MainActivity extends AppCompatActivity {

   static EditText input;
   static TextView output;
   static ImageView cond;
   static TextView degrees;
   static TextView location;


   static ImageView day1;
   static TextView day1text;
   static TextView day1textDate;

   static ImageView day2;
   static TextView day2text;
   static TextView day2textDate;

   static ImageView day3;
   static TextView day3text;
   static TextView day3textDate;

   static ImageView day4;
   static TextView day4text;
   static TextView day4textDate;

   Switch ctf;
   Button execute;
   private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*AdMob Code*/
        //Banner Ads

        MobileAds.initialize(this,
                "ca-app-pub-8271447368800027~4798101235");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        cond=(ImageView)findViewById(R.id.cond);
        input = (EditText) findViewById(R.id.input);
        output = (TextView) findViewById(R.id.output);
        degrees=(TextView)findViewById(R.id.degrees);
        execute=(Button)findViewById(R.id.execute);
        ctf=(Switch)findViewById(R.id.switch1);

        day1=(ImageView)findViewById(R.id.day1);
        day1text=(TextView)findViewById(R.id.day1text);
        day1textDate=(TextView)findViewById(R.id.day1textDate);

        day2=(ImageView)findViewById(R.id.day2);
        day2text=(TextView)findViewById(R.id.day2text);
        day2textDate=(TextView)findViewById(R.id.day2textDate);

        day3=(ImageView)findViewById(R.id.day3);
        day3text=(TextView)findViewById(R.id.day3text);
        day3textDate=(TextView)findViewById(R.id.day3textDate);

        day4=(ImageView)findViewById(R.id.day4);
        day4text=(TextView)findViewById(R.id.day4text);
        day4textDate=(TextView)findViewById(R.id.day4textDate);

        SharedPreferences SharedPref=getSharedPreferences("userInput", Context.MODE_PRIVATE);
        String savedInput=SharedPref.getString("input"," ");
        Boolean FC=SharedPref.getBoolean("c2fs", false);
        ctf.setChecked(FC);
        input.setText(savedInput);



        ctf.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences SharedPref=getSharedPreferences("userInput", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=SharedPref.edit();
                getData c=new getData();
                getDataF f =new getDataF();
                if(ctf.isChecked()){
                    f.execute();
                    editor.putBoolean("c2fs", true);
                }

                else{
                    editor.putBoolean("c2fs", false);
                    c.execute();
                }
                editor.apply();
            }
        });


        getData c = new getData();
        getDataF f = new getDataF();

        if(ctf.isChecked()){
            f.execute();        }

        else{
            c.execute();
        }
    }



    public void execute(View v){

        SharedPreferences SharedPref=getSharedPreferences("userInput", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=SharedPref.edit();
        editor.putString("input", input.getText().toString());

        getData c = new getData();
        getDataF f = new getDataF();

        if(ctf.isChecked()){
            f.execute();
            editor.putBoolean("c2fs", true);
        }

        else{
            c.execute();
            editor.putBoolean("c2fs", false);
        }
        editor.apply();

    }


}
