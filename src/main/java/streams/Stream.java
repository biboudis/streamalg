package streams;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by bibou on 10/14/14.
 */
public abstract class Stream<T>  {

    public <R> Stream<R> map(Function<T, R> mapper) {
        return new Map<>(mapper, this);
    }

    public Stream<T> filter(Predicate<T> predicate) {
        return new Filter<>(predicate, this);
    }

    public <R> Stream<R> flatMap(Function<T, Stream<R>> mapper) { return new FlatMap<T, R>(mapper, this);}

    long temp = 0L;
    public long count(){
        temp = 0;

        Consumer<T> k = i -> this.temp ++;

        Push.prj(this.fold(new PushFactory())).invoke(k);

        return temp;
    }

    public Iterator<T> iterator() {
        return streams.Pull.prj(this.fold(new PullFactory()));
    }

    abstract <C> App<C, T> fold(StreamAlg<C> algebra);
}
