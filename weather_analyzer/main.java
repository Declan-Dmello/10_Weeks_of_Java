package weather_analyzer;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

public class main {

    public static void main(String[] args) {
        try {

            System.out.println("Input the Location :  ");
            Scanner s1 = new Scanner(System.in);
            String loc = s1.nextLine();
            String api_url = "http://api.weatherapi.com/v1/forecast.json?key=83b47fc453af478794f114437252803&q=" + loc + "&days=2&aqi=no&alerts=no\n";
            URL url = new URL(api_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONObject json_response = new JSONObject(response.toString());
            JSONObject current = json_response.getJSONObject("current");
            JSONObject location = json_response.getJSONObject("location");
            String region = location.getString("region");
            String country = location.getString("country");
            double c_temp = current.getDouble("temp_c");
            double wind_speed = current.getDouble("wind_kph");
            double humidity = current.getDouble("humidity");
            double visibility = current.getDouble("vis_km");
            String condition = current.getJSONObject("condition").getString("text");

            JSONArray forecast = json_response.getJSONObject("forecast").getJSONArray("forecastday");
            for (int i = 0; i < forecast.length(); i++) {
                JSONObject day = forecast.getJSONObject(i);

                JSONArray hourly = day.getJSONArray("hour");

                for (int j = 0; j < hourly.length(); j++) {
                    JSONObject hour = hourly.getJSONObject(j);
                    String time = hour.getString("time");

                    if (time.equals("2025-03-29 07:00"))
                    {
                        double f_c_temp =     hour.getDouble("temp_c");
                        double f_wind_speed = hour.getDouble("wind_kph");
                        double f_humidity =   hour.getDouble("humidity");
                        double f_visibility = hour.getDouble("vis_km");
                        String f_condition =  hour.getJSONObject("condition").getString("text");

                        System.out.println("\n\n\nWeather Report for "+ loc + " ," + region+ " ,"+country);
                        System.out.println("\n Current Weather ");
                        System.out.println("---------------");
                        System.out.println("Temperature is " + c_temp);
                        System.out.println("Condition " + condition);
                        System.out.println("Windspeed is " + wind_speed);
                        System.out.println("Humidity is " + humidity);
                        System.out.println("Visibility " + visibility);


                        System.out.println("\nWeather Forecast for 2025-03-29 at 07:00 AM");
                        System.out.println("---------------");
                        System.out.println("Temperature will be  " + f_c_temp);
                        System.out.println("Condition will be " +      f_condition);
                        System.out.println("Windspeed will be " +   f_wind_speed);
                        System.out.println("Humidity will be  " +    f_humidity);
                        System.out.println("Visibility will be  " +   f_visibility);
                    }

                }
            }



        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

