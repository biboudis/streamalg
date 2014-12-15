package streams;

import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Authors:
 * Aggelos Biboudis (@biboudis)
 * Nick Palladinos (@NickPalladinos)
 */
public class FuturePullFactory implements FuturePullAlg {
    @Override
    public <T> App<Future.t, Long> count(App<Pull.t, T> app) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public <T> App<Future.t, T> reduce(T identity, BinaryOperator<T> accumulator, App<Pull.t, T> app) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public <T> App<Pull.t, T> source(T[] array) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public <T, R> App<Pull.t, R> map(Function<T, R> f, App<Pull.t, T> app) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public <T, R> App<Pull.t, R> flatMap(Function<T, App<Pull.t, R>> f, App<Pull.t, T> app) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public <T> App<Pull.t, T> filter(Predicate<T> f, App<Pull.t, T> app) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
