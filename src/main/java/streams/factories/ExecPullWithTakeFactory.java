package streams.factories;

import streams.algebras.ExecStreamAlg;
import streams.algebras.ExecTakeStreamAlg;
import streams.higher.App;
import streams.higher.Pull;

import java.util.NoSuchElementException;
import java.util.function.BinaryOperator;

/**
 * Authors:
 * Aggelos Biboudis (@biboudis)
 * Nick Palladinos (@NickPalladinos)
 */
public class ExecPullWithTakeFactory<E> extends PullFactory implements ExecTakeStreamAlg<E, Pull.t> {

    final ExecStreamAlg<E, Pull.t> alg;

    public ExecPullWithTakeFactory(ExecStreamAlg<E, Pull.t> alg) {
        this.alg = alg;
    }

    @Override
    public <T> App<Pull.t, T> take(int n, App<Pull.t, T> app) {
        Pull<T> self = Pull.prj(app);

        return new Pull<T>() {
            T next = null;
            int count = 0;

            @Override
            public boolean hasNext() {
                while (count++ < n && self.hasNext()) {
                    T current = self.next();
                    next = current;
                    return true;
                }
                return false;
            }

            @Override
            public T next() {
                if (next != null || this.hasNext()) {
                    T temp = this.next;
                    this.next = null;
                    return temp;
                }
                throw new NoSuchElementException();
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
