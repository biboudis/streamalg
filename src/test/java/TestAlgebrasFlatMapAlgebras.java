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

    private <C> Long query(StreamTerminalAlg<Id.t, C> algebra){
        Long value = Id.prj(
                algebra.<Long>count(
                        algebra.flatMap(x -> {
                            System.out.print("outer ");
                            return algebra.<Long, Long>map(y -> {
                                System.out.print("inner ");
                                return x * y;
                            }, algebra.source(v_inner));
                        },  algebra.source(v_outer)))).value;

        System.out.println();

        return value;
    }

    @Before
    public void setUp() {
        v_outer = IntStream.range(0, 5).mapToObj(i -> (long) (i % 5)).toArray(Long[]::new);
        v_inner = IntStream.range(0, 2).mapToObj(i -> (long) (i % 5)).toArray(Long[]::new);
        expected = java.util.stream.Stream.of(v_outer)
                .flatMap(x -> java.util.stream.Stream.of(v_inner).map(y -> x * y))
                .count();
    }

    @Test
    public void testPull(){
        PullFactory algebra = new PullFactory();

        Long actual = query(algebra);

        assertEquals(expected, actual);
    }

    @Test
    public void testPush(){
        PushFactory algebra = new PushFactory();

        Long actual = query(algebra);

        assertEquals(expected, actual);
    }

    @Test
    public void testJava8StreamsPush(){
        Long actual = java.util.stream.Stream.of(v_outer)
                .flatMap(x -> {
                    System.out.print("outer ");
                    return java.util.stream.Stream.of(v_inner).map(y -> {
                        System.out.print("inner ");
                        return x * y;
                    });
                })
                .count();

        assertEquals(expected, actual);
    }

    @Test
    public void testJava8StreamsPushWithPull(){
        Long actual = 0L;

        Iterator<Long> iterator = Stream.of(v_outer)
                .flatMap(x -> {
                    System.out.print("outer ");
                    return Stream.of(v_inner).map(y -> {
                        System.out.print("inner ");
                        return x * y;
                    });
                }).iterator();

        while(iterator.hasNext()){
            actual++;
            iterator.next();
        }

        assertEquals(expected, actual);
    }
}
