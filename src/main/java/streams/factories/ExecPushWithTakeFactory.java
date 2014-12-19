package streams.factories;

import streams.algebras.ExecStreamAlg;
import streams.algebras.ExecTakeStreamAlg;
import streams.higher.App;
import streams.higher.Push;

import java.util.function.BinaryOperator;

/**
 * Authors:
 * Aggelos Biboudis (@biboudis)
 * Nick Palladinos (@NickPalladinos)
 */
public class ExecPushWithTakeFactory<E> extends PushFactory implements ExecTakeStreamAlg<E, Push.t> {

    final ExecStreamAlg<E, Push.t> alg;

    public ExecPushWithTakeFactory(ExecStreamAlg<E, Push.t> alg) {
        this.alg = alg;
    }

    @Override
    public <T> App<Push.t, T> take(int n, App<Push.t, T> app) {
        RefCell<Long> count = new RefCell<>(0L);
        Push<T> f = k -> Push.prj(app).invoke(value -> {
            count.value++;
            if (count.value <= n) {
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
        return alg.reduce(identity, accumulator, app);
    }
}
