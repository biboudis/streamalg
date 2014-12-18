package streams.factories;

import streams.algebras.ExecIterateStreamAlg;
import streams.algebras.ExecStreamAlg;
import streams.higher.App;
import streams.higher.Pull;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

/**
 * Authors:
 * Aggelos Biboudis (@biboudis)
 * Nick Palladinos (@NickPalladinos)
 */
public class ExecPullWithIterateFactory<E> extends PullFactory implements ExecIterateStreamAlg<E, Pull.t> {
    final ExecStreamAlg<E, Pull.t> alg;

    long count = 0L;

    public ExecPullWithIterateFactory(ExecStreamAlg<E, Pull.t> alg) {
        this.alg = alg;
    }

    @Override
    public <T> App<Pull.t, T> iterate(T seed, UnaryOperator<T> f) {
        return new Pull<T>() {
            T t = null;

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public T next() {
                return t = t == null ? seed : f.apply(t);
            }
        };
    }

    @Override
    public <T> App<E, Long> count(App<Pull.t, T> app) {
        return alg.count(app);
    }

    @Override
    public <T> App<E, T> reduce(T identity, BinaryOperator<T> accumulator, App<Pull.t, T> app) {
        return alg.reduce(identity, accumulator, app);
    }
}
