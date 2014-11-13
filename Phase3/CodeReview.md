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
| g3rs-cdf | sccdmaomao|

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
 * In the commit to add the ability to edit replies to a thread [4a7cef49](https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/commit/4a7cef4966c666115ad0059ac6216166c1509abb#diff-de9134b500122ac4185561038fab4474R120), I noted a request for additional code comments due to how complex it was to generate the button controls in the thread view. There were some issues where code could be better formatted and could be refactored, which were addressed in commit [6a6943](https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/commit/6a69433eb1b4c3df84a9b5c08417ad2e410d494c).
 * I thought the implementation of banning users [341927](https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/commit/3419270c2789e22722c2225ecd17eccff2911b72) was fairly elegant. It effectively illustrates how to implement most features on the client side with an input on the Android client, parsing the input, sending a request to the server and handling the server response. I noted a couple of coding style issues, but was happy with the fact that there was enough code comments and good usage of variable names and function names to effectively self document the code.

-----

## fdlv : MapleWorld

 * I found a potential problem with [commit] (https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/commit/0bc503156831c6731e155dcc71e5d4bff20499b3), the timeout is set to 1 second which may cause problems with clients that have a bad connection. This problem is addressed in the commit [commit](https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/commit/0453fe57e53f534e21d363825b8da775cc202f4c).
 * I thought that the implementation of [commit](https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/commit/a60b852f17039c2c0723d44f9061517436cb26b7) was very elegant. The choices for variable names were very good and allowed the others to easily understand what was going on. There were no redundant code and the spacing allowed for readability.

## FrancesYu : nbxieyu

 * I found that the merge commit [commit](https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/commit/fc8c298378ab504ee3ad47873aa183c9a730eb2a), deleted the doDeleteThread function in ThreadsServlet.java. Fix in commit [commit](https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/commit/d067480c6d2dbf320cd64d1c59fc5ef332da8568).
 * I thought that the implementation of commit [commit](https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/commit/9acca297461cfd87f3d3e6796ebb2afbcbcdcd2f) of ThreadsServlet.java was very elegant, can easily understand. And using try and catch block to get the exceptions.
 * I found that the implementation of commit [commit](https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/commit/9acca297461cfd87f3d3e6796ebb2afbcbcdcd2f) of ThreadsDTO.java can change the deleted variable into boolean in line 753.
 * I found that the commit [commit](https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/commit/844c7aaedc617cf1be447d371458fd28f4420e51), need to remove all the lines that were comment out.

## sccdmaomao : fdlv

  * I thought some comments would help others to understand easier to commit [commit ](https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/commit/8db010c807ed2d7786dfbc4845cf08d71f077a03)  
  * The code does good job handling exceptions, any invalid responds from server would result in a proper error message returned to user. [commit] (https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/commit/c4ab6dd873a74976ca2e652e7ff1cb13a58e7d25#diff-55ac6d6f38dab242728c03f92608224eR39)
  * I found the above two commits have similar code, there could be potential benefits using abstract interface

## g3rs-cdf : sccdmaomao

Code review
EditThreadActivity, implementation by sccdmaomao

The code originally had not been implemented properly. The client was not configured to send the correct data to the server, and the actual functions for editing the thread contents were not called anywhere in the code, so it was also never used.


Original implementation in commit: 
[58107de7f389500bbd91967a488b737ae682d232](https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/commit/58107de7f389500bbd91967a488b737ae682d232)


I provided fixes to the code in EditThreadActivity.java, DAO.java and ViewThreadActivity.java.


DAO.java contains the logic for communicating with the server. I added code for sending the correct request to edit the thread, with exception handling so that if there are communication errors it will not crash the entire application like in the original implementation.


EditThreadActivity includes revisions to pass the correct data to the functions in DAO.java.


ViewThreadActivity was modified to insert a GUI button onto one of the existing screens which opens up EditThreadActivity, allowing users to access the functionality which was previously not available.


Fixes implemented in commit:
 
[1427c003f67b3341defd7cc8dfbbca89e3e23705](https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/commit/1427c003f67b3341defd7cc8dfbbca89e3e23705)

Fixes tracked with issue: [Issue link](https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/issues/77)
