package collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

/**
 * Created by bibou on 12/9/14.
 */
public class List<T> extends Iterable<List.t, T> implements App<List.t, T>{

    java.util.List<T> internal = new ArrayList<>();

    static class t {}

    public List() {}

    public List(T[] array) {
        this.internal = Arrays.asList(array);
    }

    public void add(T t) {
        internal.add(t);
    }

    public T get(int index) {
        return internal.get(index);
    }

    public int count() {
        return internal.size();
    }

    @Override
    App<t, T> filter(Predicate<T> predicate) {
        T[] results = (T[]) internal.stream().filter(predicate).toArray();

        return new List<>(results);
    }

    public static <A> List<A> prj(App<List.t, A> app) {
        return (List<A>) app;
    }
}