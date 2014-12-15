import org.junit.Before;
import org.junit.Test;
import streams.*;

import java.util.Iterator;
import java.util.stream.*;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

/**
 * Authors:
 * Aggelos Biboudis (@biboudis)
 * Nick Palladinos (@NickPalladinos)
 */
public class TestAlgebrasFlatMapAlgebras {

    public Long[] v_outer, v_inner;
    public Long expected;

    @Before
    public void setUp() {
        v_outer = IntStream.range(0, 5).mapToObj(i -> (long) (i % 5)).toArray(Long[]::new);
        v_inner = IntStream.range(0, 5).mapToObj(i -> (long) (i % 5)).toArray(Long[]::new);
        expected = java.util.stream.Stream.of(v_outer)
                .flatMap(x -> java.util.stream.Stream.of(v_inner).map(y -> x * y))
                .count();
    }

    @Test
    public void testPull(){
        PullFactory algebra = new PullFactory();

        App<Pull.t, Long> map = algebra.map(y -> {
            System.out.println("inner: " + y);
            return (long) 10 * (long) y;
        }, algebra.source(v_inner));

        App<Pull.t, Long> flatMap = algebra.flatMap(y -> map, algebra.source(v_outer));

        Pull<Long> prj = Pull.prj(flatMap);

        prj.hasNext();

        System.out.println(prj.next());
    }

    @Test
    public void testJava8StreamsPushWithPull(){
        Iterator<Long> iterator = Stream.of(v_outer)
                .flatMap(x -> {
                    return Stream.of(v_inner).map(y -> {
                        System.out.println("inner: " + y);
                        return x * y;
                    });
                }).iterator();

        iterator.hasNext();

        System.out.println(iterator.next());
    }
}
