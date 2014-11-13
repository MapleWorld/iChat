# Code Reviews

The table below indicates which team member reviewed which other team member's code.

| Reviewer | Coder |
| -------- | ----- |
| nbxieyu |  FrancesYu |
| MapleWorld |  gabrielluong |
| sccdmaomao |  fdlv |
| Gabriel Luong (gabrielluong) |  Russell Self (g3rs-cdf) |
| fdlv |  MapleWorld |
| GitHub username 5 |  GitHub username 6 |
| GitHub username 6 |  GitHub username 7 |
| GitHub username 7 |  GitHub username 1 |


-----

## Reviewer : GitHub username 1

NOTE: This is an example of a good format for a code review - It is very short, yet very informative (assuming you actually include links). You don't have to follow this format, but it can give you an idea of what we want to see - Short description of highlights, and links to isses/commits/pull-requests that allow us to get more details.

 * I found several minor bugs (_links to issues_), and managed to fix a few of them (_links to commits or pull-requests_).
 * I suggested an improvement to the design of _component X_ (for more details, _link to issue_), as well as some additional useful features (_links to issues_).
 * I thought that the implementation of _component Y_ was very elegant - 
The use of _interface Z_ (_link to source file_) made it really easy to add _component Y_ to the rest of the system.

You can see all of my comments by searching for issues with the label _code-review-MyGitHubUsername_ (_link to search results_).


-----

## MapleWorld : gabrielluong

 * I found server minors bugs in server.java file and tried to fix it in [commit] (https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/commit/6638f4c70e35d6bc6be4da2b78d886b9074c3320), [commit] (https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/commit/6e92fbf140a03872643fd7d607e27880a8edadae), and [commit](https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/commit/e33fd90412810c7943dfb4ce32b30e40b1bd762c) for [#29] (https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/issues/29). There are duplicate code in the server.java file for createThread and downloadUrl, which I recommend to split it into smaller methods.
 * Another bug that I found and fixed was in [commit] (https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/commit/b95b49bc447cf42eeee6152d95ebb098a22c70cf) DAO.java file and was mentioned in [#41](https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/issues/41).
 * Regarding about [#62] (https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/issues/62), I find there are too much code in onCreate method and should be breaking down into smaller methods for readability and extensibility. 
 * I thought that the implementation of [#60](https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/issues/60) was very elegant. There is no duplicate code between the method and really compact, making it really easy to follow and understand.

-----

## Gabriel Luong (gabrielluong) : Russell Self (g3rs-cdf)

-----

## fdlv : MapleWorld

-----

## nbxieyu : FrancesYu

-----

## sccdmaomao : fdlv

-----

## Reviewer : GitHub username 7
