public class Kendaraan {
    String brand;
    int year;
    VehicleType type;
    float harga;

    Kendaraan(String brand, int year, VehicleType type, float harga){
        this.brand = brand;
        this.year = year;
        this.type = type;
        this.harga = harga;
    }

    void showDetail() {
        System.out.printf("Brand: %s\n", brand);
        System.out.printf("Year: %d\n", year);
        switch (type){
            case Mobil:
                System.out.println("Type: Mobil");
                break;
            case Motor:
                System.out.println("Type: Motor");
                break;
            case Truck:
                System.out.println("Type: Truck");
                break;
        }
        System.out.printf("Harga: %.1f\n", harga);
    }
}
