package streams;

/**
 * Created by bibou on 11/1/14.
 */
public class LogVisitor implements StreamVisitor<Stream.t> {

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
}
