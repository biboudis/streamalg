package gadt.primitives;

import gadt.App;

/**
 * Created by bibou on 12/7/14.
 */
public class NumberHigh<T> implements App<NumberHigh.t, T> {
    public java.lang.Integer value;

    public NumberHigh(int i) {
        value = i;
    }

    public static class t {

    }

    public static <A> NumberHigh<A> prj(App<NumberHigh.t, A> app) {
        return (NumberHigh<A>) app;
    }
}
