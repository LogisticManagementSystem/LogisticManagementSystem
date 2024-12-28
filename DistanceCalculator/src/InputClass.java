import java.io.*;
import java.util.*;

public class InputClass {

    // Kategorileri ve öncelikleri saklamak için iki ArrayList kullanıldı
    private ArrayList<String> categories = new ArrayList<>();
    private ArrayList<Integer> priorities = new ArrayList<>();

    public void inputMethod() throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        // Kullanıcıya sistem hakkında bilgi veren karşılama mesajı
        System.out.println("");
        System.out.println("* Welcome to the Logistic Management System :)     *");
        System.out.println("* This program will help you about follow  process *");
        System.out.println("* of your cargo, calculating of your payment.      *");
        System.out.println("* You can use it by more than one person. It's     *");
        System.out.println("* sensible to multiple usage.                      *");
        System.out.println("\n");

        TimeAndDate timeAndDate = new TimeAndDate();
        timeAndDate.main();

        while (true) {
            // Kullanıcı adı alma ve çıkış kontrolü
            System.out.println("Welcome! Please enter your name (or type 'quit' to exit):");
            String name = sc.next();

            if (name.equalsIgnoreCase("quit")) {
                break;  // Döngüden çıkar ve işlemi bitirir.
            }

            // Ürün bilgileri alma işlemi
            boolean validInput = false;
            while (!validInput) {
                String category = "";
                String[] validCategories = {"foods", "valuable", "electronics", "industrial", "furniture", "textile","other"};

                // Kullanıcıdan geçerli kategori alana kadar döngüde kalır
                while (true) {
                    try {
                        System.out.println("Enter the category of your product\n(Medicine-Foods-Valuable-Electronics-Industrial-Furniture-Textile-Other):");
                        category = sc.next().trim();

                        boolean isValidCategory = false;
                        // Geçerli kategori kontrolü
                        for (String validCategory : validCategories) {
                            if (category.equalsIgnoreCase(validCategory)) {
                                isValidCategory = true;
                                break;
                            }
                        }

                        if (isValidCategory) {
                            System.out.println("You entered a valid category: " + category);
                            break; // Geçerli kategori girildiğinde döngüden çıkar
                        } else {
                            throw new IllegalArgumentException("Invalid category!");
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }

                // Geçerli kategori girildiğinde, boyut ve diğer bilgileri soruluyor
                double productSize = 0;
                boolean sizeValid = false;
                while (!sizeValid) {
                    try {
                        System.out.println("Enter the size of your product (cm3): ");
                        double product_Size = sc.nextDouble();

                        if (product_Size > 0) {
                            productSize = product_Size;
                            sizeValid = true;  // Boyut geçerli ise, işlemi devam ettiriliyor.
                        } else {
                            throw new CustomException("Size must be a positive number.");
                        }
                    } catch (InputMismatchException e) {
                        sc.nextLine(); // Girdi akışını temizle
                        System.out.println("Invalid input! Please enter a numeric value.");
                    } catch (CustomException customExceptionClass) {
                        System.out.println(customExceptionClass.getMessage());
                    }
                }

                // Ürün ağırlığı alma ve doğrulama
                double productWeight = 0;
                boolean weightValid = false;
                while (!weightValid) {
                    try {
                        System.out.println("Enter the weight of your product (kilograms): ");
                        double product_Weight = sc.nextDouble();

                        if (product_Weight > 0) {
                            productWeight = product_Weight;
                            weightValid = true;  // Ağırlık geçerli ise işlemi devam ettiriliyor.
                        } else {
                            throw new CustomException("Weight must be a positive number.");
                        }
                    } catch (InputMismatchException e) {
                        sc.nextLine(); // Girdi akışını temizler
                        System.out.println("Invalid input! Please enter a numeric value.");
                    } catch (CustomException customExceptionClass) {
                        System.out.println(customExceptionClass.getMessage());
                    }
                }

                // Ürün hassasiyet bilgisi alma ve doğrulama işlemi
                boolean productSensitive = false;
                boolean sensitivityValid = false;
                while (!sensitivityValid) {
                    try {
                        System.out.println("Enter the sensitivity of your product ('yes' or 'no'): ");
                        String productSensitiveString = sc.next();

                        if (productSensitiveString.equalsIgnoreCase("yes")) {
                            productSensitive = true;
                            sensitivityValid = true;
                        } else if (productSensitiveString.equalsIgnoreCase("no")) {
                            productSensitive = false;
                            sensitivityValid = true;
                        } else {
                            throw new CustomException("Invalid input! Please enter 'yes' or 'no'.");
                        }
                    } catch (CustomException customExceptionClass) {
                        System.out.println(customExceptionClass.getMessage());
                    }
                }

                // Storage sınıfı kullanılarak ürün kategorisi kontrolü
                Storage storage = new Storage(category);
                storage.setProductCategory(category);
                int priority = storage.productCategoryControl();

                // Packaging sınıfı ile ürün özellikleri ayarlandı
                Packaging packaging = new Packaging(productSize, productWeight, productSensitive);
                packaging.setProductSize(productSize);
                packaging.setProductWeight(productWeight);
                packaging.setProductSensitivity(productSensitive);

                // Ürün kategorisi ve önceliği listeye eklendi
                categories.add(category);
                priorities.add(priority);

                System.out.println("Category: " + category + ", Priority: " + priority);

                // Yeni kategori eklenip eklenmeyeceğini kontrol et
                boolean categoryValid = false;
                String categoryStringToBoolean = null;

                // String.valueOf yapısı ile kullanıcının girdiği yes veya no girdisi alınıyor
                // ve parseBoolean yapısı ile yes ise boolean true değeri no ise de false değerine çeviriyor.
                Boolean.parseBoolean(String.valueOf(categoryStringToBoolean));
                while (!categoryValid) {
                    try {
                        System.out.println("Do you want to add another product? 'yes' or 'no':");
                        categoryStringToBoolean = sc.next();

                        if (categoryStringToBoolean.equalsIgnoreCase("yes")) {
                            categoryValid = true;
                        }
                        else if (categoryStringToBoolean.equalsIgnoreCase("no")) {
                            categoryValid = true;
                            validInput = true;
                        }
                        else {
                            throw new CustomException("Invalid input! Please enter 'yes' or 'no'.");
                        }
                    } catch (CustomException customExceptionClass) {
                        System.out.println(customExceptionClass.getMessage());
                    }

                }
            }

            // Ürünleri öncelik sırasına göre sıralar ve yazdırır
            sortAndDisplayProducts();

            // Taşıma yöntemi ve diğer bilgileri işler
            DistanceCalculator.main();
            String transportMethod = String.valueOf(DistanceCalculator.getTransportMethod());

            // Verileri dosyaya yazma
            fileMethod(name, categories, priorities, transportMethod);

            // Dosyadan okuma
            readFile();
        }
    }

    public void sortAndDisplayProducts() {
        // Kategoriler ve öncelikler listesi kullanılarak ürünlerin oluşturulması
        ArrayList<Product> products = new ArrayList<>();
        for (int i = 0; i < categories.size(); i++) {
            products.add(new Product(categories.get(i), priorities.get(i)));
        }

        // Ürünleri öncelik sırasına göre artan şekilde sıralama
        products.sort(Comparator.comparingInt(Product::getPriority));

        // Sıralanmış ürün listesini ekrana yazdırma
        System.out.println("\nSorted list of products by priority:");
        for (Product product : products) {
            System.out.println("Category: " + product.getCategory() + ", Priority: " + product.getPriority());
        }
    }

    public void fileMethod(String name, ArrayList<String> categories, ArrayList<Integer> priorities, String transportMethod) {
        // Verilerin kaydedileceği dosya adı
        String fileName = "logistic.txt";

        // Seçilen taşıma yöntemine göre taşıma maliyeti nesneleri oluşturuluyor
        AirTransportationCost airTransportation = new AirTransportationCost(DistanceCalculator.getTransportMethod());
        RoadTransportationCost roadTransportation = new RoadTransportationCost(DistanceCalculator.getTransportMethod());

        ArrayList<Product> products = new ArrayList<>();
        for (int i = 0; i < categories.size(); i++) {
            products.add(new Product(categories.get(i), priorities.get(i)));
        }

        // Ürünleri öncelik sırasına göre sıralama
        // Comparator nesneleri sıralamak için kullanılan bir arayüz.
        // comparingInt bir int değerine dayalı olarak karşılaştırma yapmak için kullanılan bir yardımcı metot.
        products.sort(Comparator.comparingInt(Product::getPriority));
        // Product::getPriority Product'taki getPriority metodunu çağırır.

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            // Kullanıcı adını dosyaya yazma
            writer.write("Person name: " + name);
            writer.newLine();

            // Ürünlerin kategori ve öncelik bilgilerini dosyaya yazma
            for (Product product : products) {
                writer.write("Category: " + product.getCategory() + ", Priority: " + product.getPriority());
                writer.newLine();
                writer.newLine();
            }

            // Taşıma yöntemi bilgisi yazma
            writer.write("Transportation Method: " +transportMethod);
            writer.newLine();

            // Hava ve karayolu taşıma maliyetlerini hesaplama ve yazma
            double airTotalCost = airTransportation.totalCostCalculator();
            double roadTotalCost = roadTransportation.totalCostCalculator();
            double airCargoFee = airTransportation.cargoFee();
            double roadCargoFee = roadTransportation.cargoFee();

            // Kargo ücretlerini dosyaya yazma
            if(DistanceCalculator.getTransportMethod()==TransportMethod.ROAD){
                writer.write(String.format("Total transportation cost: %.2f$", roadTotalCost));
                writer.newLine();
            } else {
                writer.write(String.format("Total transportation cost: %.2f$", airTotalCost));
                writer.newLine();
            }

            if(DistanceCalculator.getTransportMethod()==TransportMethod.ROAD){
                writer.write(String.format("Cargo fee: %.2f$", roadCargoFee));
                writer.newLine();
                writer.newLine();
            } else {
                writer.write(String.format("Cargo fee: %.2f$", airCargoFee));
                writer.newLine();
                writer.newLine();
            }

            // Kullanıcıdan hız birimini alma
            boolean isMetersPerSecond = false; // Varsayılan değer
            boolean validInput = false;

            Scanner scanner = new Scanner(System.in);

            while (!validInput) { // Geçerli bir hız birimi girilene kadar döngü
                try {
                    System.out.print("Enter the speed unit:");

                    String input = scanner.next().toLowerCase();

                    if (input.equals("m/s")) {
                        isMetersPerSecond = true;
                        validInput = true;
                    } else if (input.equals("km/h")) {
                        isMetersPerSecond = false;
                        validInput = true;
                    } else
                        throw new CustomException("You can select just m/s or km/h !");
                }catch (CustomException customException)  {
                    customException.getMessage();
                } finally {
                    // Bu blok her durumda çalışacaktır.
                    System.out.println("Finally block is always executed.");
                }
            }

            // Taşıma süresini dosyaya yazma
            if(DistanceCalculator.getTransportMethod()==TransportMethod.ROAD){
                if(isMetersPerSecond == false){
                    writer.write(roadTransportation.TimeCalculator());
                    writer.newLine();
                }
                else if(isMetersPerSecond == true) {
                    writer.write(roadTransportation.TimeCalculator(isMetersPerSecond));
                    writer.newLine();
                }
            }
            else if(DistanceCalculator.getTransportMethod()==TransportMethod.AIR){
                if(isMetersPerSecond == false){
                    writer.write(airTransportation.TimeCalculator());
                    writer.newLine();
                }
                else if(isMetersPerSecond == true) {
                    writer.write(airTransportation.TimeCalculator(isMetersPerSecond));
                    writer.newLine();
                }
            }
            writer.newLine();
            System.out.println("Datas has registered to file." + fileName);

        } catch (IOException e) {
            System.out.println("Something went wrong !" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void readFile() {
        // Dosya adı
        String fileName = "logistic.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            System.out.println("\nRegistered Datas in file:\n");

            // Dosyayı satır satır okuma ve ekrana yazdırma işlemini yapar.
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Something went wrong !" + e.getMessage());
            e.printStackTrace();
    }

        }
}
