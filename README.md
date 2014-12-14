## Defunctionalizing Streams for Java
[![Build Status](https://magnum.travis-ci.com/biboudis/defunctionalizing-streams.svg?token=EYsxboxiFVSqpFARwkTX&branch=master)](https://magnum.travis-ci.com/biboudis/defunctionalizing-streams)

Paper (WIP) at [https://github.com/biboudis/defunctionalizing-streams-paper](https://github.com/biboudis/defunctionalizing-streams-paper)

## Motivation
In this project our motivation is to provide a new library design for Stream APIs that enable extensibility of Stream
libraries in 2 dimensions: open to both new combinators and new traversal methods.

## References
### 1. Push vs Pull
* [Defunctionalizing Push Arrays](http://www.cse.chalmers.se/~joels/writing/defuncEmb.pdf)
* [The Anatomy of a Loop](http://www.ccs.neu.edu/home/shivers/papers/loop.pdf)

### 2. External vs Internal Visitors
* [The Visitor Pattern as a Reusable‚ Generic‚ Type−Safe Component](http://www.cs.ox.ac.uk/jeremy.gibbons/publications/visitor.pdf)

### 3. GADTs
* [Haskell/GADT](http://en.wikibooks.org/wiki/Haskell/GADT)
* [Generalized Algebraic Data Types and Object-Oriented Programming](http://research.microsoft.com/apps/pubs/default.aspx?id=64040)
* [Lightweight Higher-Kinded Polymorphism](https://ocamllabs.github.io/higher/lightweight-higher-kinded-polymorphism.pdf) ([extended](http://www.lpw25.net/flops2014.pdf))
* [Generics of a Higher Kind](https://lirias.kuleuven.be/bitstream/123456789/186940/4/tcpoly.pdf)
* [Adding Type Constructor Parameterization to Java](http://www.jot.fm/issues/issue_2008_06/article2.pdf) ([code](http://lampwww.epfl.ch/~cremet/FGJ-omega/index.html))
* [highj-Haskell-style type classes in Java](https://code.google.com/p/highj/)

### 4. Object Algebras
* [Extensibility for the Masses](https://www.cs.utexas.edu/~wcook/Drafts/2012/ecoop2012.pdf)
* [Feature-Oriented Programming with Object Algebras](http://www.cs.utexas.edu/~wcook/Drafts/2012/FOPwOA.pdf)

## Running benchmarks

```shell
# run unit tests
mvn -q test

# build benchmarks über-jar
mvn clean package -Dskiptests

# run benchmarks
java -Xmx2g -Xms2g -XX:-TieredCompilation -Dbenchmark.N=1000000 -Dbenchmark.F=3000000 -Dbenchmark.N_outer=1000000 -Dbenchmark.N_inner=10 -jar target/microbenchmarks.jar -wi 5 -i 5 -f 1 -gc true -tu ms ".*"
```

## Streams-Zoo
* [Clash of the Lambdas](http://biboudis.github.io/clashofthelambdas/)
* [Nessos/Streams](https://github.com/nessos/Streams) in F#
* [lightweight-streams](https://github.com/biboudis/lightweight-streams) in Java (reimplemented with lambdas only)
* [sml-streams](https://github.com/biboudis/sml-streams) in SML/MLton
* [scala-streams](https://github.com/biboudis/scala-streams) in Scala
* [Iterator combinators](http://mlton.org/ForLoops) in SML/MLton

## Dependencies
* [JMH](http://openjdk.java.net/projects/code-tools/jmh/)
* [junit](http://junit.org/)
* [Guava](https://code.google.com/p/guava-libraries/)
