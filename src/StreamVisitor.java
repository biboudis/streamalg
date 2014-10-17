/**
 * Created by bibou on 10/14/14.
 */
public interface StreamVisitor<Result> {
    <T, R> Result visit(Map<T, R> map);
    <T> Result visit(Filter<T> filter);
    <T> Result visit(Source<T> source);
}
