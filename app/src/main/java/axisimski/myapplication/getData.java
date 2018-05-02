package axisimski.myapplication; /**
 * Created by Alex on 2/5/2018.
 */


import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class getData extends AsyncTask<Void, Void, Void>{

    //Today info
    String data, currentConditions, todayImgURL, currTemp, location, USA, textCondition;
    int sizeofTemp;
    int isEmpty=0;

    //DAY info
    String day1, day1d, day1ImgURL;
    String day2, day2d, day2ImgURL;
    String day3, day3d, day3ImgURL;
    String day4, day4d, day4ImgURL;


    public getData() {
        currentConditions = " ";
        data = "";
    }


    @Override
    protected Void doInBackground(Void... voids)  {

        String temploc = MainActivity.input.getText().toString();
        String placeName = temploc.replaceAll("\\s+","_");

        if(placeName.isEmpty()){
            isEmpty=1;
        }

        StringBuilder city=new StringBuilder();
        city.append("http://api.apixu.com/v1/forecast.json?key=7ec3788f0bf5483ca0f141322180602&q="+placeName+"&days=7");

        try {
            URL url= new URL(city.toString());

            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
            InputStream inputStream=httpURLConnection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            String line="";

            while(line!=null){
                line=bufferedReader.readLine();
                data=data+line;
            }

            JSONObject JO = new JSONObject(data);


            JSONObject Joloc = JO.getJSONObject("location");
            JSONObject Jcur = JO.getJSONObject("current");

            sizeofTemp= Jcur.getString("temp_c").length();
            currTemp=Jcur.getString("temp_c")+" C°\n"+"(Feels like "+Jcur.getString("feelslike_c")+" C°)";

            JSONObject Jfor=JO.getJSONObject("forecast");
            JSONArray Jarr=Jfor.getJSONArray("forecastday");

            JSONObject JToday=Jarr.getJSONObject(0);
            JSONObject JcurrDay=JToday.getJSONObject("day");
            JSONObject currentIcon=JcurrDay.getJSONObject("condition");
            todayImgURL=currentIcon.getString("icon");
            textCondition=currentIcon.getString("text");



            USA=Joloc.getString("country");

            CountryCodes code= new CountryCodes();
            USA=code.getCode(USA);

            location=" "+Joloc.getString("name");
            if(location.length()>12){
                location=location.substring(0, 11)+"...";
            }

            location=location+",\n  "+USA;

            currentConditions="\n "+location+"\n\n   "+textCondition
            +"\n"+"   High: " +JcurrDay.getString("maxtemp_c")+" C°\n"
                    +"   Low: "+JcurrDay.getString("mintemp_c")+" C°";



            //4 day forecast==========================================================

            JSONObject Jday=Jarr.getJSONObject(1);
            JSONObject Jday1=Jday.getJSONObject("day");
            JSONObject icon=Jday1.getJSONObject("condition");

            day1d=Jday.getString("date");
            day1d=day1d.substring(5);
            day1=Jday1.getString("maxtemp_c")+" C°\n"+Jday1.getString("mintemp_c")+" C°";
            day1ImgURL =icon.getString("icon");



            JSONObject Jdayb=Jarr.getJSONObject(2);
            JSONObject Jday2=Jdayb.getJSONObject("day");
            JSONObject icon2=Jday2.getJSONObject("condition");
            day2d=Jdayb.getString("date");

            day2d=day2d.substring(5);
            day2=Jday2.getString("maxtemp_c")+" C°\n"+Jday2.getString("mintemp_c")+" C°";
            day2ImgURL =icon2.getString("icon");


            JSONObject Jdayc=Jarr.getJSONObject(3);
            JSONObject Jday3=Jdayc.getJSONObject("day");
            JSONObject icon3=Jday3.getJSONObject("condition");

            day3d=Jdayc.getString("date");
            day3d=day3d.substring(5);
            day3=Jday3.getString("maxtemp_c")+" C°\n"+Jday3.getString("mintemp_c")+" C°";
            day3ImgURL =icon3.getString("icon");

            JSONObject Jdayd=Jarr.getJSONObject(4);
            JSONObject Jday4=Jdayd.getJSONObject("day");
            JSONObject icon4=Jday4.getJSONObject("condition");

            day4d=Jdayd.getString("date");
            day4d=day4d.substring(5);
            day4=Jday4.getString("maxtemp_c")+" C°\n"+Jday4.getString("mintemp_c")+" C°";
            day4ImgURL =icon4.getString("icon");


        } catch (MalformedURLException e) {
            e.printStackTrace();
            currentConditions="+";
        } catch (IOException e) {
            e.printStackTrace();
            currentConditions="+";
        }catch (JSONException e){
            MainActivity.input.setError("Something went wrong");
            e.printStackTrace();
        }

        return null;

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

       /* if(isEmpty==1){
            MainActivity.input.setError("Search location");
        }*/

        if(currentConditions.equals("+")){
            MainActivity.input.setError("Search location");
        }

        else {
            SpannableString place = new SpannableString(currentConditions);
            place.setSpan(new RelativeSizeSpan(1.5f), 0, location.length()+2 , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            place.setSpan(new ForegroundColorSpan(Color.YELLOW), 0, location.length() + 2, 0);

            SpannableString tmp = new SpannableString(currTemp);
            tmp.setSpan(new RelativeSizeSpan(.4f), sizeofTemp + 3, currTemp.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            MainActivity.output.setText(place);
            MainActivity.degrees.setText(tmp);

            Context contextToday = MainActivity.day1.getContext();
            ;
            Picasso.with(contextToday).load("http:" + todayImgURL).resize(100, 100)
                    .into(MainActivity.cond);


            Context context = MainActivity.day1.getContext();
            ;
            Picasso.with(context).load("http:" + day1ImgURL).resize(100, 100)
                    .into(MainActivity.day1);
            MainActivity.day1textDate.setText(this.day1d);
            MainActivity.day1text.setText(this.day1);

            Context context2 = MainActivity.day2.getContext();
            ;
            Picasso.with(context2).load("http:" + day2ImgURL).resize(100, 100)
                    .into(MainActivity.day2);
            MainActivity.day2textDate.setText(this.day2d);
            MainActivity.day2text.setText(this.day2);


            Context context3 = MainActivity.day3.getContext();
            ;
            Picasso.with(context3).load("http:" + day3ImgURL).resize(100, 100)
                    .into(MainActivity.day3);
            MainActivity.day3textDate.setText(this.day3d);
            MainActivity.day3text.setText(this.day3);

            Context context4 = MainActivity.day4.getContext();
            ;
            Picasso.with(context4).load("http:" + day4ImgURL).resize(100, 100)
                    .into(MainActivity.day4);
            MainActivity.day4textDate.setText(this.day4d);
            MainActivity.day4text.setText(this.day4);
        }

        //========================================================================================
    }


}

