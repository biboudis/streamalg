package streams;

/**
 * Authors:
 *      Aggelos Biboudis (@biboudis)
 *      Nick Palladinos (@NickPalladinos)
 */

public interface TakeStreamAlg<C> extends StreamAlg<C> {
    <T> App<C, T> take(int n, App<C, T> app);
}
