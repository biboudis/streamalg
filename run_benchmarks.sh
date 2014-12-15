#!/bin/bash

echo "Building JMH benchmarks über-jar"
mvn -q clean package -Dskiptests

echo "Running benchmarks"
java -Xmx2g -Xms2g -XX:-TieredCompilation -Dbenchmark.N=1000000 -Dbenchmark.F=3000000 -Dbenchmark.N_outer=1000000 -Dbenchmark.N_inner=10 -jar target/microbenchmarks.jar -wi 5 -i 5 -f 1 -gc true -tu ms ".*"
