package streams.factories;

import streams.algebras.ExecStreamAlg;
import streams.algebras.StreamAlg;
import streams.higher.App;
import streams.higher.Future;
import streams.higher.Id;

import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Authors:
 * Aggelos Biboudis (@biboudis)
 * Nick Palladinos (@NickPalladinos)
 */
public class ExecFutureFactory<C> implements ExecStreamAlg<Future.t, C> {

    private final ExecStreamAlg<Id.t, C> execAlg;
    private final StreamAlg<C> alg;


    public ExecFutureFactory(ExecStreamAlg<Id.t, C> execAlg, StreamAlg<C> alg) {
        this.alg = alg;
        this.execAlg = execAlg;
    }

    @Override
    public <T> App<Future.t, Long> count(App<C, T> app) {
        Future<Long> future = new Future<>(() -> Id.prj(execAlg.count(app)).value);
        future.run();
        return future;
    }

    @Override
    public <T> App<Future.t, T> reduce(T identity, BinaryOperator<T> accumulator, App<C, T> app) {
        Future<T> future = new Future<>(() -> Id.prj(execAlg.reduce(identity, accumulator, app)).value);
        future.run();
        return future;
    }

    @Override
    public <T> App<C, T> source(T[] array) {
        return alg.source(array);
    }

    @Override
    public <T, R> App<C, R> map(Function<T, R> f, App<C, T> app) {
        return alg.map(f, app);
    }

    @Override
    public <T, R> App<C, R> flatMap(Function<T, App<C, R>> f, App<C, T> app) {
        return alg.flatMap(f, app);
    }

    @Override
    public <T> App<C, T> filter(Predicate<T> f, App<C, T> app) {
        return alg.filter(f, app);
    }
}
