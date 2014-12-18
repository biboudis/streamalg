package streams.algebras;

import streams.higher.App;

import java.util.function.BinaryOperator;

/**
 * Authors:
 * Aggelos Biboudis (@biboudis)
 * Nick Palladinos (@NickPalladinos)
 */
public interface ExecStreamAlg<E, C> extends StreamAlg<C> {
    <T> App<E, Long> count(App<C, T> app);

    <T> App<E, T> reduce(T identity, BinaryOperator<T> accumulator, App<C, T> app);
}
