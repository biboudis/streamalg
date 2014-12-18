package streams.higher;

import java.util.Iterator;

/**
 * Authors:
 * Aggelos Biboudis (@biboudis)
 * Nick Palladinos (@NickPalladinos)
 */
public interface Pull<T> extends App<Pull.t, T>, Iterator<T> {

    static <A> Pull<A> prj(App<Pull.t, A> app) {
        return (Pull<A>) app;
    }

    static class t {

    }
}
