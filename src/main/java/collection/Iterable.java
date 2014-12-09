package collection;

import java.util.function.Predicate;

/**
 * Created by bibou on 12/9/14.
 */

class Iterable<Container, T> {
    App<Container, T> filter(Predicate<T> predicate) {
        throw new NoSuchMethodError();
    }

    public App<Container, T> remove(Predicate<T> predicate) {
        return filter(x -> !predicate.test(x));
    }
}
