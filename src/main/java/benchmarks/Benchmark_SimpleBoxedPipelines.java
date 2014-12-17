package benchmarks;

import org.openjdk.jmh.annotations.*;
import streams.factories.ExecPullFactory;
import streams.factories.ExecPushFactory;
import streams.factories.FusedPullFactory;
import streams.higher.Id;

import java.util.stream.Stream;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
public class Benchmark_SimpleBoxedPipelines {

    // For map, count, operations
    private static final int N =  Integer.getInteger("benchmark.N", 1000);
    // For cartesian product operations
    private static final int N_outer =  Integer.getInteger("benchmark.N_outer", 100);
    private static final int N_inner =  Integer.getInteger("benchmark.N_inner", 10);

    public Long[] v, v_outer, v_inner, v_forSorting_Baseline ,v_forSorting_Algebras, v_forSorting_Java8Streams;

    @Setup
    public void setUp() {
        v  = Helper.fillArray(N);
        v_outer = Helper.fillArray(N_outer);
        v_inner = Helper.fillArray(N_inner);
        v_forSorting_Baseline = Helper.fillArray(N);
        v_forSorting_Java8Streams = Helper.fillArray(N);
        v_forSorting_Algebras = Helper.fillArray(N);
    }

    @Benchmark
    public Long filter_count_Baseline() {
        Long value = 0L;
        for (int i =0 ; i < v.length ; i++) {
            if (v[i] % 2L == 0L)
                value ++;
        }
        return value;
    }

    @Benchmark
    public Long cart_Baseline() {
        Long value = 0L;
        for (int d = 0 ; d < v_outer.length ; d++) {
            for (int dp = 0 ; dp < v_inner.length ; dp++){
                value += v_outer[d] * v_inner[dp];
            }
        }
        return value;
    }

    @Benchmark
    public Long filter_count_Java8Streams() {
        Long value = java.util.stream.Stream.of(v)
                .filter(x -> x % 2L == 0L)
                .count();

        return value;
    }

    @Benchmark
    public Long cart_Java8Streams() {
        Long value = Stream.of(v_outer)
                .flatMap(d -> Stream.of(v_inner).map(dP -> dP * d))
                .reduce(0L, Long::sum);

        return value;
    }

    @Benchmark
    public Long filter_count_AlgebrasPush() {
        ExecPushFactory alg = new ExecPushFactory();

        Long value =  Id.prj(alg.count(alg.filter(x -> (long) x % 2L == 0, alg.source(v)))).value;

        return value;
    }

    @Benchmark
    public Long cart_AlgebrasPush() {
        ExecPushFactory alg = new ExecPushFactory();

        Long value =   Id.prj(alg.<Long>reduce(0L, Long::sum, alg.flatMap(x -> {
            return alg.map(y -> x * y, alg.source(v_inner));
        }, alg.source(v_outer)))).value;

        return value;
    }

    @Benchmark
    public Long filter_count_AlgebrasPull() {
        ExecPullFactory alg = new ExecPullFactory();

        Long value =  Id.prj(alg.count(alg.filter(x -> x % 2L == 0, alg.source(v)))).value;

        return value;
    }

    @Benchmark
    public Long cart_AlgebrasPull() {
        ExecPullFactory alg = new ExecPullFactory();

        Long value =  Id.prj(alg.<Long>reduce(0L, Long::sum, alg.flatMap(x -> {
            return alg.<Long, Long>map(y -> x * y, alg.source(v_inner));
        }, alg.source(v_outer)))).value;

        return value;
    }
}
