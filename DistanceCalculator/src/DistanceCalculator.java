import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;// okhttp, HTTP isteklerini kolayca göndermek ve yanıtları almak için kullandığımız kütüphane.
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Scanner;

public class DistanceCalculator {
    private static final String GEOCODING_API_KEY = ""; // Geocoding API key
    private static final String DISTANCE_MATRIX_API_KEY = ""; // Distance Matrix API key

    private static final OkHttpClient client = new OkHttpClient();  // HTTP istekleri için OkHttpClient nesnesi oluşturuldu.

    // Hava ve kara yolu mesafelerini saklamak için değişkenler
    private static double airDistance;
    private static double roadDistance;

    // Kara yolu mesafesini döndürmek için getter metodu yazıldı.
    public static double getRoadDistance() {
        return roadDistance;
    }

    // Hava yolu mesafesini döndürmek için getter metodu yazıldı.
    public static double getAirDistance() {
        return airDistance;
    }

    // Seçilen ulaşım yöntemini saklamak için "enum".
    private static TransportMethod transportMethod;

    public static void main() {
        Scanner scanner = new Scanner(System.in);

        // Kullanıcıdan kalkış şehri bilgisi alınıyor.
        System.out.print("Boarding City: ");
        //kullanıcıdan alınan girdinin başındaki veya sonundaki gereksiz boşlukları temizlemek için .trim() kullanıldı
        String startCity = scanner.nextLine().trim();

        // Kullanıcıdan varış şehri bilgisi alınıyor.
        System.out.print("Destination City: ");
        //kullanıcıdan alınan girdinin başındaki veya sonundaki gereksiz boşlukları temizlemek için .trim() kullanıldı
        String endCity = scanner.nextLine().trim();

        // Koordinatları al
        double[] startCoordinates = getCoordinates(startCity);
        double[] endCoordinates = getCoordinates(endCity);

        if (startCoordinates == null || endCoordinates == null) {
            System.out.println("Some problems has been occured while taking coordinates.");
            return;
        }

        // Geocoding API yardımıyla iki şehrin verisi koordinatlara dönüştürüldü ve
        // Haversine formülü ile aradaki kuş uçuşu mesafe yani Hava Yolu mesafesi hesaplandı.
        airDistance = calculateHaversineDistance(startCoordinates, endCoordinates);

        // Kara yolu mesafesi Distance Matrix API yardımıyla gerçekle birebir olarak alındı.
        roadDistance = getDrivingDistance(startCity, endCity);

        // Kara yolu ve hava yolunun toplam km olarak uzunluğu kullanıcıya gösterildi.
        System.out.printf("Road Distance: %.2f km\n", roadDistance);
        System.out.printf("Air Distance: %.2f km\n", airDistance);


        boolean isCorrectChoice = false;
        while (!isCorrectChoice) {
            //En ucuz yolun karayolu olduğu ve en hızlı yolun da havayolu olduğu varsayılarak kullanıcıdan veri girişi bekleniyor.
            System.out.println("Press '1' for cheapest way\nPress '2' for fastest way");
            int choice = scanner.nextInt();

            //try catch yapısıyla kullanıcının 1 veya 2 hariç bir seçim yapması durumunda hata yakalandı
            // ve Overload edilmiş CustomException fırlatıldı.
            try {
            if (choice == 1) {
                System.out.println("You have selected Road Transportation.");
                transportMethod = TransportMethod.ROAD;
                isCorrectChoice=true;

            } else if (choice == 2) {
                System.out.println("You have selected Air Transportation.");
                transportMethod = TransportMethod.AIR;
                isCorrectChoice=true;

            } else {
                throw new CustomException("Invalid Selection");
            }
        } catch (CustomException e) {
            System.out.println(e.getMessage());
    }}

    }

    // Ulaşım yöntemini döndürmek için getter metodu tanımlandı.
    public static TransportMethod getTransportMethod() {
        return transportMethod;
    }

    // Geocoding API ile şehir koordinatlarını alınıyor.
    private static double[] getCoordinates(String city) {
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + city + "&key=" + GEOCODING_API_KEY;
        try {
            // HTTP isteği oluşturma ve gönderme
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();

            // İstek başarılıysa yanıtı işleme
            if (response.isSuccessful() && response.body() != null) {
                String jsonResponse = response.body().string();
                // API'lerden gelen veriler genelde JSON formatındadır. (JSON): JavaScript Object Notation.
                JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
                JsonObject location = jsonObject.getAsJsonArray("results").get(0).getAsJsonObject().getAsJsonObject("geometry").getAsJsonObject("location");

                // Enlem ve boylam değerlerini döndürme
                double lat = location.get("lat").getAsDouble();
                double lng = location.get("lng").getAsDouble();
                return new double[]{lat, lng};
            }
        } catch (IOException e) {
            System.out.println("There is a problem with API Request." + e.getMessage());
        }
        return null;
    }

    // Haversine Formülü ile iki nokta arasındaki hava yolu mesafesi hesaplanıyor.
    private static double calculateHaversineDistance(double[] start, double[] end) {
        final int R = 6371; // Dünya'nın yarıçapı (km)
        double latDistance = Math.toRadians(end[0] - start[0]);
        double lonDistance = Math.toRadians(end[1] - start[1]);

        // Haversine formülünün matematiksel hesaplamaları
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(start[0])) * Math.cos(Math.toRadians(end[0])) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    // Distance Matrix API kullanarak iki şehir arasındaki kara yolu mesafesini hesaplama
    private static double getDrivingDistance(String origin, String destination) {
        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + origin +
                "&destinations=" + destination + "&key=" + DISTANCE_MATRIX_API_KEY + "&units=metric";
        try {
            // HTTP isteği oluşturuluyor ve gönderiliyor.
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();

            // İstek başarılıysa yanıt işleniyor.
            if (response.isSuccessful() && response.body() != null) {
                String jsonResponse = response.body().string();
                JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
                JsonObject element = jsonObject.getAsJsonArray("rows")
                        .get(0).getAsJsonObject()
                        .getAsJsonArray("elements")
                        .get(0).getAsJsonObject();

                //Kara yolu mesafesi metre cinsinden alınıp kilometreye dönüştürüldü.
                return element.getAsJsonObject("distance").get("value").getAsDouble() / 1000.0; // metre -> km
            }
        } catch (IOException e) {
            System.out.println("There is a problem with API Request." + e.getMessage());
        }
        return 0;
    }
}