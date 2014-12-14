package streams;

import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by bibou on 11/1/14.
 */
public class LogFactory<C> implements LogAlg<C> {
    
    private final StreamTerminalAlg<Id.t, C> alg;

    public LogFactory(StreamTerminalAlg<Id.t, C> alg) {
        this.alg = alg;
    }

    @Override
    public <T, R> App<C, R> flatMap(Function<T, App<C, R>> mapper, App<C, T> app) {
        return alg.flatMap(i -> {
            System.out.print("flatMap : " + i.toString());
            App<C, R> result = mapper.apply(i);
            System.out.println(" -> " + i.toString());
            return result;
        }, app);
    }

    @Override
    public <T> App<C, T> source(T[] array) {
        System.out.println("Starting Execution: ");
        return alg.source(array);
    }

    @Override
    public <T, R> App<C, R> map(Function<T, R> mapper, App<C, T> app) {
        return alg.map(i -> {
            System.out.print("map: " + i.toString());
            R result = mapper.apply(i);
            System.out.println(" -> " + result.toString());
            return result;
        }, app);
    }

    @Override
    public <T> App<C, T> filter(Predicate<T> predicate, App<C, T> app) {
        return alg.<T>filter(i -> {
            System.out.println("filter: " + i.toString());
            Boolean result = predicate.test(i);
            System.out.println(" -> " + i.toString());
            return result;
        }, app);
    }

    @Override
    public <T> App<Id.t, Long> count(App<C, T> app) {
        return alg.count(app);
    }

    @Override
    public <T> App<Id.t, T> reduce(T identity, BinaryOperator<T> accumulator, App<C, T> app) {
        return reduce(identity, accumulator, app);
    }
}
