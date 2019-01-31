package com.gourav.doctor_profile;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.mysiga.lib.view.CircleTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import static com.gourav.doctor_profile.GetDirectionsData.polylineFinal;
import static com.gourav.doctor_profile.MapsActivity.longitude;
import static com.gourav.doctor_profile.MapsActivity.latitude;
import static com.gourav.doctor_profile.MapsActivity.showdata;


public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.ViewHolder> {

    Context context;
    List<Hospital_info> hospital_infos;
    GoogleMap mMap;
    TextView tvdistance,tvtime;
    List<distime>Distime;
    int count=1;

    Polyline polyline=null;

    int[] Colors= new int[]{android.graphics.Color.RED,Color.parseColor("#35CDA9"),android.graphics.Color.YELLOW,Color.parseColor("#F99D12"),android.graphics.Color.MAGENTA,Color.parseColor("#57F912"),Color.parseColor("#E241A2"),Color.parseColor("#808080")};


    public HospitalAdapter(Context context, List<Hospital_info>hospital_infos , GoogleMap map,List<distime>Distime) {
        this.context = context;
        this.mMap=map;
        this.hospital_infos=hospital_infos;
        this.Distime=Distime;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hospital_info, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Random random=new Random();
        int x=random.nextInt(Colors.length);

        holder.hospitalname.setText(hospital_infos.get(position).getName());

        String s=hospital_infos.get(position).getName();
        holder.circleTextView.setText(String.valueOf(s.charAt(0)));
        holder.circleTextView.setCircleBackgroundColor(Colors[x]);


//        Toast.makeText(context, "Distance : "+Distime.get(position).getTxtdis()+"  Time : "+Distime.get(position).getTxttime(), Toast.LENGTH_SHORT).show();
       // holder.durationdata.setText("Distance : "+Distime.get(position).getTxtdis()+"  Time : "+Distime.get(position).getTxttime());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //showdata.setVisibility(View.VISIBLE);

                if(count>1)
                {
                        polylineFinal.remove();

                }


                Object dataTransfer[] = new Object[5];
                String url=getDirectionsUrl(hospital_infos.get(position).getLatitude(),
                        hospital_infos.get(position).getLongitude());


                GetDirectionsData getDirectionsData = new GetDirectionsData();
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;
                dataTransfer[2] = new LatLng(hospital_infos.get(position).getLatitude(),
                        hospital_infos.get(position).getLongitude());
//                dataTransfer[3]=tvdistance;
//                dataTransfer[4]=tvtime;
                count=count+1;

                Toast.makeText(context, String.valueOf(count), Toast.LENGTH_SHORT).show();



                getDirectionsData.execute(dataTransfer);



            }
        });

    }


    private String getDirectionsUrl(double end_latitude,double end_longitude)
    {
        StringBuilder googleDirectionsUrl = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
        googleDirectionsUrl.append("origin="+ latitude +","+longitude);
        googleDirectionsUrl.append("&destination="+end_latitude+","+end_longitude);
        googleDirectionsUrl.append("&key="+"AIzaSyCuhiczePJwW-HnRQAKOhaHigHXg3NRyTQ");

        return googleDirectionsUrl.toString();
    }

    @Override
    public int getItemCount() {
        return hospital_infos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView hospitalname,durationdata;

        CircleTextView circleTextView;

        LinearLayout linearLayout;



        public ViewHolder(View itemView) {
            super(itemView);
            circleTextView=(CircleTextView)itemView.findViewById(R.id.hospitalfirstname);
            linearLayout=itemView.findViewById(R.id.card_view);
                      hospitalname=itemView.findViewById(R.id.hospitalName);
                      durationdata=itemView.findViewById(R.id.durationdata);


        }
    }
}
