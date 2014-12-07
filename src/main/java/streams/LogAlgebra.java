package streams;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by bibou on 11/1/14.
 */
public class LogAlgebra implements StreamAlg<Log.t> {

    /*
    @Override
    public <T, R> App<Stream.t, R> visit(Map<T, R> map) {
        return new Map<T,R>(x -> {
            System.out.println("mapped with -> " + x.toString());
            R r = map.getMapper().apply(x);
            System.out.println("mapped returned: " + r.toString());
            return r;
        }, map.getStream());
    }

    @Override
    public <T, R> App<Stream.t, R> visit(FlatMap<T, R> map) {
        return new FlatMap<T,R>(x -> {
            System.out.println("flatMapping -> " + x.toString());
            Stream<R> r = map.getMapper().apply(x);
            System.out.println("flatMapping ended");
            return r;
        }, map.getStream());
    }

    @Override
    public <T> App<Stream.t, T> visit(Filter<T> filter) {
        return new Filter<T>(x -> {
            System.out.println("filter with-> " + x.toString());
            Boolean t = filter.getPredicate().test(x);
            System.out.println("filter returned: " + t.toString());
            return t;
        }, filter.getStream());
    }

    @Override
    public <T> App<Stream.t, T> visit(Source<T> source) {
        return source;
    }
    */

    @Override
    public <T> App<Log.t, T> source(T[] array) {
        return null;
    }

    @Override
    public <T, R> App<Log.t, R> map(Function<T, R> f, App<Log.t, T> app) {
        return null;
    }

    @Override
    public <T, R> App<Log.t, R> flatMap(Function<T, App<Log.t, R>> f, App<Log.t, T> app) {
        return null;
    }

    @Override
    public <T> App<Log.t, T> filter(Predicate<T> f, App<Log.t, T> app) {
        return null;
    }

    @Override
    public <T> long length(App<Log.t, T> app) {
        return 0;
    }
}
