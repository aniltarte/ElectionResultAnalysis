ElectionResultAnalysis
======================

It is a solution to the [workshop exercise](https://github.com/aniltarte/ElectionResultComputing) at [Expertalk meetup event](http://www.meetup.com/expertalks/events/179983882/ "Java 8")

Domain problem
-----------------------------
* The exercise is to analyse the 'Loksabha election 2014' results using Java 8 features.
* For simplicity, considered only Maharashtra state.

Project setup instructions
---------------------------
1. Clone the project, better option is to fork it
    * git clone https://github.com/aniltarte/ElectionResultAnalysis.git
2. Run gradle build command from project root directory
    * gradle clean build
    * gradle cleanIdea idea
3. Open the project in Intellij Idea, that's it.

Project Structure
-------------------------
It has below two modules   

1. *data* - This module provides the raw election data, it reads the csv file and creates list of `at.election.Result`, which is accessed as `at.election.ElectionResult.instance.getResult()`
2. *analyzer* - Analyzer uses data module to get ElectionResult and analyzes it to produces meaningful statistics, check out below classes
   * *at.election.ElectionResultStatistics.java* (present in test folder), this is how a Analyzer module's client look like.
   * *at.election.ResultAnalyzer.java* (present in main folder), it is responsible to produce domain specific api's on top of raw election result, to see the result statistics.

User Stories (Domain problem in more details)
----------------------------------------------
1. List the election result
2. List the election result for Pune
   * List it down!
   * It is more clear if I see it sorted by Votes in descending order
   * Lets remove the noise, I am interested in top 5
   * Can I see just the runner up?
3. Lets do similar to the Mumbai's result (same steps as 2)
4. List number of candidates per party
5. List winners per constituency
6. Time to see, party wise performance (party wise won seats), yay!
7. Total votes in constituencies
8. Runner ups show, party wise runner up seats
9. Party wise vote share
10. Close Fights
11. One Sided Contest


Notes
------------
1. Install Java 8, latest version
2. Setup  Intellij Community edition 13.1.3 or latest, its free to use and reason is, it has the best support for Java 8 as of now.
3. You can very easily setup the project in intellij idea without any build tool, as it is not using any third party dependency, it just uses java 8 jdk and junit.
4. If you plan to use gradle, make sure it is latest(1.11 or above), it is optional because of point 3.

