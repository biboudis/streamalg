package streams;

import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Authors:
 *      Aggelos Biboudis (@biboudis)
 *      Nick Palladinos (@NickPalladinos)
 */
public class ExecPushWithTakeFactory<E> extends PushFactory implements ExecTakeStreamAlg<E, Push.t> {

    final ExecStreamAlg<E, Push.t> alg;

    long count = 0L;

    public ExecPushWithTakeFactory(ExecStreamAlg<E, Push.t> alg) {
        this.alg = alg;
    }

    @Override
    public <T> App<Push.t, T> take(int n, App<Push.t, T> app) {
        count = 0L;
        Push<T> f = k -> Push.prj(app).invoke(value -> {
            this.count++;
            if (count <= n) {
                k.accept(value);
            }
            // regarding the missing else part: no shortcut, this is just an example.
        });
        return f;
    }

    @Override
    public <T> App<E, Long> count(App<Push.t, T> app) {
        return alg.count(app);
    }

    @Override
    public <T> App<E, T> reduce(T identity, BinaryOperator<T> accumulator, App<Push.t, T> app) {
        return alg.reduce(identity,accumulator, app);
    }
}
