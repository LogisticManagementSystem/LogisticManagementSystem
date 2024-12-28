import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeAndDate {

    public static void main() throws InterruptedException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm:ss");

        // Anlık zamanı alıyoruz
        LocalDateTime now = LocalDateTime.now();

        // Güncel zamanı yazdırıyoruz
        String formattedDateTime = now.format(formatter);
        System.out.print("\rCurrent Time and Date: " + formattedDateTime+"\n");


    }

}
