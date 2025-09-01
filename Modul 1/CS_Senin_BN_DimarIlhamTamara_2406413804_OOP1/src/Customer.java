public class Customer {
    String nama;
    Kendaraan kendaraan;
    Customer(String nama, Kendaraan kendaraan){
        this.nama = nama;
        this.kendaraan = kendaraan;
    }

    double getTotalPrice(){
        return kendaraan.harga;
    }

    void showDetail(){
        System.out.printf("Customer Name: %s\n", nama);
        kendaraan.showDetail();
    }
}
