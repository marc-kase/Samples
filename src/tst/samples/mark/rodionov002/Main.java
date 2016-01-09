package tst.samples.mark.rodionov002;

import java.util.concurrent.ExecutionException;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Created by MM on 09.01.2016.
 */
public class Main {
    private static double function(Double x) {
        return sin(x) * sin(x) + cos(x) * cos(x);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        double start = 0;
        double end = 1000.0;
        double step = 0.1;
        int chunks = 4;
        double sequentialThreashold = (end - start) / (chunks * step);

        System.out.println("sequentialThreashold = " + sequentialThreashold);

        SequentialCalculate calc = new SequentialCalculate(x -> (function(x)));
        double resSq = calc.calculate(start, end, step);

        ThreadCalculate threadCalculate = new ThreadCalculate();
        double resThread = threadCalculate.calculate(start, end, step, chunks,
                x -> (function(x)));

        ThreadPoolCalculate threadPoolCalculate = new ThreadPoolCalculate();
        double resPool = threadPoolCalculate.calculate(start, end, step, chunks,
                x -> (function(x)));

        ForkJoinCalculate forkJoinCalculate = new ForkJoinCalculate(start, end, step, sequentialThreashold,
                x -> (function(x)));
        double resFJ = forkJoinCalculate.compute();

        ForkJoinPoolCalculate forkJoinPoolCalculate = new ForkJoinPoolCalculate(start, end, step, sequentialThreashold,
                x -> (function(x)));
        double resFJPool = forkJoinPoolCalculate.compute();

        System.out.println("\n" +
                "SequentialCalculate = " + resSq +
                "\nThreadCalculate = " + resThread +
                "\nThreadPoolCalculate = " + resPool +
                "\nForkJoinCalculate = " + resFJ +
                "\nForkJoinPoolCalculate = " + resFJPool
        );
    }
}
