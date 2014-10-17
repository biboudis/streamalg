import java.util.function.Predicate;

/**
 * Created by bibou on 10/14/14.
 */
public class Filter<T> extends Stream<T> implements StreamStep {

    final Predicate<T> predicate;
    final Stream<T> stream;

    public Filter(Predicate<T> predicate, Stream<T> stream) {
        this.predicate = predicate;
        this.stream = stream;
    }

    public Predicate<T> getPredicate() {
        return predicate;
    }

    public Stream<T> getStream() {
        return stream;
    }

    @Override
    public <Result> Result accept(StreamVisitor<Result> streamVisitor) {
        return streamVisitor.visit(this);
    }


}
