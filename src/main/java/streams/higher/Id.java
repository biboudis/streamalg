package streams.higher;

/**
 * Authors:
 * Aggelos Biboudis (@biboudis)
 * Nick Palladinos (@NickPalladinos)
 */
public class Id<T> implements App<Id.t, T> {

    public final T value;

    public Id(T value) {
        this.value = value;
    }

    public static <A> Id<A> prj(App<Id.t, A> app) {
        return (Id<A>) app;
    }

    public static <A> App<Id.t, A> inj(Id<A> app) {
        return (App<Id.t, A>) app;
    }

    public static class t {
    }
}