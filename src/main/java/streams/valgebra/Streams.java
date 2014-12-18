package streams.valgebra;

/**
 * Authors:
 * Aggelos Biboudis (@biboudis)
 * Nick Palladinos (@NickPalladinos)
 */
public class Streams {
    public static <T> Stream<T> of(T[] src) {
        return new Source<>(src);
    }
}