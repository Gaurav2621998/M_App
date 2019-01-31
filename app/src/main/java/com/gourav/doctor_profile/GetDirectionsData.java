package com.gourav.doctor_profile;

import android.graphics.Color;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.gourav.doctor_profile.GetNearbyPlacesData.Distime;


/**
 * @auth Priyanka
 */

public class GetDirectionsData extends AsyncTask<Object,String,String> {

    GoogleMap mMap;
    String url;
    String googleDirectionsData;
    TextView tvdistance,tvtime;
    public static Polyline polylineFinal = null;


    LatLng latLng;
    int count;


    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap)objects[0];
        url = (String)objects[1];
        latLng = (LatLng)objects[2];
     //   count=(int)objects[3];
//        tvdistance=(TextView)objects[3];
//        tvtime=(TextView)objects[4];






        DownloadUrl downloadUrl = new DownloadUrl();
        try {
            googleDirectionsData = downloadUrl.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return googleDirectionsData;
    }

    @Override
    protected void onPostExecute(String s) {

//        String[] directionsList;
//        DataParser parser = new DataParser();
//        directionsList = parser.parseDirections(s);
//
//        DataParser duration=new DataParser();
//        HashMap<String,String> googleDirectionsMap = new HashMap<>();
//        googleDirectionsMap=duration.parseDurations(s);
//        display(googleDirectionsMap);

//        displayDirection(directionsList,googleDirectionsMap);

        TaskParser taskParser = new TaskParser();
        taskParser.execute(s);

    }
    public void display(HashMap<String,String>googlemapDuration){
        String duration=googlemapDuration.get("duration");
        String distance=googlemapDuration.get("distance");

         distime distime=new distime(distance,duration,0,0);
        Distime.add(distime);

    }


    public class TaskParser extends AsyncTask<String, Void, List<List<HashMap<String, String>>> > {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... strings) {
            JSONObject jsonObject = null;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jsonObject = new JSONObject(strings[0]);
                DataParser directionsParser = new DataParser();
                routes = directionsParser.parse1(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> lists) {

            //Get list route and display it into the map

            ArrayList points = null;

            PolylineOptions polylineOptions = null;

            for (List<HashMap<String, String>> path : lists) {
                points = new ArrayList();
                polylineOptions = new PolylineOptions();

                for (HashMap<String, String> point : path) {
                    double lat = Double.parseDouble(point.get("lat"));
                    double lon = Double.parseDouble(point.get("lon"));

                    points.add(new LatLng(lat, lon));
                }

                polylineOptions.addAll(points);
                polylineOptions.width(15);
                polylineOptions.color(Color.BLUE);
                polylineOptions.geodesic(true);

               // tvdistance.setText(Distime.get(0).getTxtdis());
                //tvtime.setText(Distime.get(0).getTxttime());

            }

            if (polylineOptions != null) {
                polylineFinal = mMap.addPolyline(polylineOptions);
            } else {

            }

        }
    }




    public void displayDirection(String[] directionsList,HashMap<String,String>googlemapDuration)
    {
        String duration=googlemapDuration.get("duration");
        String distance=googlemapDuration.get("distance");



//        PolylineOptions options = new PolylineOptions();
//
//
//
//        int count = directionsList.length;
//        for(int i = 0;i<count;i++)
//        {
//            options.color(Color.BLUE);
//            options.geodesic(true);
//            options.width(10);
//            options.addAll(PolyUtil.decode(directionsList[i]));
//
//            mMap.addPolyline(options);
//        }
//
//
//
//        tvdistance.setText(distance);
//        tvtime.setText(duration);
//


    }






}

