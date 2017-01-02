import java.io.*;
import java.text.DecimalFormat;

/**
 * Created by bozhidar on 30.11.16.
 */
public class FullTernaryRunner {
    public static final int SIZE = 100000;
    public static final int MAX_RANGE = SIZE;
    public static final int RUNS = SIZE;
    public static final int TESTS = 100;

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Start of program");
        File dir = new File("./output");
        if(!dir.exists()){
            dir.mkdir();
        }

        try (FileWriter fw = new FileWriter("output/output.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(SIZE + " random numbers, " + MAX_RANGE + " maximum range of key, " + RUNS + " tests on the same data set");
            out.println("Binary-Complex Ternary-Equal-Times Faster average-min fast-max fast");

            // System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream("output/test.txt")), true));
            //System.out.println(SIZE + " random numbers, " + MAX_RANGE + " maximum range of key, " + RUNS + " tests on the same data set");
            //System.out.println("Binary-Complex Ternary-Times Faster average-min fast-max fast");
            for (int k = 0; k < TESTS; k++) {
                if ((k + 1) % (TESTS / 100) == 0){
                    System.out.println((k + 1)/(TESTS / 100) + "% done");
                }
                FullTernarySearch search = new FullTernarySearch(SIZE, MAX_RANGE);
                long binary = 0, complexT = 0;
                double ratio = 0;
                double minR = 10, maxR = 0;
                for (int i = 0; i < RUNS; i++) {
                    search.generateKey();
                    long b, c;
                    b = search.binarySearch();
                    c = search.checkClose();
                    if (c == 0) c = 1;
                    if (b == 0) b = 1;
                    long min = Math.min(b, c);
                    if (c == min) complexT++;
                    if (b == min) binary++;
                    double r = (double) b / (double) c;
                    ratio += r;
                    if (r > maxR) maxR = r;
                    if (r < minR) minR = r;
                    //System.out.println(b + " " + c + " " + search.getKey());
                    //System.out.println("Binary: " + binary + " Simple ternary: " + simpleT + " Complex ternary: " + complexT + "(" + i + ")");
                    //System.out.println();
                }
                DecimalFormat df = new DecimalFormat("0.0000");
                out.println(binary + "-" + complexT + "-" + (binary + complexT - SIZE) + "-" + df.format(ratio / RUNS) + "-" + df.format(minR) + "-" + df.format(maxR));
                //System.out.println(/*binary +*/ binary + "-" + complexT + "-" + df.format(ratio / RUNS) + "-" + df.format(minR) + "-" + df.format(maxR));
            }
            out.println();
        } catch (IOException e) {

        }


    }
}
