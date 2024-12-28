import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Çoklu kalıtım sağlayan ARoadTransportationCost sınıfı tanımlandı ve
// TimeCalculator sınıfını extend ederken Transportable arayüzünü implement etti.
public class RoadTransportationCost extends TimeCalculator implements Transportable {

    // Private olarak değişkenler tanımlandı.
    private TransportMethod transportMethod;
    private double roadSpeed = 90;

    // Air ve Road arasında oluşabilecek karışıklıkları önlemek adına AIR verisi girildiğinde ROAD'a Null değer atandı.
    public RoadTransportationCost(TransportMethod transportMethod) {
        // Eğer taşıma türü AIR ise, RoadTransportation'ı hiç kullanmama durumu ayarlandı.
        if (transportMethod == TransportMethod.AIR) {
            this.transportMethod = null; // Bu durumda hiçbir şey yapılmayacak.
        } else {
            this.transportMethod = transportMethod;
        }
    }

    // Hem TransportationCost hem de AirTransportationCost sınıflarında calculateCostPerKm metodu OVERRIDE edilerek kullanıldı
    // Bu sayede aynı isimde farklı içerikte metod kullanabilme esnekliğine sahip olundu.
    @Override
    public double calculateCostPerKm() {
        // Sadece ROAD taşıma türü seçildiyse işlem yapılıyor.
        if (transportMethod == TransportMethod.ROAD) {
            return 1.5;
        }
        return 0; // AIR durumunda işlem yapmıyoruz, bu yüzden 0 döndürüyoruz.
    }

    // Aynı şekilde Hem RoadTransportationCost hem de AirTransportationCost sınıflarında calculateCapacity metodu OVERRIDE edilerek kullanıldı
    // Bu sayede aynı isimde farklı içerikte metod kullanabilme esnekliğine sahip olundu.
    @Override
    public int calculateCapacity() {
        // Sadece ROAD taşıma türü seçildiyse işlem yapılıyor.
        if (transportMethod == TransportMethod.ROAD) {
            return 2000;
        }
        return 0; // AIR durumunda işlem yapmıyoruz, bu yüzden 0 döndürüyoruz
    }

    // Km olarak kara yolu mesafesiyle özel olarak katsayıları ayarlanan calculateCostPerKm metodunun döndürdüğü değer çarpılarak bir maliyet üretildi.
    public double totalCostCalculator() {
        // Eğer ROAD taşıma türü yoksa, işlem yapma
        if (transportMethod == TransportMethod.ROAD) {
            double totalCost = DistanceCalculator.getRoadDistance() * calculateCostPerKm();
            return totalCost;
        }
        return 0; // AIR durumunda işlem yapmıyoruz, bu yüzden 0 döndürüyoruz
    }

    // Km olarak belirlenen maliyeti yine özel olarak ayarladığımız kara yolu taşıtının kapasitesine böldükten sonra
    // üzerine taşıması yapılacak kargonun boyutuna ağırlığına ve hassasiyetine göre Packaging sınıfında belirlenen değerler ile
    // özel 0.55 katsayısı çarpılarak kullanıcının ödeyeceği kargolama fiyatı hesaplandı.
    public double cargoFee() {
        // Eğer ROAD taşıma türü yoksa, işlem yapmayacak.
        if (transportMethod == TransportMethod.ROAD) {
            double cargoFee = DistanceCalculator.getRoadDistance() * calculateCostPerKm() / calculateCapacity()
                    + 0.55 * Packaging.packageCostCalculator();
            return cargoFee;
        }
        return 0; // AIR durumunda işlem yapmıyoruz.
    }

    // Kara Yolu hızını km/h cinsinden yazdırıyor ve
    // aşağıdaki parametreli OVERLOAD edilmiş metottakiyle aynı olacak şekilde süreyi saat dakika saniye olarak yazdırıyor.
    @Override
    public String TimeCalculator() {
        double roadTime = DistanceCalculator.getRoadDistance()/roadSpeed;
        int second = (int)(roadTime*3600);
        int minute = second / 60;
        int hour = minute / 60;
        second = second % 60;
        minute = minute % 60;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm:ss");

        //while (true) {
        // Anlık zamanı alıyoruz
        LocalDateTime now = LocalDateTime.now();

        // Güncel zamanı yazdırıyoruz
        String formattedDateTime = now.format(formatter);
        //System.out.print("\rCurrent Time: " + formattedDateTime+"\n");

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

        return String.format("Flight Speed (km/h): "+roadSpeed+"km/h\nFlight Time: "+hour+" Hour "+minute+" Minute "+second+" Second\nCurrent Time and Date: "+formattedDateTime+"\nArriving Time and Date: " +updatedDateTime);
    }

    /* Kara Yolu hızını metre/saniye cinsinden hesaplayan hem OVERRIDE hem de OVERLOAD edilmiş
        (bir tanesi hızı m/s olarak yazdırma ile ilgili parametre alırken
        diğeri otomatik olarak parametresiz şekilde km/h biriminde hızı yazdırıyor)
        kargolamanın süresini saat dakika saniye olarak hesaplayan ve hızı da kullanıcının isteğine bağlı m/s olarak yazdıran metod.*/

    public String TimeCalculator(boolean isMetersPerSecond) {
        double roadDistance = DistanceCalculator.getRoadDistance();
        if (isMetersPerSecond) {
            roadDistance = roadDistance * 1000; // Kilometers to meters
            roadSpeed = roadSpeed / 3.6; // km/h to m/s
        }
        double roadTime = roadDistance / roadSpeed;
        int second = (int)roadTime;
        int minute = second / 60;
        int hour = minute / 60;
        second = second % 60;
        minute = minute % 60;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm:ss");

        //while (true) {
        // Anlık zamanı alıyoruz
        LocalDateTime now = LocalDateTime.now();

        // Güncel zamanı yazdırıyoruz
        String formattedDateTime = now.format(formatter);
        //System.out.print("\rCurrent Time: " + formattedDateTime+"\n");

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

        return String.format("Flight Speed (km/s): "+roadSpeed+"m/s\nFlight Time: "+hour+" Hour "+minute+" Minute "+second+" Second\nCurrent Time and Date: "+formattedDateTime+"\nArriving Time and Date: " +updatedDateTime);
    }
}
