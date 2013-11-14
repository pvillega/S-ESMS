# S-ESMS

**NOTE: The project is kind of dead due to lack of time, feel free to use and fork the code, I may retake it later on**

A port of [ESMS](http://eli.thegreenplace.net/programs-and-code/esms/) to Scala. It preserves most of the aspects of the original code, with some improvements.

WARNING: the code is not the best Scala I've written, ESMS is a C++ app broken into several

# What's ESMS

[ESMS](http://eli.thegreenplace.net/programs-and-code/esms/) is an acronym for Electronic Soccer Management Simulator. It is a program, or rather a family of programs, that allows people to run fantasy soccer management games. Think of a soccer computer game, like Championship Manager, for example. It allows you to build a team of players and manage them through a soccer league, setting the opening squad, tactics and formations, substituting players etc. You deal with player injuries, suspensions, transfers and all the other aspects of soccer team management.

ESMS gives you these abilities, but submits the whole league to your control, and not the PC’s. That means that you decide how your league looks like, what teams play (these are often completely made-up, fantasy teams, and not copies of real soccer teams), who are the managers (it can be your friends, or just people from the Internet who want to play), etc. It’s really a wholly another concept, which might be difficult to get used to unless you know what you want. The best advice I can give at this point is to look at some existing fantasy leagues online that use ESMS, to get a feel of how these things work (it is as simple as looking for “esms soccer league” or something similar in your favorite web-search engine).

# Changes from original ESMS

No logic changes have been done for this first release. This means that when generating a roaster or simulating a match, the application should behave the same as its C++ counterpart in its v.2.7.3 release.

That said, besides the obvious change of porting the source to Scala, the project is built differently, The original ESMS was a collection of executables that used plain-text files to communicate.
In contrast, this port is a library. This means that it provides methods to emulate the functionality of ESMS, but it won't work on its own, you need to call the methods from another JVM application.

Some relevant changes:

* Use of JSON: the original API used plain-text files as input/output. The API now uses direct method calls to communicate with clients, but it also provides helper methods to convert data to JSON.

* Additional classes: to facilitate implementation some new classes are added to the source, like a TeamSheet class or a Country class.

* Countries: the original ESMS has a limited list of 20 countries. This has been extended to a list of all countries, and the official 2 letter country code is used to identify them

* Random values: I use the Gaussian distribution provided by Scala. It is similar to the one used by ESMS and it should give similar ranges of values. I may modify this in the future.

* Fixtures: I use a Round robin generation algorithm adapted to scala. The algorithm has the same limitations than ESMS when trying to schedule games away/home, it should work well enough for leagues with many teams, may have some issues with smaller leagues

* TSC: the functionality to create a default team is now a method in the Roster class, which takes the Roster and a given tactic and selects a "best team" (as per TSC in ESMS 2.7.3 specifications)

# Comments

Comments have been ported to `Properties` format. Unfortunately, due to the nature of Properties files duplicate keys are not allowed.
Given that we may want multiple strings to describe an event a custom formatting is applied to this file, so it can be easily translated and it also allows for multiple values for an event.

Keys matching a single value work as normal ( key = value)

Keys matching multiple values have 2 parts:

- First part is the key along the number of options for the given key ( key = n)
- Second part is a list of the key plus a number (1 to n) that lists the multiple options for the key (key.1 = a, key.2 = b, etc)

The helper class that reads this file will load the values accordingly and return a random one to provide some variety in comments

# Config

Configuration has been unified to a single bundle `Config.properties`. The keys are similar to the ones in ESMS 2.7.3, adapted to bundle format.

The configuration has been broken into several classes, each one tackling specific aspects of the configuration. In some cases limitations in case classes and the JSON formatter mean some additional classes were required.

Config values use by default the values in ESMS 2.7.3, and a hardcoded version of these values can be obtained via the method `Config.getDefaultConfig`.

You can modify the Config bundle to generate different values, or just create your config from scratch by building the corresponding hierarchy of objects or by creating a Config object from a JSON string..

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




