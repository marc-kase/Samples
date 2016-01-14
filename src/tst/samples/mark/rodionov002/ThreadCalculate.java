package tst.samples.mark.rodionov002;

import java.util.function.Function;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Created by MM on 07.01.2016.
 */
public class ThreadCalculate {
    public double calculate(double start, double end, double step, int chunks,
                            Function<Double, Double> func) throws InterruptedException {
        PartialCalcThread[] threads = new PartialCalcThread[chunks];
        double interval = (end - start) / chunks;
        double st = start;
        for (int i = 0; i < chunks; i++) {
            threads[i] = new PartialCalcThread(st, st + interval, step, func);
            threads[i].start();
            st += interval;
        }

        double result = 0.0;
        for (PartialCalcThread t : threads) {
            t.join();
            result += t.partialResult;
        }
        return result;
    }

    class PartialCalcThread extends Thread {
        private final double start;
        private final double end;
        private final double step;
        public double partialResult;
        private final Function<Double, Double> func;

        public PartialCalcThread(double start, double end, double step, Function<Double, Double> func) {
            this.start = start;
            this.end = end;
            this.step = step;
            this.func = func;
        }

        public void run() {
            partialResult = 0.0;
            double x = start;
            while (x < end) {
                partialResult +=
                        func.apply(x);
                x += step;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadCalculate threadCalculate = new ThreadCalculate();
        double res = threadCalculate.calculate(0, 1000, 0.1, 2, x -> (sin(x) * sin(x) + cos(x) * cos(x)));
        System.out.println(res);
    }
}
