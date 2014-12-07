package streams;

import java.util.function.Consumer;

/**
 * Created by bibou on 11/1/14.
 */
public interface PushWithTake<T> extends App<PushWithTake.t, T> {

    static class t {

    }

    static <A> PushWithTake<A> prj(App<PushWithTake.t, A> app) {
        return (PushWithTake<A>) app;
    };

    void invoke(Consumer<T> k);
}
