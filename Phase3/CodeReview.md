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

## nbxieyu : FrancesYu
* I suggested an improvement to the Client-admin create category(#65)(47feee433290f52080f76485e2e9d39f00734def)
  I think the the comment is necessary for  readability.

* I thought that  the implement of Client-admin delete thread(#63)(3ffa8ad4aeeea6a5b5562d8fa4842c10eff00095) is 
helpful for the other cilent implements like Client-admin delete replies and etc. 

* I thought that the implement of Server-admin can delete a thread(#45)(42f3333ccfacfbe462385e80775d48b6e835c17a)  
was really elegant, it is really easy to follow and understand. Moreover, this implement is helpful for the 
implement Server-admin can delete reply(#46).


-----

## MapleWorld : gabrielluong

 * I found server minors bugs in server.java file and tried to fix it in commit 6638f4c70e35d6bc6be4da2b78d886b9074c3320, 6e92fbf140a03872643fd7d607e27880a8edadae, and e33fd90412810c7943dfb4ce32b30e40b1bd762c for #29 And commit bc3100ea0564f2c7d216cba6665a2ebb9b91ec29, also, there are duplicate code in the server.java file for createThread and downloadUrl, which I recommend to split it into smaller methods.
 * Another bug that I found fixed was in commit bc3100ea0564f2c7d216cba6665a2ebb9b91ec29 DAO.java file and was mentioned in #41.
 * Regarding about #62 ViewTopicListByCategoryActivity.java, I find there are too much code in onCreate method and should be break down into smaller methods for readability and extensibility.
 * I thought that the implementation of #60 (_6083920d3433eafbf7f10b4714927d235d9529e7_) was very elegant. There is no duplicate code between the method and really compact, making it really easy to follow and understand.

-----

## Gabriel Luong (gabrielluong) : Russell Self (g3rs-cdf)

 * In Russell's commit for adding admin delete reply to the client [e64032aa](https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/commit/e64032aa2cfaadbca0ce5f00c5ff39f1f771dc1e#diff-4b5d83ff78209d2d18f1831d3c540886R142), I noted several code styling nits, which could be improved. In addition, there were some instances where better logging can be done to improve the user experience by providing meaningful success or error messages. I thought that the implementation was fairly simple and easy to understand ignoring the issues described before. I learned a bit more about the JSONObject API with Russell's usage of getBoolean() and optString(), which fetches the data type associated with a key. Some of these issues were adjusted in [ecb428](https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/commit/ecb42871d6b4747c6ffbbc0644a01b5782da233d)

-----

## fdlv : MapleWorld
 * I suggest that the indentation in (_8e76ef374329090a0780f5fafaea067375c156fe_) be improved, some lines are up to 140 columns which is not very easy to read. Created issue #78 to address this issue.
 * I found a potential problem with (_0bc503156831c6731e155dcc71e5d4bff20499b3_), the timeout is set to 1 second which may cause problems with clients that have a bad connection. This problem is addressed in the commit (_0453fe57e53f534e21d363825b8da775cc202f4c_).
 * I thought that the implementation of (_a60b852f17039c2c0723d44f9061517436cb26b7_) was very elegant. The choices for variable names were very good and allowed the others to easily understand what was going on. There were no redundant code and the spacing allowed for readability.

## Reviewer : GitHub username 5

-----

## Reviewer : GitHub username 6

-----

## Reviewer : GitHub username 7
