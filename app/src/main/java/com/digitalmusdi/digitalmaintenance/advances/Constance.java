package com.digitalmusdi.digitalmaintenance.advances;

public class Constance {
    public String SERVER_API = "https://karyaku.id/mrtjakarta/public/api";

    public String getLogin(){
        return SERVER_API+"/login";
    }

    public String getLogout(){return SERVER_API+"/logout";}
}
