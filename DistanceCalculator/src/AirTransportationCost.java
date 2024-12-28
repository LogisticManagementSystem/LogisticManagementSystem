import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Çoklu kalıtım sağlayan AirTransportationCost sınıfı tanımlandı ve
// TimeCalculator sınıfını extend ederken Transportable arayüzünü implement etti.
public class AirTransportationCost extends TimeCalculator implements Transportable {

    // Private olarak değişkenler tanımlandı.
    private TransportMethod transportMethod;
    private double airSpeed = 475;

    // Air ve Road arasında oluşabilecek karışıklıkları önlemek adına ROAD verisi girildiğinde AIR'a Null değer atandı.
    public AirTransportationCost(TransportMethod transportMethod) {
        // Eğer taşıma türü ROAD ise, AirTransportation'ı hiç kullanmama durumu ayarlandı.
        if (transportMethod == TransportMethod.ROAD) {
            this.transportMethod = null; // Bu durumda hiçbir şey yapılmayacak.
        } else {
            this.transportMethod = transportMethod;
        }
    }

    // Hem RoadTransportationCost hem de AirTransportationCost sınıflarında calculateCostPerKm metodu OVERRIDE edilerek kullanıldı
    // Bu sayede aynı isimde farklı içerikte metod kullanabilme esnekliğine sahip olundu.
    @Override
    public double calculateCostPerKm() {
        // Sadece AIR taşıma türü seçildiyse işlem yapılıyor.
        if (transportMethod == TransportMethod.AIR) {
            return 2.5;
        }
        return 0; // ROAD durumunda işlem yapmıyoruz, bu yüzden 0 döndürüyoruz
    }

    // Aynı şekilde Hem RoadTransportationCost hem de AirTransportationCost sınıflarında calculateCapacity metodu OVERRIDE edilerek kullanıldı
    // Bu sayede aynı isimde farklı içerikte metod kullanabilme esnekliğine sahip olundu.
    @Override
    public int calculateCapacity() {
        // Sadece AIR taşıma türü seçildiyse işlem yapılıyor.
        if (transportMethod == TransportMethod.AIR) {
            return 500;
        }
        return 0; // ROAD durumunda işlem yapmıyoruz, bu yüzden 0 döndürüyoruz.
    }

    // Km olarak hava yolu mesafesiyle özel olarak katsayıları ayarlanan calculateCostPerKm metodunun döndürdüğü değer çarpılarak bir maliyet üretildi.
    public double totalCostCalculator() {
        // Eğer AIR taşıma türü yoksa, işlem yapma
        if (transportMethod == TransportMethod.AIR) {
            double totalCost = DistanceCalculator.getAirDistance() * calculateCostPerKm();
            return totalCost;
        }
        return 0; // ROAD durumunda işlem yapmıyoruz, bu yüzden 0 döndürüyoruz.
    }

    /* Km olarak belirlenen maliyeti yine özel olarak ayarladığımız uçak kapasitesine böldükten sonra
       üzerine taşıması yapılacak kargonun boyutuna ağırlığına ve hassasiyetine göre Packaging sınıfında belirlenen değerler ile
       özel 0.75 katsayısı çarpılarak kullanıcının ödeyeceği kargolama fiyatı hesaplandı.*/
    public double cargoFee() {
        // Eğer AIR taşıma türü yoksa, işlem yapılmayacak.
        if (transportMethod == TransportMethod.AIR) {
            double cargoFee = DistanceCalculator.getAirDistance() * calculateCostPerKm() / calculateCapacity()
                    + 0.75 * Packaging.packageCostCalculator();
            return cargoFee;
        }
        return 0; // ROAD durumunda işlem yapmıyoruz, bu yüzden 0 döndürüyoruz.
    }

    /* Hava Yolu hızını km/h cinsinden yazdırıyor ve
       aşağıdaki parametreli OVERLOAD edilmiş metottakiyle aynı olacak şekilde süreyi saat dakika saniye olarak yazdırıyor.*/
    @Override
    public String TimeCalculator() {
        double airTime = DistanceCalculator.getAirDistance()/airSpeed;
        int second = (int)(airTime*3600);
        int minute = second/60;
        int hour = minute/60;
        second = second%60;
        minute = minute%60;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm:ss");

        // Anlık zamanı alıyoruz
        LocalDateTime now = LocalDateTime.now();

        // Güncel zamanı yazdırıyoruz
        String formattedDateTime = now.format(formatter);

        // Üzerine eklenen zamanı hesaplıyoruz
        LocalDateTime updatedTime = now.plusHours(hour)
                .plusMinutes(minute)
                .plusSeconds(second);

        // Üzerine eklenmiş zamanı yazdırıyoruz
        String updatedDateTime = updatedTime.format(formatter);

        if (now.isBefore(updatedTime)) {
            System.out.println("Current Time is before the Updated Time.");
        } else if (now.isAfter(updatedTime)) {
            System.out.println("Current Time is after the Updated Time.");
        } else if (now.isEqual(updatedTime)) {
            System.out.println("Current Time is equal to the Updated Time.");
        }

        return String.format("Flight Speed (km/h): "+airSpeed+"km/h\nFlight Time: "+hour+" Hour "+minute+" Minute "+second+" Second\nCurrent Time and Date: "+formattedDateTime+"\nArriving Time and Date: " +updatedDateTime);
    }

    /* Hava Yolu hızını metre/saniye cinsinden hesaplayan hem OVERRIDE hem de OVERLOAD edilmiş
        (bir tanesi hızı m/s olarak yazdırma ile ilgili parametre alırken
        diğeri otomatik olarak parametresiz şekilde km/h biriminde hızı yazdırıyor)
        kargolamanın süresini saat dakika saniye olarak hesaplayan ve hızı da kullanıcının isteğine bağlı m/s olarak yazdıran metod.*/
    @Override
    public String TimeCalculator(boolean isMetersPerSecond) {
        double airDistance = DistanceCalculator.getAirDistance();
        if (isMetersPerSecond) {
            airDistance = airDistance*1000; // Kilometers to meters
            airSpeed = (int)(airSpeed / 3.6); // km/h to m/s
        }
        double airTime = airDistance / airSpeed;
        int second = (int)airTime;
        int minute = second / 60;
        int hour = minute / 60;
        second = second % 60;
        minute = minute % 60;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm:ss");

        // Anlık zamanı alıyoruz
        LocalDateTime now = LocalDateTime.now();

        // Güncel zamanı yazdırıyoruz
        String formattedDateTime = now.format(formatter);

        // Üzerine eklenen zamanı hesaplıyoruz
        LocalDateTime updatedTime = now.plusHours(hour)
                .plusMinutes(minute)
                .plusSeconds(second);

        // Üzerine eklenmiş zamanı yazdırıyoruz
        String updatedDateTime = updatedTime.format(formatter);
        if (now.isBefore(updatedTime)) {
            System.out.println("Current Time is before the Updated Time.");
        } else if (now.isAfter(updatedTime)) {
            System.out.println("Current Time is after the Updated Time.");
        } else if (now.isEqual(updatedTime)) {
            System.out.println("Current Time is equal to the Updated Time.");
        }

        // String.format yardımıyla verileri girdi olarak verdiğimiz formatta yazdırıyoruz böylece okunabilirlik artıyor.
        return String.format("Flight Speed (m/s): "+airSpeed+"m/s\nFlight Time: "+hour+" Hour "+minute+" Minute "+second+" Second\nCurrent Time and Date: "+formattedDateTime+"\nArriving Time and Date: " +updatedDateTime);

    }

}
