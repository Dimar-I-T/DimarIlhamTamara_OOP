import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        KitchenFacade kitchenFacade = new KitchenFacade();
        System.out.println("Selamat datang di dapur Ramid");
        Scanner inp = new Scanner(System.in);
        int pilihan = 0;
        while (pilihan != 4) {
            print("1. Masak Pasta");
            print("2. Buat Salad");
            print("3. Membersihkan dapur");
            print("4. Keluar dari dapur");
            System.out.print("Pilihlah menu(1-4): ");
            pilihan = inp.nextInt();
            switch (pilihan) {
                case 1:
                    kitchenFacade.startCooking(new PastaRecipe());
                    break;
                case 2:
                    kitchenFacade.startCooking(new SaladRecipe());
                    break;
                case 3:
                    kitchenFacade.cleanUp();
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Input tidak valid");
            }

            print("");
        }

        inp.close();
    }

    public static void print(String s) {
        System.out.println(s);
    }
}
