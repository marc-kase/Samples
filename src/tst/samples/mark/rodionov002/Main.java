package tst.samples.mark.rodionov002;

import java.util.concurrent.ExecutionException;
import java.util.function.Function;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Created by MM on 09.01.2016.
 */
public class Main {

    private static final Function<Double, Double> function = x -> sin(x) * sin(x) + cos(x) * cos(x);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        double start = 0;
        double end = 10000.0;
        double step = 0.1;
        int chunks = 4;

        Function<Double, Double> calcFunc = y -> step * y;
        Function<Double, Double> sqrFunc = function.andThen(calcFunc);

        double sequentialThreashold = (end - start) / (chunks * step);

        System.out.println("sequentialThreashold = " + sequentialThreashold);

        long tseq = System.currentTimeMillis();
        SequentialCalculate calc = new SequentialCalculate(
                sqrFunc);
        double resSq = calc.calculate(start, end, step);
        tseq = System.currentTimeMillis() - tseq;

        ThreadCalculate threadCalculate = new ThreadCalculate();
        double resThread = threadCalculate.calculate(start, end, step, chunks,
                sqrFunc);

        long ttpool = System.currentTimeMillis();
        ThreadPoolCalculate threadPoolCalculate = new ThreadPoolCalculate();
        double resPool = threadPoolCalculate.calculate(start, end, step, chunks,
                sqrFunc);
        ttpool = System.currentTimeMillis() - ttpool;


        ForkJoinCalculate forkJoinCalculate = new ForkJoinCalculate(start, end, step, sequentialThreashold,
                sqrFunc);
        double resFJ = forkJoinCalculate.compute();

        long tfjpool = System.currentTimeMillis();
        ForkJoinPoolCalculate forkJoinPoolCalculate = new ForkJoinPoolCalculate(start, end, step, sequentialThreashold,
                sqrFunc);
        double resFJPool = forkJoinPoolCalculate.compute();
        tfjpool = System.currentTimeMillis() - tfjpool;


        long tstream = System.currentTimeMillis();
        StreamCalculate streamCalc = new StreamCalculate(start, end, step, (x -> sin(x) * sin(x) + cos(x) * cos(x)));
//        double streamSum = streamCalc.sumParallel();
        double streamSum = streamCalc.sum();
        tstream = System.currentTimeMillis() - tstream;

        System.out.println("\n" +
                        "SequentialCalculate = " + resSq + "  t=" + tseq +
                        "\nThreadCalculate = " + resThread +
                        "\nThreadPoolCalculate = " + resPool + "  t=" + ttpool +
                        "\nForkJoinCalculate = " + resFJ +
                        "\nForkJoinPoolCalculate = " + resFJPool + "  t=" + tfjpool +
                        "\nStreamCalculate = " + streamSum + "  t=" + tstream
        );
    }
}
