package gadt.primitives;

import gadt.App;

/**
 * Created by bibou on 12/7/14.
 */
public class BooleanHigh<T> implements App<BooleanHigh.t, T> {
    public java.lang.Boolean value;

    public BooleanHigh(boolean i) {
        value = i;
    }

    public static class t {

    }

    public static <A> BooleanHigh prj(App<BooleanHigh.t, A> app) {
        return (BooleanHigh<A>) app;
    };
}
