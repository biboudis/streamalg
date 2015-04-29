#!/bin/bash

if [ -z "$1" ]
then
     FILTER=".*"
else
     FILTER=$1
fi

echo "Building JMH benchmarks über-jar"
mvn -DskipTests -q clean package

echo "Running benchmarks"
java -Xmx3g -Xms3g -XX:-TieredCompilation\
     -Dbenchmark.N=10000000 -Dbenchmark.F=1000000 -Dbenchmark.N_small=10 -Dbenchmark.N_limit=100000\
     -jar target/microbenchmarks.jar -wi 10 -i 10 -f 3 -gc true -tu ms -rf csv ${FILTER}
