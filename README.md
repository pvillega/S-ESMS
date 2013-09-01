# S-ESMS

A port of [ESMS](http://eli.thegreenplace.net/programs-and-code/esms/) to Scala

# Changes from original ESMS

No logic changes have been done for this first release. This means that when generating a roaster or simulating a match, the application should behave the same as its C++ counterpart in its v.2.7.3 release.

That said, besides the obvious change of porting the source to Scala, I've done some other minor modifications:

* Purpose: the original ESMS produced a set of executables that could be run to do specific tasks. Instead, this implementation is intended to be used as a Jar that provides ways to do the same tasks and it's used inside of another application.

* Use of JSON: the original API used plain-text files as input/output. The API now uses JSON to communicate with potential clients.

* Countries: the original ESMS has a limited list of 20 countries. This has been extended to a list of all countries, and the official 2 letter country code is used to identify them

* Random values: I use the Gaussian distribution provided by Scala. It is similar to the one used by ESMS and it should give similar ranges of values/ I may modify this in the future.

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




