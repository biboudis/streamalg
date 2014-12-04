import java.util.Iterator;

/**
 * Created by bibou on 11/1/14.
 */
public interface Pull<T> extends App<Pull.t, T>, Iterator<T> {

    static class t {

    }

    static <A> Pull<A> prj(App<Pull.t, A> app) {
        return (Pull) app;
    };
}
