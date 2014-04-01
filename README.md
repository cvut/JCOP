# JCOP
[![Build Status](https://travis-ci.org/cvut/JCOP.svg?branch=master)](https://travis-ci.org/cvut/JCOP)
[![Coverage Status](https://coveralls.io/repos/cvut/JCOP/badge.png?branch=master)](https://coveralls.io/r/cvut/JCOP?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/cz.cvut.fit.jcop/jcop/badge.svg)](https://maven-badges.herokuapp.com/maven-central/cz.cvut.fit.jcop/jcop)

JCOP stands for Java Combinatorial Optimization Platform. Its development began in 2009 as a part of Ondřej Skalička's master's thesis. It was created with two main goals in mind. First is to make a platform which allows to create combinatorial algorithms and problems in such a way that it is possible to apply (almost) every algorithm on every problem, without any modifications. This way it should be possible to benchmark several settings of the same algorithm or even different algorithms on one problem and tell which performed better. It is not intended to be able to solve problems extremely fast, just to be able to tell which one is better among the others to be preferred and used on a problem.

The second goal of JCOP is that it is both easy to use and contains several algorithms and problems bundled with it. This is because of it will be used as part of teaching process on Faculty of Electrical Engineering on Czech Technical University in Prague by students to make their end-term projects, in which several algorithms of increasing complexity will be implemented.

JCOP comes bundled with several problems and algorithms of its own (see appendixes for description) so you can begin playing with it right away. After you have seen how it works, you can add your own algorithms and problems to it and see how better (or worse) do they fare against built-in ones.

## Links

For more information see [the JCOP website](http://jcop.sourceforge.net/).

## Maven

Released versions are available in The Central Repository. Just add this artifact to your project:

```xml
<dependency>
    <groupId>cz.cvut.fit.jcop</groupId>
    <artifactId>jcop</artifactId>
    <version>1.0.1</version>
</dependency>
```

However if you want to use the last snapshot version, you have to add the Sonatype OSS repository:

```xml
<repository>
    <id>sonatype-snapshots</id>
    <name>Sonatype repository for deploying snapshots</name>
    <url>https://oss.sonatype.org/content/repositories/snapshots</url>
    <snapshots>
        <enabled>true</enabled>
    </snapshots>
</repository>
```

## Authors

 * Ondrej Skalicka

## License

This project is licensed under [LGPL license](https://www.gnu.org/licenses/lgpl.html).
