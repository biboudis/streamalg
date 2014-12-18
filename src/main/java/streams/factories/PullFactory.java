package streams.factories;

import streams.algebras.StreamAlg;
import streams.higher.App;
import streams.higher.Pull;

import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Authors:
 * Aggelos Biboudis (@biboudis)
 * Nick Palladinos (@NickPalladinos)
 */
public class PullFactory implements StreamAlg<Pull.t> {

    @Override
    public <T> App<Pull.t, T> source(T[] array) {
        Pull<T> f = new Pull<T>() {

            final int size = array.length;
            int cursor = 0;

            @Override
            public boolean hasNext() {
                return cursor != size;
            }

            @Override
            public T next() {
                if (cursor >= size)
                    throw new NoSuchElementException();

                return array[cursor++];
            }
        };

        return f;
    }

    @Override
    public <T, R> App<Pull.t, R> map(Function<T, R> mapper, App<Pull.t, T> app) {
        Pull<T> self = Pull.prj(app);

        Pull<R> f = new Pull<R>() {
            R next = null;

            @Override
            public boolean hasNext() {
                while (self.hasNext()) {
                    T current = self.next();
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

        return f;
    }

    @Override
    public <T, R> App<Pull.t, R> flatMap(Function<T, App<Pull.t, R>> mapper, App<Pull.t, T> app) {
        Pull<T> self = Pull.prj(app);
        Pull<R> f = new Pull<R>() {
            Pull<R> current = null;

            R next = null;

            @Override
            public boolean hasNext() {

                while (true) {
                    while (current != null && current.hasNext()) {
                        next = current.next();
                        return true;
                    }

                    if (self.hasNext())
                        this.current = Pull.prj(mapper.apply(self.next()));
                    else
                        return false;
                }

            }

            @Override
            public R next() {
                if (current != null || this.hasNext()) {
                    R temp = this.next;
                    this.next = null;
                    return temp;
                }
                throw new NoSuchElementException();
            }
        };

        return f;
    }

    @Override
    public <T> App<Pull.t, T> filter(Predicate<T> filter, App<Pull.t, T> app) {
        final Pull<T> self = Pull.prj(app);

        Pull<T> f = new Pull<T>() {
            T next = null;

            @Override
            public boolean hasNext() {
                while (self.hasNext()) {
                    T current = self.next();
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
        return f;
    }
}
