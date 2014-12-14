package streams;

/**
 * Authors:
 *      Aggelos Biboudis (@biboudis)
 *      Nick Palladinos (@NickPalladinos)
 */

public interface TakeStreamAlg<C> extends StreamTerminalAlg<Id.t, C> {
    <T> App<C, T> take(int n, App<Push.t, T> app);
}
