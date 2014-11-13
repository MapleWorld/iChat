# Code Reviews

The table below indicates which team member reviewed which other team member's code.

| Reviewer | Coder |
| -------- | ----- |
| nbxieyu |  FrancesYu |
| MapleWorld |  gabrielluong |
| sccdmaomao |  fdlv |
| Gabriel Luong (gabrielluong) |  Russell Self (g3rs-cdf) |
| fdlv |  MapleWorld |
| FrancesYu |  nbxieyu |
| GitHub username 6 |  GitHub username 7 |
| GitHub username 7 |  GitHub username 1 |


-----

## nbxieyu : FrancesYu
* I suggested an improvement to the Client-admin create category[#65](https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/commit/47feee433290f52080f76485e2e9d39f00734def)
  I think the the comment is necessary for  readability.

* I thought that  the implement of Client-admin delete thread[#63](https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/commit/3ffa8ad4aeeea6a5b5562d8fa4842c10eff00095) is
helpful for the other cilent implements like Client-admin delete replies and etc.

* I thought that the implement of Server-admin can delete a thread[#45](https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/commit/42f3333ccfacfbe462385e80775d48b6e835c17a)
was really elegant, it is really easy to follow and understand. Moreover, this implement is helpful for the
implement Server-admin can delete reply[#46](https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/issues/46).


-----

## MapleWorld : gabrielluong

* I found server minors bugs in server.java file and tried to fix it in [commit] (https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/commit/6638f4c70e35d6bc6be4da2b78d886b9074c3320), [commit] (https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/commit/6e92fbf140a03872643fd7d607e27880a8edadae), and [commit](https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/commit/e33fd90412810c7943dfb4ce32b30e40b1bd762c) for [#29] (https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/issues/29). There are duplicate code in the server.java file for createThread and downloadUrl, which I recommend to split it into smaller methods.
* Another bug that I found and was later mentioned in [#41](https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/issues/41), also, I noticed there is a problem with the login [#79] (https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/issues/79).
* Regarding about [#62] (https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/issues/62), I find there are too much code in onCreate method and should be breaking down into smaller methods for readability and extensibility.
* I thought that the implementation of [#60](https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/issues/60) was very elegant. There is no duplicate code between the method and really compact, making it really easy to follow and understand.

-----

## Gabriel Luong (gabrielluong) : Russell Self (g3rs-cdf)

 * In Russell's commit for adding admin delete reply to the client [e64032aa](https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/commit/e64032aa2cfaadbca0ce5f00c5ff39f1f771dc1e#diff-4b5d83ff78209d2d18f1831d3c540886R142), I noted several code styling nits, which could be improved. In addition, there were some instances where better logging can be done to improve the user experience by providing meaningful success or error messages. I thought that the implementation was fairly simple and easy to understand ignoring the issues described before. I learned a bit more about the JSONObject API with Russell's usage of getBoolean() and optString(), which fetches the data type associated with a key. Some of these issues were fixed in [ecb428](https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/commit/ecb42871d6b4747c6ffbbc0644a01b5782da233d)

-----

## fdlv : MapleWorld

 * I found a potential problem with [commit] (https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/commit/0bc503156831c6731e155dcc71e5d4bff20499b3), the timeout is set to 1 second which may cause problems with clients that have a bad connection. This problem is addressed in the commit [commit](https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/commit/0453fe57e53f534e21d363825b8da775cc202f4c).
 * I thought that the implementation of [commit](https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/commit/a60b852f17039c2c0723d44f9061517436cb26b7) was very elegant. The choices for variable names were very good and allowed the others to easily understand what was going on. There were no redundant code and the spacing allowed for readability.

## FrancesYu : nbxieyu

 * I found that the merge commit [commit](https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/commit/fc8c298378ab504ee3ad47873aa183c9a730eb2a), deleted the doDeleteThread function in ThreadsServlet.java. Fix in commit [commit](https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/commit/d067480c6d2dbf320cd64d1c59fc5ef332da8568).
 * I thought that the implementation of commit [commit](https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/commit/9acca297461cfd87f3d3e6796ebb2afbcbcdcd2f) of ThreadsServlet.java was very elegant, can easily understand. And using try and catch block to get the exceptions.
 * I found that the implementation of commit [commit](https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/commit/9acca297461cfd87f3d3e6796ebb2afbcbcdcd2f) of ThreadsDTO.java can change the deleted variable into boolean in line 753.
 * I found that the commit [commit](https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/commit/844c7aaedc617cf1be447d371458fd28f4420e51), need to remove all the lines that were comment out.

-----

## Reviewer : GitHub username 6

-----

## Reviewer : GitHub username 7
