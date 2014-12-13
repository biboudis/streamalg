package streams;

import java.util.Iterator;

/**
 * Authors:
 *      Aggelos Biboudis (@biboudis)
 *      Nick Palladinos (@NickPalladinos)
 */
public interface Log<T> extends App<Log.t, T>, Iterator<T> {

    static class t {

    }

    static <A> Log<A> prj(App<Log.t, A> app) {
        return (Log<A>) app;
    }
}
