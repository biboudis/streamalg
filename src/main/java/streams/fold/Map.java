package streams.fold;

import streams.algebras.StreamAlg;
import streams.higher.App;

import java.util.function.Function;

/**
 * Authors:
 * Aggelos Biboudis (@biboudis)
 * Nick Palladinos (@NickPalladinos)
 */
public class Map<T, R> extends Stream<R> {

    final Function<T, R> mapper;
    final Stream<T> stream;

    public Map(Function<T, R> mapper, Stream<T> stream) {
        this.mapper = mapper;
        this.stream = stream;
    }

    @Override
    <C> App<C, R> fold(StreamAlg<C> algebra) {
        return algebra.map(mapper, stream.fold(algebra));
    }
}
