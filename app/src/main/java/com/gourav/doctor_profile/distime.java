package com.gourav.doctor_profile;

public class distime {

    public distime(String txtdis, String txttime, int distance, int time) {
        this.txtdis = txtdis;
        this.txttime = txttime;
        this.distance = distance;
        this.time = time;
    }

    public distime() {
    }

    public String getTxtdis() {
        return txtdis;
    }

    public void setTxtdis(String txtdis) {
        this.txtdis = txtdis;
    }

    public String getTxttime() {
        return txttime;
    }

    public void setTxttime(String txttime) {
        this.txttime = txttime;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    String txtdis,txttime;
    int distance,time;
}
