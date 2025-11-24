public class PastaRecipe extends RecipeAssembler {
    @Override
    public void prepareIngredients() {
        System.out.println("Siapkan: ");
        String[] ingredients = {"200g Spageti", "1500ml Air", "1 sdt Garam", "1 sdm Minyak", "2 buah Bawang bombay", "500 g Daging giling", "100 g Saus Carbonara", "1 sdm Saus Cabai"};
        super.printIngredients(ingredients);
    }

    @Override
    public void cook() {
        System.out.println("\nMemulai memasak pasta");
        String[] steps = {"Didihkan air, tambahkan garam dan minyak dan rebus spageti selama 7 menit atau sesuai kematangan yang diinginkan, angkat dan tiriskan.", "Iris dan tumis bawang bombay hingga harum.", "Masukkan daging giling dan masak hingga matang.", "Tambahkan saos carbonara dan saos aduk rata dan masak sebentar saja.", "Siram spageti dengan saos carbonara daging"};
        super.printCookingSteps(steps);
    }

    @Override
    public void serve() {
        System.out.println("\nMenyajikan Pastanya.");
    }
}
