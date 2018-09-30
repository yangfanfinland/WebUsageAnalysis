package com.lumiinsight.quartz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONException;
import org.json.JSONObject;




public class RequestJsonDemo {
	public static void main(String[] args) {
		String url = "http://api.geonames.org/citiesJSON?north=44.1&south=-9.9&east=-22.4&west=55.2&lang=de&username=demo";
		String json = loadJSON(url);
        System.out.println(json);
        
        JSONObject jsonObject;
		try {
			jsonObject = new JSONObject("{'hello':'world', 'abc':'xyz'}");
			System.out.println(jsonObject.getString("hello"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
        
        
	}
	
	public static String loadJSON (String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                                        yc.getInputStream()));
            String inputLine = null;
            while ( (inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        return json.toString();
    }
}
