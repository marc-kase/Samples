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
        double end = 1000.0;
        double step = 0.1;
        int chunks = 4;

        Function<Double, Double> calcFunc = y -> step * y;
        Function<Double, Double> sqrFunc = function.andThen(calcFunc);

        double sequentialThreashold = (end - start) / (chunks * step);

        System.out.println("sequentialThreashold = " + sequentialThreashold);

        SequentialCalculate calc = new SequentialCalculate(
                sqrFunc);
        double resSq = calc.calculate(start, end, step);

        ThreadCalculate threadCalculate = new ThreadCalculate();
        double resThread = threadCalculate.calculate(start, end, step, chunks,
                sqrFunc);

        ThreadPoolCalculate threadPoolCalculate = new ThreadPoolCalculate();
        double resPool = threadPoolCalculate.calculate(start, end, step, chunks,
                sqrFunc);

        ForkJoinCalculate forkJoinCalculate = new ForkJoinCalculate(start, end, step, sequentialThreashold,
                sqrFunc);
        double resFJ = forkJoinCalculate.compute();

        ForkJoinPoolCalculate forkJoinPoolCalculate = new ForkJoinPoolCalculate(start, end, step, sequentialThreashold,
                sqrFunc);
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
