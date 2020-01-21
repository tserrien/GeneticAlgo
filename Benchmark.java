import java.text.DecimalFormat;

public class Benchmark{

    private static DecimalFormat df = new DecimalFormat("#.##");
    private long start = 0;
    private long end = 0;
    private long totalMem = 0;
    private long usedMem = 0;
    private char separator = ';';

    public Benchmark(){}

    public void Start(){
        start = System.nanoTime();
        totalMem = Runtime.getRuntime().totalMemory();
    }

    public void End(){
        end = System.nanoTime();
        usedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }

    public String PrintRuntime(){
        long runtimeNano = 0;
        double runtime = 0;
        String unit = "";


        runtimeNano = end - start;

        if(runtimeNano > 1000000){
            runtime = (double)runtimeNano/1000000;
            unit = "ms";
            if(runtime > 1000){
                runtime = runtime / 1000;
                unit = "s";
            }

        }else{
            unit = "nanos";
        }

        return "Runtime: " + df.format(runtime) + " " + unit;
    }

    private double MemoryUsage(){
        String[] unit = new String[] {"b", "kb", "Mb", "Gb", "Tb"};
        double memory = (double) usedMem;
        byte counter = 0;
        while (memory > 1024){
            memory = memory / 1024;
            counter++;
        }
        return memory;
    }

    public String PrintMemoryUsage(){
                return "Used memory: " + df.format(MemoryUsage) + " " + unit[counter];
    }

    public String PrintResult(){
        return PrintRuntime() + " " + PrintMemoryUsage();
    }

    public String PrintCsvStyle(){
        return (end - start) + separator + Long.toString(usedMem);
    }

    public long getUsedMemory(){
        return usedMem;
    }

    public long getRuntimeNano(){
        return end - start;
    }
}