import java.util.Iterator;
import java.util.function.Consumer;
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

    long temp = 0L;

    long count(){
        temp = 0;

        Consumer<T> k = i -> this.temp ++;

        Push.prj(this.accept(new PushVisitor())).invoke(k);

        return temp;
    };

    Iterator<T> iterator() {
        return Pull.prj(this.accept(new PullVisitor()));
    }

    abstract <C> App<C, T> accept(StreamVisitor<C> visitor);

}
