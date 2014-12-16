package streams;

import java.util.function.BinaryOperator;

/**
 * Authors:
 * Aggelos Biboudis (@biboudis)
 * Nick Palladinos (@NickPalladinos)
 */
public class FutureFactory extends PullFactory implements ExecStreamAlg<Future.t, Pull.t> {

    private final ExecPullFactory alg;

    public FutureFactory(ExecPullFactory alg) {
        this.alg = alg;
    }

    @Override
    public <T> App<Future.t, Long> count(App<Pull.t, T> app) {
        Future<Long> future = new Future<>(() -> Id.prj(alg.count(app)).value);
        future.run();
        return future;
    }

    @Override
    public <T> App<Future.t, T> reduce(T identity, BinaryOperator<T> accumulator, App<Pull.t, T> app) {
        Future<T> future = new Future<>(() -> Id.prj(alg.reduce(identity, accumulator, app)).value);
        future.run();
        return future;
    }
}
