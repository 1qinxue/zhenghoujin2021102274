package com.jnu.myrecycle.data;

import static com.tencent.map.tools.internal.u.s;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class ShopDownloader {

    @NonNull
    public String doDownloadData(String strings){
        StringBuffer sb=new StringBuffer();
        ArrayList<ShopLocation> shopsLocations = new ArrayList<>();
        try {
            URL url = new URL(strings);
            HttpURLConnection connect = (HttpURLConnection) url.openConnection();
            connect.setRequestMethod("GET");
            connect.connect();
            int code = connect.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connect.getInputStream()));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();
                connect.disconnect();
            }
        }
        catch (MalformedURLException e){

        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

        public ArrayList<ShopLocation> parsonJson(String json){
        ArrayList<ShopLocation> shopsLocations = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(json);
            JSONArray shops = root.getJSONArray("shops");
            for(int i=0;i<shops.length();++i) {
                JSONObject shop = shops.getJSONObject(i);

                ShopLocation shopLocation = new ShopLocation();
                shopLocation.setName(shop.getString("name"));
                shopLocation.setLatitude(shop.getDouble("latitude"));
                shopLocation.setLongitude(shop.getDouble("longitude"));
                shopLocation.setMemo(shop.getString("memo"));
                shopsLocations.add(shopLocation);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return shopsLocations;
        }


}
