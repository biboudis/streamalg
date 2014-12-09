package gadt;

/**
 * Created by bibou on 12/8/14.
 */
public class Id<T> implements App<Id.t, T>{

    public T value;

    private Id(T value) {
        this.value = value;
    }

    static class t {}

    public static <A> Id<A> prj(App<Id.t, A> app) {
        return (Id<A>) app;
    }

    public static <A> App<Id.t, A> inj(A value) {
        return new Id<>(value);
    }
}