package streams;

/**
 * Authors:
 *      Aggelos Biboudis (@biboudis)
 *      Nick Palladinos (@NickPalladinos)
 */
public interface LogPushAlg extends StreamAlg<Push.t> {
    <T> App<Push.t, T> log(App<Push.t, T> app);
}
