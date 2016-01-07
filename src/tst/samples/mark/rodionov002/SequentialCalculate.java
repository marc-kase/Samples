package tst.samples.mark.rodionov002;

import static java.lang.Math.*;


public class SequentialCalculate {
    private final Function<Double, Double> func;

    public SequentialCalculate(Function<Double, Double> func) {
        this.func = func;
    }

    public double calculate(double start, double end, double step) {
        double result = 0.0;
        double x = start;
        while (x < end) {
            result +=
                    step * func.apply(x);
            x += step;
        }
        return result;
    }

    public static void main(String[] args) {
        SequentialCalculate calc = new SequentialCalculate(x -> (sin(x) * sin(x) + cos(x) * cos(x)));
        calc.calculate(0, 10, 0.1);
    }
}
