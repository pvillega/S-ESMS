# S-ESMS

A port of [ESMS](http://eli.thegreenplace.net/programs-and-code/esms/) to Scala

# Changes from original ESMS

No logic changes have been done for this first release. This means that when generating a roaster or simulating a match, the application should behave the same as its C++ counterpart in its v.2.7.3 release.

That said, besides the obvious change of porting the source to Scala, the project is built differently, The original ESMS was a collection of executables that used plain-text files to communicate.
In contrast, this port is a library. This means that it provides methods to emulate the functionality of ESMS, but it won't work on its own, you need to call the methods from another JVM application.

Some relevant changes:

* Use of JSON: the original API used plain-text files as input/output. The API now uses direct method calls to communicate with clients, but it also provides helper methods to convert data to JSON.

* Countries: the original ESMS has a limited list of 20 countries. This has been extended to a list of all countries, and the official 2 letter country code is used to identify them

* Random values: I use the Gaussian distribution provided by Scala. It is similar to the one used by ESMS and it should give similar ranges of values. I may modify this in the future.

* Fixtures: I use a Round robin generation algorithm adapted to scala. The algorithm has the same limitations than ESMS when trying to schedule games away/home, it should work well enough for leagues with many teams, may have some issues with smaller leagues


# Dependencies

The code has the following dependencies:

* [Logback](http://logback.qos.ch/) for logging
* [ScalaTest](http://www.scalatest.org/) for unit testing
* [play-json[(https://github.com/mandubian/play-json-alone) to manage conversion between classes and JSON

Check the `Build.scala` file for the versions used.

# Thanks

Thanks to [Félix Bellanger](https://gist.github.com/Keeguon) for the list of countries, obtained from https://gist.github.com/Keeguon/2310008

# License

The original ESMS was initially created by Eli Bendersky and Igor Oks under LGPL. Thus, to follow the same spirit as the original code, this port uses LGPL v3.

Copyright of areas not covered by the LGPL belongs to Pere Villega.




