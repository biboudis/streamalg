package streams.fold;

import streams.algebras.StreamAlg;
import streams.higher.App;

import java.util.function.Function;

/**
 * Authors:
 * Aggelos Biboudis (@biboudis)
 * Nick Palladinos (@NickPalladinos)
 */
public class FlatMap<T, R> extends Stream<R> {

    final Function<T, Stream<R>> mapper;
    final Stream<T> stream;

    public FlatMap(Function<T, Stream<R>> mapper, Stream<T> stream) {
        this.mapper = mapper;
        this.stream = stream;
    }

    @Override
    <C> App<C, R> fold(StreamAlg<C> algebra) {
        return algebra.flatMap(x -> mapper.apply(x).fold(algebra), stream.<C>fold(algebra));
    }
}
