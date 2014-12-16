package streams.factories;

import streams.higher.App;
import streams.higher.Push;
import streams.algebras.StreamAlg;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Authors:
 *      Aggelos Biboudis (@biboudis)
 *      Nick Palladinos (@NickPalladinos)
 */
public class PushFactory implements StreamAlg<Push.t> {

    @Override
    public <T> App<Push.t, T> source(T[] array) {
        Push<T> f = k -> {
            for(int i=0 ; i < array.length ; i++){
                k.accept(array[i]);
            }
        };
        return f;
    }

    @Override
    public <T, R> App<Push.t, R> map(Function<T, R> mapper, App<Push.t, T> app) {
        Push<R> f = k -> Push.prj(app).invoke(i -> k.accept(mapper.apply(i)));
        return f;
    }

    @Override
    public <T, R> App<Push.t, R> flatMap(Function<T, App<Push.t, R>> mapper, App<Push.t, T> app) {
        Push<R> f = k -> Push.prj(app).invoke(v -> {
            Push<R> inner = Push.prj(mapper.apply(v));
            inner.invoke(k);
        });
        return f;
    }

    @Override
    public <T> App<Push.t, T> filter(Predicate<T> predicate, App<Push.t, T> app) {
        Push<T> f = k -> Push.prj(app).invoke(i -> {
            if (predicate.test(i))
                k.accept(i);
        });
        return f;
    }
}
