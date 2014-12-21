package streams.factories;

import streams.algebras.ExecStreamAlg;
import streams.higher.App;

import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Authors:
 * Aggelos Biboudis (@biboudis)
 * Nick Palladinos (@NickPalladinos)
 */
public class LogFactory<E, C> implements ExecStreamAlg<E, C> {

    private final ExecStreamAlg<E, C> alg;

    public LogFactory(ExecStreamAlg<E, C> alg) {
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
    public <T> App<E, Long> count(App<C, T> app) {
        return alg.count(app);
    }

    @Override
    public <T> App<E, T> reduce(T identity, BinaryOperator<T> accumulator, App<C, T> app) {
        return alg.reduce(identity, accumulator, app);
    }
}
