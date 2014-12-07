package streams;

import java.util.Iterator;

/**
 * Created by bibou on 11/1/14.
 */
public interface Log<T> extends App<Log.t, T>, Iterator<T> {

    static class t {

    }

    static <A> Log<A> prj(App<Log.t, A> app) {
        return (Log<A>) app;
    };
}
