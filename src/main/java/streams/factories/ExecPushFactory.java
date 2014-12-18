package streams.factories;

import streams.algebras.ExecStreamAlg;
import streams.higher.App;
import streams.higher.Id;
import streams.higher.Push;

import java.util.function.BinaryOperator;

/**
 * Authors:
 * Aggelos Biboudis (@biboudis)
 * Nick Palladinos (@NickPalladinos)
 */
public class ExecPushFactory extends PushFactory implements ExecStreamAlg<Id.t, Push.t> {

    long temp = 0L;

    @Override
    public <T> App<Id.t, Long> count(App<Push.t, T> app) {
        temp = 0L;
        Push.prj(app).invoke(i -> this.temp++);
        return Id.newA(temp);
    }

    @Override
    public <T> App<Id.t, T> reduce(T identity, BinaryOperator<T> accumulator, App<Push.t, T> app) {
        final RefCell<T> state = new RefCell<>(identity);
        Push.prj(app).invoke(i -> state.value = accumulator.apply(state.value, i));
        return Id.newA(state.value);
    }
}
