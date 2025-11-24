public abstract class RecipeAssembler {
    final void prepareRecipe() {
        prepareIngredients();
        cook();
        serve();
    }

    abstract public void prepareIngredients();
    abstract public void cook();
    abstract public void serve();

    public void printIngredients(String[] ingredients) {
        for (String s : ingredients) {
            System.out.println("- " + s);
        }
    }

    public void printCookingSteps(String[] steps) {
        int i = 1;
        for (String s : steps) {
            System.out.println(i + ". " + s);
            i++;
        }
    }
}
