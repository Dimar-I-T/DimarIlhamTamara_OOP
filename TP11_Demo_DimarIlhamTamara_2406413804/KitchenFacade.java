public class KitchenFacade {
    public void startCooking(RecipeAssembler recipe) {
        recipe.prepareRecipe();
    }

    public void cleanUp() {
        System.out.println("Membersihkan dapur");
    }
}
