package algebras;

import base.TestBase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import streams.factories.PullFactory;
import streams.higher.App;
import streams.higher.Pull;

import java.util.Iterator;
import java.util.stream.*;
import java.util.stream.Stream;

/**
 * Authors:
 * Aggelos Biboudis (@biboudis)
 * Nick Palladinos (@NickPalladinos)
 */
public class TestAlgebrasFlatMapAlgebras extends TestBase {

    public Long[] v_outer, v_inner;

    @Before
    public void setUp() {
        v_outer = IntStream.range(0, 5).mapToObj(i -> (long) (i % 5)).toArray(Long[]::new);
        v_inner = IntStream.range(0, 5).mapToObj(i -> (long) (i % 5)).toArray(Long[]::new);
    }

    @Test
    public void testPull(){
        PullFactory algebra = new PullFactory();

        App<Pull.t, Long> map = algebra.map(y -> {
            System.out.println("inner: " + y);
            return (long) 10 * y;
        }, algebra.source(v_inner));

        App<Pull.t, Long> flatMap = algebra.flatMap(y -> map, algebra.source(v_outer));

        Pull<Long> prj = Pull.prj(flatMap);

        prj.hasNext();

        System.out.println(prj.next());

        Assert.assertEquals(
                "inner: 0\n" +
                        "0\n",
                outContent.toString());
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

        Assert.assertEquals(
                "inner: 0\n" +
                        "inner: 1\n" +
                        "inner: 2\n" +
                        "inner: 3\n" +
                        "inner: 4\n" +
                        "0\n",
                outContent.toString());
    }
}
