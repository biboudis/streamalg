package streams;

import java.util.function.Function;

/**
 * Created by bibou on 10/14/14.
 */
public class FlatMap<T,R> extends Stream<R> {

    final Function<T, Stream<R>> mapper;
    final Stream<T> stream;

    public FlatMap(Function<T, Stream<R>> mapper, Stream<T> stream) {
        this.mapper = mapper;
        this.stream = stream;
    }

    public Function<T, Stream<R>> getMapper() {
        return mapper;
    }

    public Stream<T> getStream() {
        return stream;
    }

    @Override
    <C> App<C, R> fold(StreamAlg<C> algebra) {
        return algebra.flatMap(x -> mapper.apply(x).fold(algebra), stream.<C>fold(algebra));
    }
}
