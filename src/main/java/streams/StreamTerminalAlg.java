package streams;

import java.util.function.BinaryOperator;

/**
 * Authors:
 * Aggelos Biboudis (@biboudis)
 * Nick Palladinos (@NickPalladinos)
 */
public interface StreamTerminalAlg<E, C> extends StreamAlg<C> {
    <T> App<E, Long> count(App<C, T> app);
    <T> App<E, T>    reduce(T identity, BinaryOperator<T> accumulator, App<C, T> app);
}
