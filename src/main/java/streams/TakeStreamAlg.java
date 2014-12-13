package streams;

/**
 * Authors:
 *      Aggelos Biboudis (@biboudis)
 *      Nick Palladinos (@NickPalladinos)
 */
public interface TakeStreamAlg<C> extends StreamAlg<Push.t> {
    <T> App<C, T> take(int n, App<Push.t, T> app);
}
