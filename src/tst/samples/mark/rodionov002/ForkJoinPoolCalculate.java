package tst.samples.mark.rodionov002;

import java.util.concurrent.ForkJoinPool;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Created by MM on 09.01.2016.
 */
public class ForkJoinPoolCalculate {
    private final double start;
    private final double end;
    private final double step;
    public final double sequentialThreashold;
    private final Function<Double, Double> func;

    public ForkJoinPoolCalculate(double start, double end, double step, double sequentialThreashold,
                                 Function<Double, Double> func) {
        this.start = start;
        this.end = end;
        this.step = step;
        this.sequentialThreashold = sequentialThreashold;
        this.func = func;
    }

    public double compute() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinCalculate calc = new ForkJoinCalculate(start, end, step, sequentialThreashold,
                x -> (sin(x) * sin(x) + cos(x) * cos(x)));
        return forkJoinPool.invoke(calc);
    }
}
