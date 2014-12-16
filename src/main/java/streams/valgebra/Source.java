package streams.valgebra;

import streams.algebras.StreamAlg;
import streams.higher.App;

/**
 * Authors:
 *      Aggelos Biboudis (@biboudis)
 *      Nick Palladinos (@NickPalladinos)
 */
public class Source<T> extends Stream<T> {

    final T[] array;

    public Source(T[] array) {
        this.array = array;
    }

    public T[] getArray() {
        return array;
    }

    @Override
    <C> App<C, T> fold(StreamAlg<C> algebra) {
        return algebra.source(array);
    }
}
