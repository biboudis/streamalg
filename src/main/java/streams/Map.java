package streams;

import java.util.function.Function;

/**
 * Authors:
 *      Aggelos Biboudis (@biboudis)
 *      Nick Palladinos (@NickPalladinos)
 */
public class Map<T,R> extends Stream<R> {

    final Function<T,R> mapper;
    final Stream<T> stream;

    public Map(Function<T, R> mapper, Stream<T> stream) {
        this.mapper = mapper;
        this.stream = stream;
    }

    public Function<T, R> getMapper() {
        return mapper;
    }

    public Stream<T> getStream() {
        return stream;
    }

    @Override
    <C> App<C, R> fold(StreamAlg<C> algebra) {
        return algebra.map(mapper, stream.fold(algebra));
    }
}
