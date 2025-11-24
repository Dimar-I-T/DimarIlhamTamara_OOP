public class SaladRecipe extends RecipeAssembler{
    @Override
    public void prepareIngredients() {
        System.out.println("Siapkan: ");
        String[] ingredients = {"2 buah Tomat", "1 buah jagung manis", "1 buah labu siam", "1 telur rebus", "5 lembar selada", "3 lembar kol", "1 buah wortel", "Saos Wijen"};
        super.printIngredients(ingredients);
    }

    @Override
    public void cook() {
        System.out.println("\nMemulai membuat salad");
        String[] steps = {"Serut wortel, iris-iris sayur kol, potong-potong tomat, potong-potong labu siam", "Kemudian siapkan Air dan didihkan air", "Rebus satu persatu sayur mulai dari labu siam, jagung, wortel, kol, terakhir telur", "Setelah matang lalu angkat dan sajikan", "Sajikan dengan saos wijen bisa di beli di mini market"};
        super.printCookingSteps(steps);
    }

    @Override
    public void serve() {
        System.out.println("\nMenyajikan Saladnya.");
    }
}
