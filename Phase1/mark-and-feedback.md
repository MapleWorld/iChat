# Feedback

* Excellent Personas
* You are missing description about the Social One in Personas but you are using it in user stories.
* Very concise and clear MVP description. However, I do not think it has any selling point. For example, I think what you are describing can be accomplished with existing tools such as google groups, Piazza, Twitter, etc.
* Did not play out scenarios with CRC cards
* User class does not have to depend on DAO
* I think RequestProcessor class is implicit and can be left out.
* I liked your AccessControlLayer. I think you can make it work without it depending on User/Category/Topic/Thread/Post, etc. My suggestion is to have a separate class called AccessControlPolicy which contains access control settings for each actions. The AccessControlPolicy can be loaded from a config file or can be set by admin users dynamically. In this way, you can reduce unnecessary dependencies between classes. I would also make AccessControlLayer another DAO with simple one or two responsibilities. (grant/revoke/check access control)
* I suggest dividing up DAO into multiple DAO in the future.

# Mark

* MVP (13/15)
* CRC (10/15)
* All other parts, full mark.
* Total: 68/75
