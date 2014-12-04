package streams;

import java.util.function.Consumer;

/**
 * Created by bibou on 11/1/14.
 */
public interface Push<T> extends App<Push.t, T> {

    static class t {

    }

    static <A> Push<A> prj(App<Push.t, A> app) {
        return (Push) app;
    };

    void invoke(Consumer<T> k);
}
