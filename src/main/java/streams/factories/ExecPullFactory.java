package streams.factories;

import streams.higher.App;
import streams.algebras.ExecStreamAlg;
import streams.higher.Id;
import streams.higher.Pull;

import java.util.function.BinaryOperator;

/**
 * Authors:
 * Aggelos Biboudis (@biboudis)
 * Nick Palladinos (@NickPalladinos)
 */
public class ExecPullFactory extends PullFactory implements ExecStreamAlg<Id.t, Pull.t> {

    long temp = 0L;
    @Override
    public <T> App<Id.t, Long> count(App<Pull.t, T> app) {
        Pull<T> self = Pull.prj(app);

        temp = 0L;

        while(self.hasNext()){
            this.temp++;
        }
        return Id.newA(temp);
    }

    @Override
    public <T> App<Id.t, T> reduce(T identity, BinaryOperator<T> accumulator, App<Pull.t, T> app) {
        Pull<T> self = Pull.prj(app);

        T state = identity;

        while(self.hasNext()){
            state = accumulator.apply(state, self.next());
        }

        return Id.newA(state);
    }
}
