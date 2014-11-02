import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by bibou on 11/1/14.
 */
public class PushVisitor implements StreamVisitor<Push.t> {

    @Override
    public <T, R> App<Push.t, R> visit(Map<T, R> map) {
        Push<R> f = k -> Push
                .prj(map.getStream().accept(this))
                .invoke(i -> k.accept(map.getMapper().apply(i)));
        return f;
    }

    @Override
    public <T> App<Push.t, T> visit(Filter<T> filter) {
        Push<T> f = k -> Push
                .prj(filter.getStream().accept(this))
                .invoke(i -> {
                    if (filter.getPredicate().test(i))
                        k.accept(i);
                    });
        return f;
    }

    @Override
    public <T> App<Push.t, T> visit(Source<T> source) {
        Push<T> f = k -> {
            for(int i=0 ; i < source.array.length ; i++){
                k.accept(source.array[i]);
            }
        };
        return f;
    }
}
