package streams.fold;

import streams.algebras.StreamAlg;
import streams.factories.PullFactory;
import streams.factories.PushFactory;
import streams.factories.RefCell;
import streams.higher.App;
import streams.higher.Pull;
import streams.higher.Push;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Authors:
 * Aggelos Biboudis (@biboudis)
 * Nick Palladinos (@NickPalladinos)
 */
public abstract class Stream<T> {

    public static <T> Stream<T> of(T[] src) {
        return new Source<>(src);
    }

    public <R> Stream<R> map(Function<T, R> mapper) {
        return new Map<>(mapper, this);
    }

    public Stream<T> filter(Predicate<T> predicate) {
        return new Filter<>(predicate, this);
    }

    public <R> Stream<R> flatMap(Function<T, Stream<R>> mapper) {
        return new FlatMap<>(mapper, this);
    }

    public long count() {
        RefCell<Long> temp = new RefCell<>(0L);

        Consumer<T> k = i -> temp.value++;

        Push.prj(this.fold(new PushFactory())).invoke(k);

        return temp.value;
    }

    public Iterator<T> iterator() {
        return Pull.prj(this.fold(new PullFactory()));
    }

    abstract <C> App<C, T> fold(StreamAlg<C> algebra);
}
