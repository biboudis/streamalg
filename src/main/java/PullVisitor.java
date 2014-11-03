import java.util.NoSuchElementException;

/**
 * Created by bibou on 11/1/14.
 */
public class PullVisitor implements StreamVisitor<Pull.t> {

    @Override
    public <T, R> App<Pull.t, R> visit(Map<T, R> map) {
        final Pull<T> inner = Pull.prj(map.getStream().accept(PullVisitor.this));

        Pull<R> f = new Pull<R>() {
            R next = null;

            @Override
            public boolean hasNext() {
                while (inner.hasNext()) {
                    T current = inner.next();
                    next = map.getMapper().apply(current);
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
    public <T, R> App<Pull.t, R> visit(FlatMap<T, R> map) {
        return null;
    }

    @Override
    public <T> App<Pull.t, T> visit(Filter<T> filter) {
        final Pull<T> inner = Pull.prj(filter.getStream().accept(PullVisitor.this));

        Pull<T> f = new Pull<T>() {
            T next = null;

            @Override
            public boolean hasNext() {
                while (inner.hasNext()) {
                    T current = inner.next();
                    if (filter.getPredicate().test(current)) {
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

    @Override
    public <T> App<Pull.t, T> visit(Source<T> source) {

        final T[] array = source.getArray();

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
}
