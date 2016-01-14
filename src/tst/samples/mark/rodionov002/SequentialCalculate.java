package tst.samples.mark.rodionov002;

import java.util.function.Function;

import static java.lang.Math.cos;
import static java.lang.Math.sin;


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
                    func.apply(x);
            x += step;
        }
        return result;
    }

    public static void main(String[] args) {
        SequentialCalculate calc = new SequentialCalculate(x -> (sin(x) * sin(x) + cos(x) * cos(x)));
        double res = calc.calculate(0, 1000, 0.1);
        System.out.println(res);
    }
}
