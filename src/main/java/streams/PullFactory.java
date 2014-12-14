package streams;

import java.util.NoSuchElementException;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

/**
* Created by bibou on 11/1/14.
*/
public class PullFactory implements StreamTerminalAlg<Id.t, Pull.t> {

    @Override
    public <T> App<Pull.t, T> source(T[] array) {
        Pull<T> f = new Pull<T>() {

            int cursor = 0;
            final int size = array.length;

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

                while(true) {
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

    long temp = 0L;
    @Override
    public <T> App<Id.t, Long> count(App<Pull.t, T> app) {
        Pull<T> self = Pull.prj(app);

        temp = 0L;

        while(self.hasNext()){
            this.temp++;
        }
        return Id.newA(temp);
    }

    @Override
    public <T> App<Id.t, T> reduce(T identity, BinaryOperator<T> accumulator, App<Pull.t, T> app) {
        Pull<T> self = Pull.prj(app);

        T state = identity;

        while(self.hasNext()){
            state = accumulator.apply(state, self.next());
        }

        return Id.newA(state);
    }
}
