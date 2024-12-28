
// Hem AirTransportationCost sınıfının hem de RoadTransportationCost sınıfının extend ettiği abstract(soyut) sınıf.
// Bu soyut sınıf sayesinde Time Calculator sınıfını soyutlayarak kullanıcının sadece gereken yere odaklanması sağlandı.
// Ve soyut sınıfların metotları tanımlanırken gövde bulunduramayacakları için gövdesiz OVERLOAD edilen metotlar tanımlandı.

public abstract class TimeCalculator {
    public abstract String TimeCalculator();
    public abstract String TimeCalculator(boolean isMetersPerSecond);
}