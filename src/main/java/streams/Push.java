package streams;

import java.util.function.Consumer;

/**
 * Authors:
 *      Aggelos Biboudis (@biboudis)
 *      Nick Palladinos (@NickPalladinos)
 */
public interface Push<T> extends App<Push.t, T> {

    static class t {

    }

    static <A> Push<A> prj(App<Push.t, A> app) {
        return (Push<A>) app;
    }

    void invoke(Consumer<T> k);
}
