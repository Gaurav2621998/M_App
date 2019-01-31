package com.gourav.doctor_profile;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static com.gourav.doctor_profile.MapsActivity.latitude;
import static com.gourav.doctor_profile.MapsActivity.longitude;

/**
 * @author Priyanka
 */

public class GetNearbyPlacesData extends AsyncTask<Object, String, String> {

    String googlePlacesData;
    GoogleMap mMap;
    String url;
    RecyclerView recyclerView;
    TextView tvdistance,tvtime;
    HospitalAdapter hospitalAdapter;
    Context context;

    public static List<distime>Distime=new ArrayList<>();


    List<Hospital_info>hospital_infos=new ArrayList<>();

    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap)objects[0];
        url = (String)objects[1];
        recyclerView=(RecyclerView)objects[2];
        context=(Context)objects[3];
        tvdistance=(TextView)objects[4];
        tvtime=(TextView)objects[5];

        DownloadUrl downloadUrl = new DownloadUrl();
        try {
            googlePlacesData = downloadUrl.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s) {
        List<HashMap<String, String>> nearbyPlaceList = null;
        DataParser parser = new DataParser();
        nearbyPlaceList = parser.parse(s);
        showNearbyPlaces(nearbyPlaceList);
    }

    private void showNearbyPlaces(List<HashMap<String,String>> nearbyPlaceList)
    {
        hospital_infos.clear();
        Distime.clear();
        for(int i = 0;i<nearbyPlaceList.size() ; i++)
        {
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String , String> googlePlace = nearbyPlaceList.get(i);
            Log.d("onPostExecute","Entered into showing locations");

            String placeName = googlePlace.get("place_name");
            String vicinity = googlePlace.get("vicinity"
            );

            double lat = Double.parseDouble( googlePlace.get("lat") );
            double lng = Double.parseDouble( googlePlace.get("lng"));
            Hospital_info hospital_info=new Hospital_info(placeName,lat,lng);




            Object dataTransfer[] = new Object[5];
            String url=getDirectionsUrl(lat,lng);


            GetDirectionsData getDirectionsData = new GetDirectionsData();
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            dataTransfer[2] = new LatLng(lat, lng);
            dataTransfer[3]=i;
           // getDirectionsData.execute(dataTransfer);





            hospital_infos.add(hospital_info);
            LatLng latLng = new LatLng(lat, lng);
            markerOptions.position(latLng);
            markerOptions.title(placeName +" : "+ vicinity);
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital));

            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(13));

        }

        Collections.reverse(hospital_infos);

        HospitalAdapter hospitalAdapter=new HospitalAdapter(context,hospital_infos,mMap,Distime);
        recyclerView.setAdapter(hospitalAdapter);



    }

    private String getDirectionsUrl(double end_latitude,double end_longitude)
    {
        StringBuilder googleDirectionsUrl = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
        googleDirectionsUrl.append("origin="+ latitude +","+longitude);
        googleDirectionsUrl.append("&destination="+end_latitude+","+end_longitude);
        googleDirectionsUrl.append("&key="+"AIzaSyARLiWp8GNGd1Ma_uriKYZ_DcwzuMXK2Z4");

        return googleDirectionsUrl.toString();
    }

}
