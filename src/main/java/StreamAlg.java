import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by bibou on 11/1/14.
 */
public interface StreamAlg<C> {
    <T> App<C, T> source(T[] array);
    <T, R> App<C, R> map(Function<T, R> f, App<C, T> app);
    <T, R> App<C, R> flatMap(Function<T, App<C, R>> f, App<C, T> app);
    <T> App<C, T> filter(Predicate<T> f, App<C, T> app);
    <T> long length(App<C, T> app);
}

