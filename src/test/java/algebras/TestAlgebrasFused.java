package algebras;

import org.junit.Before;
import org.junit.Test;
import streams.ExecStreamAlg;
import streams.FusedPullFactory;
import streams.Id;
import streams.Pull;

import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

/**
 * Authors:
 *      Aggelos Biboudis (@biboudis)
 *      Nick Palladinos (@NickPalladinos)
 */
public class TestAlgebrasFused {

    public Long[] v;

    @Before
    public void setUp() {
        v = IntStream.range(0, 10).mapToObj(Long::new).toArray(Long[]::new);
    }

    @Test
    public void testMultipleFilterFusedPull(){

        ExecStreamAlg<Id.t, Pull.t> alg = new FusedPullFactory();

        Long actual = Id.prj(alg.count(
                alg.filter(x -> x > 7,
                alg.filter(x -> x > 6,
                alg.filter(x -> x > 5,
                alg.filter(x -> x > 4,
                alg.filter(x -> x > 3,
                alg.filter(x -> x > 2,
                alg.filter(x -> x > 1,
                alg.filter(x -> x > 0, alg.source(v))))))))))).value;

        Long expected = java.util.stream.Stream.of(v)
                .filter(x -> x > 0)
                .filter(x -> x > 1)
                .filter(x -> x > 2)
                .filter(x -> x > 3)
                .filter(x -> x > 4)
                .filter(x -> x > 5)
                .filter(x -> x > 6)
                .filter(x -> x > 7)
                .count();

        assertEquals(expected, actual);
    }

    @Test
    public void testMultipleMapFusedPull(){

        ExecStreamAlg<Id.t, Pull.t> alg = new FusedPullFactory();

        Long actual = Id.prj(alg.reduce(0L, Long::sum,
                alg.<Long, Long>map(x -> x + 1,
                alg.<Long, Long>map(x -> x + 1,
                alg.<Long, Long>map(x -> x + 1,
                alg.<Long, Long>map(x -> x + 1,
                alg.<Long, Long>map(x -> x + 1,
                alg.<Long, Long>map(x -> x + 1,
                alg.<Long, Long>map(x -> x + 1, alg.source(v)))))))))).value;

        Long expected = java.util.stream.Stream.of(v)
                .map(x -> x + 1)
                .map(x -> x + 1)
                .map(x -> x + 1)
                .map(x -> x + 1)
                .map(x -> x + 1)
                .map(x -> x + 1)
                .map(x -> x + 1)
                .reduce(0L, Long::sum);

        assertEquals(expected, actual);
    }
}
