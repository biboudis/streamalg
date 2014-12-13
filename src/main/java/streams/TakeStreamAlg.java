package streams;

import java.util.function.Predicate;

/**
 * Created by bibou on 12/7/14.
 */
public interface TakeStreamAlg<C> extends StreamAlg<Push.t> {
    <T> App<C, T> take(int n, App<Push.t, T> app);
}
