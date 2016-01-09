package tst.samples.mark.rodionov002;

import java.util.concurrent.*;

import static java.lang.Math.*;

/**
 * Created by MM on 07.01.2016.
 */
public class ThreadPoolCalculate {
    public double calculate(double start, double end, double step, int chunks, Function<Double, Double> func) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(chunks);
        Future[] futures = new Future[chunks];

        double interval = (end - start) / chunks;
        double st = start;
        for (int i = 0; i < chunks; i++) {
            futures[i] = executorService
                    .submit(new CallableCalcThread(st, st + interval, step, func));
            st += interval;
        }
        executorService.shutdownNow();

        Double result = 0.0;
        for (Future t : futures) {
            result += (Double) t.get();
        }
        return result;
    }

    class CallableCalcThread implements Callable<Double> {
        private final double start;
        private final double end;
        private final double step;
        public double partialResult;
        private final Function<Double, Double> func;

        public CallableCalcThread(double start, double end, double step, Function<Double, Double> func) {
            this.start = start;
            this.end = end;
            this.step = step;
            this.func = func;
        }

        @Override
        public Double call() throws Exception {
            partialResult = 0.0;
            double x = start;
            while (x < end) {
                partialResult +=
                        step * func.apply(x);
                x += step;
            }
            return partialResult;
        }
    }


    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ThreadPoolCalculate threadCalculate = new ThreadPoolCalculate();
        double res = threadCalculate.calculate(0, 1000, 0.1, 2, x -> (sin(x) * sin(x) + cos(x) * cos(x)));
        System.out.println(res);
    }
}
