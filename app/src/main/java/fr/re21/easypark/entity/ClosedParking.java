package fr.re21.easypark.entity;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fr.re21.easypark.customInterface.ServerResponseInterface;
import fr.re21.easypark.utils.ConnectionUtils;
import fr.re21.easypark.utils.URLConfig;

/**
 * Created by maxime on 19/05/15.
 */
public class ClosedParking {

    public static final int GET=1;
    public static final String TYPE="closedParkingList";

    private String id, name, adresse, city, weekHour, sundayHour;
    private Double latitude, longitude;
    private int totalPlace, remainPlace, zipCode;
    private boolean card, cash, coins, privatePark;

    public ClosedParking(){}

    public ClosedParking(String id, String name, String adresse, String city, Double latitude, Double longitude, int totalPlace, int remainPlace, int zipCode) {
        this.id = id;
        this.name = name;
        this.adresse = adresse;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.totalPlace = totalPlace;
        this.remainPlace = remainPlace;
        this.zipCode = zipCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public int getTotalPlace() {
        return totalPlace;
    }

    public void setTotalPlace(int totalPlace) {
        this.totalPlace = totalPlace;
    }

    public int getRemainPlace() {
        return remainPlace;
    }

    public void setRemainPlace(int remainPlace) {
        this.remainPlace = remainPlace;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getWeekHour() {
        return weekHour;
    }

    public void setWeekHour(String weekHour) {
        this.weekHour = weekHour;
    }

    public String getSundayHour() {
        return sundayHour;
    }

    public void setSundayHour(String sundayHour) {
        this.sundayHour = sundayHour;
    }

    public boolean isCard() {
        return card;
    }

    public void setCard(boolean card) {
        this.card = card;
    }

    public boolean isCash() {
        return cash;
    }

    public void setCash(boolean cash) {
        this.cash = cash;
    }

    public boolean isCoins() {
        return coins;
    }

    public void setCoins(boolean coins) {
        this.coins = coins;
    }

    public boolean isPrivatePark() {
        return privatePark;
    }

    public void setPrivatePark(boolean privatePark) {
        this.privatePark = privatePark;
    }

    public static SyncData getCloseParkingList(ArrayList<ClosedParking> closedParkingList, ServerResponseInterface sri, Context context) {
        System.out.println("get close parking list");
        SyncData syncData = new SyncData(GET, closedParkingList, URLConfig.closedParkingURL, sri, context);
        syncData.execute();
        return syncData;
    }

    public static class SyncData extends AsyncTask<Void, Void, Boolean> {

        private int type;
        private String url;
        private String result;
        private JSONArray jsonResult = null;
        private ArrayList<ClosedParking> closedParkingList;
        private ServerResponseInterface sri;
        private Context context=null;

        public SyncData(int type, ArrayList<ClosedParking> closedParkingList, String url, ServerResponseInterface sri ,Context context) {
            super();
            this.type = type;
            this.url = url;
            this.result = null;
            this.closedParkingList=closedParkingList;
            this.sri=sri;
            this.context=context;
        }


        @Override
        protected void onPreExecute() {}

        @Override
        protected Boolean doInBackground(Void... params) {
            if (type == GET) {
                result = ConnectionUtils.dataGet(url, context);
                if(result!=null){
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {

            if(type==GET){
                System.out.println(result);
                try {
                    jsonResult = new JSONArray(result);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

                closedParkingList = closedParkingListJsonParse(jsonResult);
                EntityList.closedParkingList=closedParkingList;

                if(sri != null) {
                    sri.onEventCompleted(GET, TYPE);
                }
            }
        }
    }

    public static ArrayList<ClosedParking> closedParkingListJsonParse(JSONArray jsonResult) {
        ArrayList<ClosedParking> clientList = new ArrayList<>();
        try {

            for (int i = 0; i < jsonResult.length(); i++) {
                JSONObject jsonClosedParking = jsonResult.getJSONObject(i);
                clientList.add(closedParkingJsonParse(jsonClosedParking));
            }
            return clientList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ClosedParking closedParkingJsonParse(JSONObject jsonPark)
            throws JSONException {
        ClosedParking closedParking = new ClosedParking();

        if(jsonPark.has("id")){
            closedParking.setId(jsonPark.getString("id"));
        }

        if(jsonPark.has("nom")){
            closedParking.setName(jsonPark.getString("nom"));
        }

        if (jsonPark.has("latitude")){
            closedParking.setLatitude(jsonPark.getDouble("latitude"));
        }

        if (jsonPark.has("longitude")) {
            closedParking.setLongitude(jsonPark.getDouble("longitude"));
        }

        if (jsonPark.has("places_total")) {
            closedParking.setTotalPlace(jsonPark.getInt("places_total"));
        }

        if(jsonPark.has("places_restantes")) {
            closedParking.setRemainPlace(jsonPark.getInt("places_restantes"));
        }

        if(jsonPark.has("adresse")) {
            closedParking.setAdresse(jsonPark.getString("adresse"));
        }

        if(jsonPark.has("code_postal")) {
            closedParking.setZipCode(jsonPark.getInt("code_postal"));
        }

        if(jsonPark.has("ville")) {
            closedParking.setCity(jsonPark.getString("ville"));
        }

        if(jsonPark.has("horaire_payant_semaine")) {
            closedParking.setWeekHour(jsonPark.getString("horaire_payant_semaine"));
        }

        if(jsonPark.has("horaire_payant_dimanche")) {
            closedParking.setSundayHour(jsonPark.getString("horaire_payant_dimanche"));
        }

        if(jsonPark.has("carte_bancaire")) {
            if (jsonPark.getInt("carte_bancaire") == 1){
                closedParking.setCard(true);
            } else {
                closedParking.setCard(false);
            }
        }

        if(jsonPark.has("billets")) {
            if (jsonPark.getInt("billets") == 1){
                closedParking.setCash(true);
            } else {
                closedParking.setCash(false);
            }
        }

        if(jsonPark.has("pieces")) {
            if (jsonPark.getInt("pieces") == 1){
                closedParking.setCoins(true);
            } else {
                closedParking.setCoins(false);
            }
        }

        if(jsonPark.has("prive")) {
            if (jsonPark.getInt("prive") == 1){
                closedParking.setPrivatePark(true);
            } else {
                closedParking.setPrivatePark(false);
            }
        }
        return closedParking;
    }

}
