package streams.algebras;

import streams.higher.App;
import streams.higher.Id;
import streams.higher.Pull;

import java.util.function.UnaryOperator;

/**
 * Authors:
 *      Aggelos Biboudis (@biboudis)
 *      Nick Palladinos (@NickPalladinos)
 */

public interface ExecIterateStreamAlg<E, C> extends ExecStreamAlg<E, C> {
    <T> App<C, T> iterate(final T seed, final UnaryOperator<T> f);
}
