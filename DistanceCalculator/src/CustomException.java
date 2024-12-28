public class CustomException extends Throwable {

    // Hata fırlatabilmesi için Throwable sınıfını extend eden bir özel hata sınıfı oluşturuldu.
    // Bu sınıfın Constructor yani yapıcı metodu biri parametreli biri parametresiz olmak üzere OVERLOAD edildi.

    // Burada hata durumunda belirlenen hata mesajı yazdırılıyor.
    public CustomException() {
        super("You entered wrong data! You can select just 'yes' or 'no'.");
    }
    // Burada ise kullanıcı ilgili yerin hata durumunda hangi mesajın gösterilmesini istiyorsa onu yazdırıyor.
    public CustomException(String message) {
        super(message);
    }

}
