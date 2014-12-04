package streams;

import java.util.function.Predicate;

/**
 * Created by bibou on 10/14/14.
 */
public class Filter<T> extends Stream<T> {

    final Predicate<T> predicate;
    final Stream<T> stream;

    public Filter(Predicate<T> predicate, Stream<T> stream) {
        this.predicate = predicate;
        this.stream = stream;
    }

    public Predicate<T> getPredicate() {
        return predicate;
    }

    public Stream<T> getStream() {
        return stream;
    }

    @Override
    <C> App<C, T> fold(StreamAlg<C> algebra) {
        return algebra.filter(predicate, stream.fold(algebra));
    }

}
