package streams;

import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by bibou on 12/13/14.
 */
public class FusedPullFactory extends PullFactory implements FusedPullAlg {

    interface FusibleFilterPull<T> extends Pull<T> {
        void combineWith(Predicate<T> other);
    }

    interface FusibleMapPull<T, R> extends Pull<R> {
        <M> void combineWith(Function<R, M> other);
    }

    @Override
    public <T> App<Pull.t, T> filter(Predicate<T> filter, App<Pull.t, T> app) {
        Pull<T> self = Pull.prj(app);

        boolean isFusible = self instanceof FusibleFilterPull;

        if (isFusible) {
            FusibleFilterPull<T> coerced = (FusibleFilterPull<T>) self;

            coerced.combineWith(filter);

            return self;
        } else {
            return new FusibleFilterPull<T>() {
                T next = null;
                Predicate<T> predicate;

                @Override
                public void combineWith(Predicate<T> other) {
                    predicate = t -> filter.test(t) && other.test(t);
                }

                @Override
                public boolean hasNext() {
                    while (self.hasNext()) {
                        T current = self.next();

                        if (predicate.test(current)) {
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
    }

    @Override
    public <T, R> App<Pull.t, R> map(Function<T, R> mapper, App<Pull.t, T> app) {
        Pull<T> self = Pull.prj(app);

        if (self instanceof FusibleMapPull) {
            FusibleMapPull<Object, T> coerced = (FusibleMapPull<Object, T>) self;

            coerced.combineWith(mapper);

            return (Pull<R>) coerced;
        }
        else {
            return new FusibleMapPull<T, R>() {

                Function<T, Object> combined;

                @Override
                public <M> void combineWith(Function<R, M> other) {
                    combined = x -> other.apply((R) combined.apply(x));
                }

                R next = null;

                @Override
                public boolean hasNext() {
                    while (self.hasNext()) {
                        T current = self.next();
                        next = (R) combined.apply(current);
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
