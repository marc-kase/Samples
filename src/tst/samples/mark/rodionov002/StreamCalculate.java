package tst.samples.mark.rodionov002;

import java.util.function.DoubleUnaryOperator;
import java.util.stream.DoubleStream;
import java.util.stream.LongStream;

/**
 * Created by MM on 14.01.2016.
 */
public class StreamCalculate {
    private final double start;
    private final double end;
    private final double step;
    private final DoubleUnaryOperator func;
    private final DoubleUnaryOperator sqFunc;

    public StreamCalculate(double start, double end, double step, DoubleUnaryOperator func) {
        this.start = start;
        this.end = end;
        this.step = step;
        this.func = func;

        DoubleUnaryOperator calcFunc = y -> step * y;
        sqFunc = func.andThen(calcFunc);
    }

    public double sumParallel() {
        return DoubleStream.iterate(0.0, s -> s + step)
                .limit((long) ((end - start) / step)) //todo Why step / 2 ?
                .parallel()
//                .map(sqFunc).reduce(0.0, Double::sum); //this is an equal notation
                .map(sqFunc).sum();
    }

    public double sum() {
        return LongStream
                .range(0, (long) ((end - start) / step)) //todo Why step / 2 ?
                .parallel()
                .mapToDouble(i -> start + step * i)
                .map(sqFunc)
                .sum();
    }
}
