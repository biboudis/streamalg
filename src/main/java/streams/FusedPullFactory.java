package streams;

import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Authors:
 *      Aggelos Biboudis (@biboudis)
 *      Nick Palladinos (@NickPalladinos)
 */
public class FusedPullFactory extends ExecPullFactory implements FusedPullAlg {

    interface FusibleFilterPull<T> extends Pull<T> {
        Predicate<T> combineWith(Predicate<T> other);
        Pull<T> getSource();
    }

    interface FusibleMapPull<T, R> extends Pull<R> {
        <M> Function<T, M> combineWith(Function<R, M> other);
        Pull<T> getSource();
    }

    @Override
    public <T> App<Pull.t, T> filter(Predicate<T> filter, App<Pull.t, T> app) {
        Pull<T> self = Pull.prj(app);

        if (self instanceof FusibleFilterPull) {
            FusibleFilterPull<T> coerced = (FusibleFilterPull<T>) self;

            Predicate<T> composed = coerced.combineWith(filter);

            return new FusibleFilterPull<T>() {
                T next = null;

                @Override
                public Pull<T> getSource() {
                    return coerced.getSource();
                }

                @Override
                public Predicate<T> combineWith(Predicate<T> other) {
                    return t -> filter.test(t) && other.test(t);
                }

                @Override
                public boolean hasNext() {
                    while (getSource().hasNext()) {
                        T current = getSource().next();

                        if (composed.test(current)) {
                            next = current;
                            return true;
                        }
                    }
                    return false;
                }

                @Override
                public T next() {
                    if (next != null || this.hasNext()) {
                        T temp = this.next;
                        this.next = null;
                        return temp;
                    }
                    throw new NoSuchElementException();
                }
            };
        } else
            return new FusibleFilterPull<T>() {
                T next = null;

                public Pull<T> getSource() {
                    return self;
                }

                @Override
                public Predicate<T> combineWith(Predicate<T> other) {
                    return t -> filter.test(t) && other.test(t);
                }

                @Override
                public boolean hasNext() {
                    Pull<T> source = getSource();
                    while (source.hasNext()) {
                        T current = source.next();

                        if (filter.test(current)) {
                            next = current;
                            return true;
                        }
                    }
                    return false;
                }

                @Override
                public T next() {
                    if (next != null || this.hasNext()) {
                        T temp = this.next;
                        this.next = null;
                        return temp;
                    }
                    throw new NoSuchElementException();
                }
            };
    }

    @Override
    public <T, R> App<Pull.t, R> map(Function<T, R> mapper, App<Pull.t, T> app) {
        Pull<T> self = Pull.prj(app);

        if (self instanceof FusibleMapPull) {
            FusibleMapPull<Object, T> coerced = (FusibleMapPull<Object, T>) self;

            Function<Object, R> composed = coerced.combineWith(mapper);

            return new FusibleMapPull<T, R>() {

                @Override
                public <M> Function<T, M> combineWith(Function<R, M> other) {
                    return x -> other.apply(composed.apply(x));
                }

                @Override
                public Pull<T> getSource() {
                    //noinspection unchecked
                    return (Pull<T>) coerced.getSource();
                }

                R next = null;

                @Override
                public boolean hasNext() {
                    Pull<T> source = getSource();
                    while (source.hasNext()) {
                        T current = source.next();
                        next = composed.apply(current);
                        return true;
                    }

                    return self.hasNext();
                }

                @Override
                public R next() {
                    if (next != null || this.hasNext()) {
                        R temp = this.next;
                        this.next = null;
                        return temp;
                    }
                    throw new NoSuchElementException();
                }
            };
        }
        else {
            return new FusibleMapPull<T, R>() {

                public Pull<T> getSource() {
                    return self;
                }

                @Override
                public <M> Function<T, M> combineWith(Function<R, M> other) {
                    return x -> other.apply(mapper.apply(x));
                }

                R next = null;

                @Override
                public boolean hasNext() {
                    while (getSource().hasNext()) {
                        T current = getSource().next();
                        next = mapper.apply(current);
                        return true;
                    }

                    return false;
                }

                @Override
                public R next() {
                    if (next != null || this.hasNext()) {
                        R temp = this.next;
                        this.next = null;
                        return temp;
                    }
                    throw new NoSuchElementException();
                }
            };
        }
    }
}
