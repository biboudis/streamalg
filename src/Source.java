/**
 * Created by bibou on 10/14/14.
 */
public class Source<T> extends Stream<T> implements StreamStep {

    final T[] array;

    public Source(T[] array) {
        this.array = array;
    }

    public T[] getArray() {
        return array;
    }

    @Override
    public <Result> Result accept(StreamVisitor<Result> streamVisitor) {
        return streamVisitor.visit(this);
    }
}
