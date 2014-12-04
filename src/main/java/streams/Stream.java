package streams;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by bibou on 10/14/14.
 */
public abstract class Stream<T> implements App<Stream.t, T> {

    static class t {

    }

    static <A> Stream<A> prj(App<t, A> app) {
        return (Stream) app;
    };

    long temp = 0L;

    <R> Stream<R> map(Function<T, R> mapper) {
        return new Map(mapper, this);
    };

    Stream<T> filter(Predicate<T> predicate) {
        return new Filter(predicate, this);
    }

    <R> Stream<Stream<R>> flatMap(Function<T, Stream<R>> mapper) { return new FlatMap(mapper, this);}

    long count(){
        temp = 0;

        Consumer<T> k = i -> this.temp ++;

        Push.prj(this.fold(new PushAlg())).invoke(k);

        return temp;
    }

//    streams.Stream<T> log(){
//        return streams.Stream.prj(this.fold(new streams.LogVisitor()));
//    }

//    Iterator<T> iterator() {
//        return streams.Pull.prj(this.fold(new streams.PullAlg()));
//    }

    abstract <C> App<C, T> fold(StreamAlg<C> algebra);
}
