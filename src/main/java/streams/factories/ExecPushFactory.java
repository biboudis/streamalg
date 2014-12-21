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
    @Override
    public <T> App<Id.t, Long> count(App<Push.t, T> app) {
        RefCell<Long> temp = new RefCell<>(0L);
        Push.prj(app).invoke(i -> temp.value++);
        return Id.inj(new Id<>(temp.value));
    }

    @Override
    public <T> App<Id.t, T> reduce(T identity, BinaryOperator<T> accumulator, App<Push.t, T> app) {
        final RefCell<T> state = new RefCell<>(identity);
        Push.prj(app).invoke(i -> state.value = accumulator.apply(state.value, i));
        return Id.inj(new Id<>(state.value));
    }
}
