/**
 * Created by bibou on 11/1/14.
 */
public interface StreamVisitor<C> {
    <T, R> App<C, R> visit(Map<T, R> map);
    <T, R> App<C, R> visit(FlatMap<T, R> map);
    <T> App<C, T> visit(Filter<T> filter);
    <T> App<C, T> visit(Source<T> source);
}