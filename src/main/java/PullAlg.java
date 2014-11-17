import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;

/**
* Created by bibou on 11/1/14.
*/
public class PullAlg implements StreamAlg<Pull.t> {
    @Override
    public <T> App<Pull.t, T> source(T[] array) {
        Pull<T> f = new Pull<T>() {

            int cursor = 0;
            int size = array.length;

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
        Pull<T> inner = Pull.prj(app);

        Pull<R> f = new Pull<R>() {
            R next = null;

            @Override
            public boolean hasNext() {
                while (inner.hasNext()) {
                    T current = inner.next();
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
//        Pull<T> outer = Pull.prj(app);
//
//        Pull<R> f = new Pull<R>() {
//            App<Pull.t, R> next = null;
//
//            @Override
//            public boolean hasNext() {
//                while (outer.hasNext()) {
//                    T current = outer.next();
//                    next = mapper.apply(current);
//                    return true;
//                }
//
//                return false;
//            }
//
//            @Override
//            public R next() {
//                if (next != null || this.hasNext()) {
//                    App<Pull.t, R>  temp = Pull.prj(this.next);
//                    this.next = null;
//                    return temp;
//                }
//                throw new NoSuchElementException();
//            }
//        };
//
//        return f;
        return null;
    }

    @Override
    public <T> App<Pull.t, T> filter(Predicate<T> filter, App<Pull.t, T> app) {
        final Pull<T> inner = Pull.prj(app);

        Pull<T> f = new Pull<T>() {
            T next = null;

            @Override
            public boolean hasNext() {
                while (inner.hasNext()) {
                    T current = inner.next();
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
    public <T> long length(App<Pull.t, T> app) {
        Pull<T> inner = Pull.prj(app);

        temp = 0L;

        while(inner.hasNext()){
            this.temp++;
        }

        return temp;
    }
}
