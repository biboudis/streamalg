/**
 * Created by bibou on 10/14/14.
 */
public interface StreamStep {
    <Result> Result accept(StreamVisitor<Result> streamVisitor);
}
