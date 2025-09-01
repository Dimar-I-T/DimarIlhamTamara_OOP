public class Main {
    public static void main(String[] args) {
        Kendaraan kendaraanSupraBapak = new Kendaraan("Honda Supra", 1998, VehicleType.Motor, 3000);
        Kendaraan kendaraanKalcer = new Kendaraan("VW Beetle", 1998, VehicleType.Mobil, 200000);
        Kendaraan kendaraanGuede = new Kendaraan("Isuzu Giga", 2011, VehicleType.Truck, 300000);

        Customer customer1 = new Customer("Ramid", kendaraanSupraBapak);
        Customer customer2 = new Customer("Dimar", kendaraanKalcer);
        Customer customer3 = new Customer("Amara", kendaraanGuede);

        Customer[] customers = {customer1, customer2, customer3};
        String batas = "---------------------------------";
        for (Customer c : customers){
            System.out.println(batas);
            c.showDetail();
            System.out.println(batas);
        }
    }
}