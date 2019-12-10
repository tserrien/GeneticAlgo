public class Config{
    public static final String TARGET = "HELLO WORLD";
    public static boolean debug = false;
    public static int mutationChance = 300000;
    public static int children = 2;

    public static void setDebug(boolean reset){
        debug = reset;
    }

    public static void setMutationChance(int mutation){
        mutationChance = mutation;
    }

    public static void setChildren(int childNumber){
        children = childNumber;
    }
}