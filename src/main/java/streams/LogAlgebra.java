package streams;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by bibou on 11/1/14.
 */
public class LogAlgebra extends PullAlg implements StreamAlg<Pull.t> {
    @Override
    public <T, R> App<Pull.t, R> map(Function<T, R> mapper, App<Pull.t, T> app) {
        return super.map(mapper, app);
    }

    @Override
    public <T, R> App<Pull.t, R> flatMap(Function<T, App<Pull.t, R>> mapper, App<Pull.t, T> app) {
        return super.flatMap(mapper, app);
    }

    @Override
    public <T> App<Pull.t, T> filter(Predicate<T> filter, App<Pull.t, T> app) {
        return super.filter(filter, app);
    }

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

}
