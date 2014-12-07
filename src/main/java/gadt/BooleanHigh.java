package gadt;

/**
 * Created by bibou on 12/7/14.
 */
public class BooleanHigh<T> implements App<BooleanHigh, T> {
    public java.lang.Boolean value;

    public BooleanHigh(boolean i) {
        value = i;
    }

    public static class t {

    }

    static <A> BooleanHigh prj(App<BooleanHigh.t, A> app) {
        return (BooleanHigh) app;
    };
}
