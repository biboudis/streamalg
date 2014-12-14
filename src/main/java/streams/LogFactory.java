package streams;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by bibou on 11/1/14.
 */
public class LogFactory extends PushFactory implements StreamTerminalAlg<Id.t, Push.t> {

    @Override
    public <T, R> App<Push.t, R> flatMap(Function<T, App<Push.t, R>> mapper, App<Push.t, T> app) {
        Push<R> f = k -> Push.prj(app).invoke(i -> {
            System.out.print("flatMap : " + i.toString());
            Push<R> result = Push.prj(mapper.apply(i));
            System.out.println(" -> " + result.toString());
            result.invoke(k);
        });
        return f;
    }

    @Override
    public <T, R> App<Push.t, R> map(Function<T, R> mapper, App<Push.t, T> app) {
        Push<R> f = k -> Push.prj(app).invoke(i -> {
            System.out.print("map: " + i.toString());
            R result = mapper.apply(i);
            System.out.println(" -> " + result.toString());
            k.accept(result);
        });
        return f;
    }

    @Override
    public <T> App<Push.t, T> filter(Predicate<T> predicate, App<Push.t, T> app) {
        Push<T> f = k -> Push.prj(app).invoke(i -> {
            System.out.println("filter: " + i.toString());
            if(predicate.test(i))
                k.accept(i);
            System.out.println(" -> " + i.toString());
        });
        return f;
    }

    public <T> App<Push.t, T> log(App<Push.t, T> app) {
        Push<T> f = k -> Push.prj(app).invoke(k);

        return f;
    }
}
