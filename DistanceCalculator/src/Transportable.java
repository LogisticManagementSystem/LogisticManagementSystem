
// Transportable interface'i tanımlandı.
// Bunun amacı bu arayüzü implement eden tüm sınıfların bu özelliklere sahip olma zorunluluğunu sağlamaktır.

// Yani hem ROAD hem de AIR taşıma yöntemlerinin zorunlu olarak
// calculateCostPerKm ve calculateCapacity metotları yardımıyla KM başı maliyet ve kapasite hesaplarının bulunması gerekir.

public interface Transportable {
    double calculateCostPerKm(); // Araç için km başına maliyet
    int calculateCapacity();// Araç için kapasite
}