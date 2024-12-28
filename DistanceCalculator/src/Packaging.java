public class Packaging {
    // private olarak ihtiyacımız olan değişkenler tanımlandı.
    private static double productSize;
    private static double productWeight;
    private static boolean productSensitivity;

    //private değişkenlerin değerlerini kullanıcı değiştireceği için setter metodları tanımlandı.
    public void setProductSize(double productSize) {
        this.productSize = productSize;
    }
    public void setProductWeight(double productWeight) {
        this.productWeight = productWeight;
    }
    public boolean isProductSensitivity() {
        return productSensitivity;
    }
    public void setProductSensitivity(boolean productSensitivity) {
        this.productSensitivity = productSensitivity;
    }

    // Packaging sınıfının parametreli Constructor(Yapıcı) Metodu.
    public Packaging(double productSize, double productWeight, boolean productSensitivity) {
        this.productSize = productSize;
        this.productWeight = productWeight;

        // String.valueOf yapısı ile kullanıcının girdiği yes veya no girdisi alınıyor
        // ve parseBoolean yapısı ile yes ise boolean true değeri no ise de false değerine çeviriyor.
        this.productSensitivity = Boolean.parseBoolean(String.valueOf(productSensitivity));
    }

    // Kullanıcının girdiği ürün hacmine göre belirlenen Boyut katsayılarının hesaplanması.
    public static int productSizeMultipiler(){
        if(productSize > 0 && productSize <= 300){
            return 1;
        }
        else if(productSize > 300 && productSize <= 1000){
            return 2;
        }
        else if(productSize > 1000 && productSize <= 10000){
            return 3;
        }
        else {
            return 4;
        }
    }

    // Kullanıcının girdiği ürün ağırlığına göre belirlenen Ağırlık katsayılarının hesaplanması.
    public static int productWeightMultipiler(){
        if(productWeight > 0 && productWeight <= 1){
            return 1;
        }
        else if(productWeight > 1 && productWeight <= 5){
            return 2;
        }
        else if(productWeight > 5 && productWeight <= 25){
            return 3;
        }
        else{
            return 4;
        }
    }

    // Ağırlık ve hacim katsayıları yardımıyla özel bir maliyet hesabı
    public static double packageCostCalculator() {
        if(productSensitivity){
            return (2 * productSizeMultipiler() + 1.5 * productWeightMultipiler() + 4);
        }else
            return (2 * productSizeMultipiler() + 1.5 * productWeightMultipiler());
    }
}