package streams.factories;

import streams.higher.App;
import streams.algebras.FusedPullAlg;
import streams.higher.Pull;

import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Authors:
 *      Aggelos Biboudis (@biboudis)
 *      Nick Palladinos (@NickPalladinos)
 */
public class FusedPullFactory extends ExecPullFactory implements FusedPullAlg {

    //TODO: can pull up to PullFactory.
    abstract class FusibleFilterPull<T> implements Pull<T> {

        private final Pull<T> source;
        private final Predicate<T> predicate;

        public FusibleFilterPull(Pull<T> source, Predicate<T> predicate) {
            this.source = source;
            this.predicate = predicate;
        }

        T next = null;

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

    abstract class FusibleMapPull<T, R> implements Pull<R> {

        private final Pull<T> source;
        public final Function<T, R> mapper;

        public FusibleMapPull(Pull<T> source, Function<T, R> mapper) {
            this.source = source;
            this.mapper = mapper;
        }

        R next = null;
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

    @Override
    public <T> App<Pull.t, T> filter(Predicate<T> filter, App<Pull.t, T> app) {
        Pull<T> self = Pull.prj(app);

        if (self instanceof FusibleFilterPull) {
            FusibleFilterPull<T> previousSelf = (FusibleFilterPull<T>)self;
            return new FusibleFilterPull<T>(previousSelf.source, previousSelf.predicate.and(filter)) {};
        } else {
            return new FusibleFilterPull<T>(self, filter) {};
        }
    }

    @Override
    public <T, R> App<Pull.t, R> map(Function<T, R> mapper, App<Pull.t, T> app) {
        Pull<T> self = Pull.prj(app);

        if (self instanceof FusibleMapPull) {
            FusibleMapPull previousSelf = (FusibleMapPull)self;
            //TODO: introduce generic parameters
            return new FusibleMapPull<T, R>(previousSelf.source, previousSelf.mapper.andThen(mapper)) {};
        } else {
            // If self is not a fusible map, it means that some other
            // combinator was the passed app, so this is the first 
            // fusible map of the pipeline.
            return new FusibleMapPull<T, R>(self, mapper) {};
        }
    }
}
