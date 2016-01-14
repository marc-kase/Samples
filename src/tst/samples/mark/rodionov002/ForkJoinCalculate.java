package tst.samples.mark.rodionov002;

import java.util.concurrent.RecursiveTask;
import java.util.function.Function;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Created by MM on 09.01.2016.
 */
public class ForkJoinCalculate extends RecursiveTask<Double> {
    private final double start;
    private final double end;
    private final double step;
    private final double sequentialThreashold;
    private final Function<Double, Double> func;

//    private final long SEQUENTIAL_THREASHOLD = 2500;

    public ForkJoinCalculate(double start, double end, double step, double sequentialThreashold, Function<Double, Double> func) {
        this.start = start;
        this.end = end;
        this.step = step;
        this.sequentialThreashold = sequentialThreashold;
        this.func = func;
    }

    private Double sequentialCompute() {
        double result = 0.0;
        double x = start;
        while (x < end) {
            result +=
                    func.apply(x);
            x += step;
        }
        return result;
    }

    @Override
    protected Double compute() {

        if ((end - start) / step < sequentialThreashold) {
            return sequentialCompute();
        }

        double mid = start + (end - start) / 2.0;
        ForkJoinCalculate left  = new ForkJoinCalculate(start, mid, step, sequentialThreashold, func);
        ForkJoinCalculate right = new ForkJoinCalculate(mid, end, step, sequentialThreashold, func);

        left.fork();
        double rightAnsw = right.compute();
        double leftAnsw = left.join();

        return leftAnsw + rightAnsw;
    }


    public static void main(String[] args) {
        ForkJoinCalculate threadCalculate = new ForkJoinCalculate(0, 1000, 0.1, 5000, x -> (sin(x) * sin(x) + cos(x) * cos(x)));
        double res = threadCalculate.compute();
        System.out.println(res);
    }
}
