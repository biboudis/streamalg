package streams.factories;

import streams.algebras.FusedPullAlg;
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
public class ExecFusedPullFactory extends ExecPullFactory implements FusedPullAlg {

    @Override
    public <T> App<Pull.t, T> filter(Predicate<T> filter, App<Pull.t, T> app) {
        Pull<T> self = Pull.prj(app);

        if (self instanceof FusibleFilterPull) {
            return ((FuseFilterPull<T>) self).compose(filter);
        } else {
            return new FusibleFilterPull<T>(self, filter);
        }
    }

    @Override
    public <T, R> App<Pull.t, R> map(Function<T, R> mapper, App<Pull.t, T> app) {
        Pull<T> self = Pull.prj(app);

        if (self instanceof FuseMapPull) {
            return ((FuseMapPull<T>) self).compose(mapper);
        } else {
            return new FusibleMapPull<>(self, mapper);
        }
    }

    interface FuseMapPull<T> extends Pull<T> {
        <S> Pull<S> compose(Function<T, S> other);
    }

    interface FuseFilterPull<T> extends Pull<T> {
        Pull<T> compose(Predicate<T> other);
    }

    class FusibleFilterPull<T> implements FuseFilterPull<T> {

        private final Pull<T> source;
        private final Predicate<T> predicate;
        T next = null;

        public FusibleFilterPull(Pull<T> source, Predicate<T> predicate) {
            this.source = source;
            this.predicate = predicate;
        }

        @Override
        public FusibleFilterPull<T> compose(Predicate<T> other) {
            return new FusibleFilterPull<>(source, other.and(predicate));
        }

        @Override
        public boolean hasNext() {
            while (source.hasNext()) {
                T current = source.next();
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
    }

    class FusibleMapPull<T, R> implements FuseMapPull<R> {

        private final Pull<T> source;
        private final Function<T, R> mapper;
        private R next = null;

        public FusibleMapPull(Pull<T> source, Function<T, R> mapper) {
            this.source = source;
            this.mapper = mapper;
        }

        @Override
        public <S> FuseMapPull<S> compose(Function<R, S> other) {
            return new FusibleMapPull<>(source, other.compose(mapper));
        }

        @Override
        public boolean hasNext() {
            while (source.hasNext()) {
                T current = source.next();
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
    }
}
