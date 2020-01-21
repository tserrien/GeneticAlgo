public class Config{

    public static final String TARGET = "HELLO WORLD";
    public static boolean debug = false;
    public static int mutationChance = 300000;
    public static int children = 1;
    public static double elitePercent = 0.1;
    public static int partners = 3;
    public static int lifeTime = 2;

    private static char separator = ';';



    public static void setDebug(boolean reset){
        debug = reset;
    }

    public static void setMutationChance(int mutation){
        mutationChance = mutation;
    }

    public static void setChildren(int childNumber){
        children = childNumber;
    }

    public static void setElitePercent(double eliteP) {
        elitePercent = eliteP;
    }

    public static void setLifeTime(int lifeT) {
        lifeTime = lifeT;
    }

    public String toString(){
        return TARGET + separator + mutationChance + separator + children + separator + elitePercent + separator + partners
                + separator + lifeTime;
    }
}