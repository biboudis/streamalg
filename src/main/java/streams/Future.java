package streams;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

/**
 * Authors:
 * Aggelos Biboudis (@biboudis)
 * Nick Palladinos (@NickPalladinos)
 */
public class Future<T> extends FutureTask implements App<Future.t, T> {

    public Future(Callable callable) {
        super(callable);
    }

    public Future(Runnable runnable, Object result) {
        super(runnable, result);
    }

    static class t {}

    public static <A> Future<A> prj(App<Future.t, A> app) {
        return (Future<A>) app;
    }
}
