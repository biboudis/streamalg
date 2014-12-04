## Defunctionalizing Streams for Java

## Motivation
In this project our motivation is to provide a new library design for Stream APIs that enable extensibility of Stream
libraries in 2 dimensions: open to both new combinators and new traversal methods.

## References
### 1. Push vs Pull
* [Defunctionalizing Push Arrays](http://www.cse.chalmers.se/~joels/writing/defuncEmb.pdf)
* [The Anatomy of a Loop](http://www.ccs.neu.edu/home/shivers/papers/loop.pdf)

### 2. External vs Internal Visitors
* [The Visitor Pattern as a Reusable‚ Generic‚ Type−Safe Component](http://www.cs.ox.ac.uk/jeremy.gibbons/publications/visitor.pdf)

### 3. GADTs in Java
* [Generalized Algebraic Data Types and Object-Oriented Programming](http://research.microsoft.com/apps/pubs/default.aspx?id=64040)
* [Lightweight Higher-Kinded Polymorphism](https://ocamllabs.github.io/higher/lightweight-higher-kinded-polymorphism.pdf) ([extended](http://www.lpw25.net/flops2014.pdf))
* [Generics of a Higher Kind](https://lirias.kuleuven.be/bitstream/123456789/186940/4/tcpoly.pdf)
* [Adding Type Constructor Parameterization to Java](http://www.jot.fm/issues/issue_2008_06/article2.pdf)

### 4. Object Algebras
* [Extensibility for the Masses](https://www.cs.utexas.edu/~wcook/Drafts/2012/ecoop2012.pdf)

## Streams-Zoo
* [Clash of the Lambdas](http://biboudis.github.io/clashofthelambdas/)
* [Nessos/Streams](https://github.com/nessos/Streams) in F#
* [lightweight-streams](https://github.com/biboudis/lightweight-streams) in Java (reimplemented with lambdas only)
* [sml-streams](https://github.com/biboudis/sml-streams) in SML/MLton
* [Iterator combinators](http://mlton.org/ForLoops) in SML/MLton

## Dependencies
* [JMH](http://openjdk.java.net/projects/code-tools/jmh/)
* [junit](http://junit.org/)
* [Guava](https://code.google.com/p/guava-libraries/)
