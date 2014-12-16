package algebras;

import base.TestBase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import streams.*;

import java.util.stream.IntStream;

public class TestAlgebrasLog extends TestBase {

    public Long[] v, v_inner;
    Long expected;

    @Before
    public void setUp() {
        v = IntStream.range(0, 10).mapToObj(Long::new).toArray(Long[]::new);
        v_inner = IntStream.range(0, 5).mapToObj(Long::new).toArray(Long[]::new);
        expected = java.util.stream.Stream.of(v)
                .map(x -> x + 2)
                .reduce(0L, Long::sum);
    }

    @Test
    public void testPushLog(){
        ExecStreamAlg<Id.t, Push.t> alg = new LogFactory<>(new ExecPushFactory());

        Long actual = Id.prj(alg.reduce(0L, Long::sum, alg.map(x -> x + 2, alg.source(v)))).value;

        Assert.assertEquals(
                "Starting Execution: \n" +
                        "map: 0 -> 2\n" +
                        "map: 1 -> 3\n" +
                        "map: 2 -> 4\n" +
                        "map: 3 -> 5\n" +
                        "map: 4 -> 6\n" +
                        "map: 5 -> 7\n" +
                        "map: 6 -> 8\n" +
                        "map: 7 -> 9\n" +
                        "map: 8 -> 10\n" +
                        "map: 9 -> 11\n",
                outContent.toString());

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testPullLog(){
        ExecStreamAlg<Id.t, Pull.t> alg = new LogFactory<>(new ExecPullFactory());

        Long actual = Id.prj(alg.reduce(0L, Long::sum, alg.map(x -> x + 2, alg.source(v)))).value;

        Assert.assertEquals(
                "Starting Execution: \n" +
                        "map: 0 -> 2\n" +
                        "map: 1 -> 3\n" +
                        "map: 2 -> 4\n" +
                        "map: 3 -> 5\n" +
                        "map: 4 -> 6\n" +
                        "map: 5 -> 7\n" +
                        "map: 6 -> 8\n" +
                        "map: 7 -> 9\n" +
                        "map: 8 -> 10\n" +
                        "map: 9 -> 11\n",
                outContent.toString());

        Assert.assertEquals(expected, actual);
    }
}