import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import streams.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class TestAlgebrasLog {

    public Long[] v, v_inner;

    @Before
    public void setUp() {
        v = IntStream.range(0, 10).mapToObj(i -> new Long(i % 5)).toArray(Long[]::new);
        v_inner = IntStream.range(0, 5).mapToObj(i -> new Long(i % 5)).toArray(Long[]::new);
    }

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }

    @Test
    public void testLog(){
        LogPushAlgebra alg = new LogAlgebra();

        alg.<Long>length(alg.map(x -> x ^ 2, alg.log(alg.source(v))));

        assertEquals(
                "map: 0 -> 2\n" +
                "map: 1 -> 3\n" +
                "map: 2 -> 0\n" +
                "map: 3 -> 1\n" +
                "map: 4 -> 6\n" +
                "map: 0 -> 2\n" +
                "map: 1 -> 3\n" +
                "map: 2 -> 0\n" +
                "map: 3 -> 1\n" +
                "map: 4 -> 6\n",
                outContent.toString());
    }
}