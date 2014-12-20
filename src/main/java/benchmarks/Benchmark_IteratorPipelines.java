package benchmarks;

import org.openjdk.jmh.annotations.*;
import streams.factories.ExecPullFactory;
import streams.factories.RefCell;
import streams.higher.Pull;

import java.util.stream.Stream;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
public class Benchmark_IteratorPipelines {

    // For map, count, operations
    private static final int N = Integer.getInteger("benchmark.N", 1000);

    private Long[] v;

    @Setup
    public void setUp() {
        v = Helper.fillArray(N);
    }

    // Java 8 Benchmarks (Pull)
    @Benchmark
    public Long count_iterate_Java8Streams() {
        RefCell<Long> acc = new RefCell<>(0L);

        Stream.of(v)
                .iterator().forEachRemaining(i -> acc.value++);

        return acc.value;
    }

    @Benchmark
    public Long filter_count_iterate_Java8Streams() {
        RefCell<Long> acc = new RefCell<>(0L);

        Stream.of(v)
                .filter(x -> x % 2L == 0L)
                .iterator().forEachRemaining(i -> acc.value++);

        return acc.value;
    }

    @Benchmark
    public Long filter_map_count_iterate_Java8Streams() {
        RefCell<Long> acc = new RefCell<>(0L);

        Stream.of(v)
                .filter(x -> x % 2L == 0L)
                .map(d -> d * d)
                .iterator().forEachRemaining(i -> acc.value++);

        return acc.value;
    }

    // Pull Factory Benchmarks
    @Benchmark
    public Long count_iterate_AlgebrasPull() {
        RefCell<Long> acc = new RefCell<>(0L);

        ExecPullFactory alg = new ExecPullFactory();

        Pull.prj(alg.source(v)).forEachRemaining(i -> acc.value++);

        return acc.value;
    }

    @Benchmark
    public Long filter_count_iterate_AlgebrasPull() {
        RefCell<Long> acc = new RefCell<>(0L);

        ExecPullFactory alg = new ExecPullFactory();

        Pull.prj(alg.filter(x -> x % 2L == 0L, alg.source(v))).forEachRemaining(i -> acc.value++);

        return acc.value;
    }

    @Benchmark
    public Long filter_map_count_iterate_AlgebrasPull() {
        RefCell<Long> acc = new RefCell<>(0L);

        ExecPullFactory alg = new ExecPullFactory();

        Pull.prj(alg.map(d -> d * d, alg.filter(x -> x % 2L == 0L, alg.source(v)))).forEachRemaining(i -> acc.value++);

        return acc.value;
    }
}
