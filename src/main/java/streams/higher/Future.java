package streams.higher;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Authors:
 * Aggelos Biboudis (@biboudis)
 * Nick Palladinos (@NickPalladinos)
 */
public class Future<T> extends FutureTask<T> implements App<Future.t, T> {

    public Future(Callable<T> callable) {
        super(callable);
    }

    public static class t {}

    public static <A> Future<A> prj(App<Future.t, A> app) {
        return (Future<A>) app;
    }
}
