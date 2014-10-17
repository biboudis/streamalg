import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by bibou on 10/14/14.
 */
public abstract class Stream<T> {
    <R> Stream<R> map(Function<T,R> mapper) {
        return new Map(mapper, this);
    };

    Stream<T> filter (Predicate<T> predicate){
        return new Filter(predicate, this);
    };

    long count(){ return 0L; };
}
