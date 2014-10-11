# Project, Phase 1 - Planning #

 * [Introducing the team](#introducing-the-team)
 * [Choosing the project](#choosing-the-project)
 * [Personas](#personas)
 * [User Stories](#user-stories)
 * [MVP](#mvp)
 * [Release & Iteration Planning](#release--iteration-planning)
 * [CRC cards](#crc-cards)

## Introducing the team ##

#### Russell Self ####
I am a Computer Science student working towards an undergraduate degree. I am also working full time as a network and systems administrator. I used to have other interests but now I am a shell of a person that does nothing but sit at a desk and push buttons.  

#### Felix Lu ####
I am a third year Computer Science student. I hope to find a PEY job to pay off my student loans. I want to finish school as soon as possible and go into the industry.

#### Guohao Yan ####
I am a third year student specializing in Computer Science at the University of Toronto. I work part time after school at Transport Canada. I enjoy playing chess and competitive games. I envy those who can draw very well, because my drawings are terrible.

#### Frances (Fangzhou) Yu ####
I am a third year Computer Science student. I like travelling and photography. Visiting the desert was one of the most amazing experience I had.

#### Vince (Xie Yu) ####
I am a third year Computer Science student. I like playing video games, reading, and watching TV series whenever I have a chance.

#### Gabriel Luong ####
I am in my final year of study as a Computer Science and Human Biology student. Previously, I interned at Mozilla and IBM, and contributed a lot to open source projects.

#### Ou Ye ####
I am a third year Computer Science student. I am looking to go to PEY next year, and currently work at a small startup. In my free time, I like to play boardgames.

## Choosing the project ##

For the upcoming project, we plan on implementing an Android version of the messaging application for universities. A quick poll among the team was done to decide the platform and the project. We came to an unanimous decision on the messaging application since this project presented an opportunity to produce the highest quality final product considering the amount of time required with a lot of potential opportunities to extend the original features.

## Personas ##

#### Christine the Consumer ####
Craig Campbell is a 18 year old University of Toronto student in his first year of Computer Science. Craig has not yet developed opinions which are worth sharing. Therefore, he only wants to read the topics posted by other people. Craig gets annoyed by websites which force him to create an account in order to read content. He just wants to browse topics about the latest dope parties that are happening in the hood.

#### Phil the Producer ####
Phil McRoy likes to share his opinions. He likes getting involved in deep discussions with others who share the same interests with him. He likes arguing with people on Facebook about politics and talking to people he doesn't know while riding the bus.

#### Alex The Administrator ####
Alex Smith is a Bioinformatics instructor at the University of Toronto. He is happily married, and commutes daily to the St. George campus. He wants to be able to start discussions with his students about topics related to the courses that he teaches. He also would like the ability to moderate the discussions so that they don't become an impediment to the learning process.

## User Stories ##

__Guide to numbers:__ (Priority/Effort)
 * Priority:
  * 1 = High
  * 2 = Medium
  * 3 = Low
 * Effort:
  * 1 = High
  * 2 = Medium
  * 3 = Low


 * As a Consumer, I want to create an account, so that I can keep my own profile. (1/2)
 * As a Consumer, I want to log into my account, so that I can use my own preferences and settings. (1/2)
 * As a Consumer, I want to view threads that matches my specified topics, so that I can filter through the topics. (1/1)
 * As a Consumer, I want to browse all threads within a broad category, so that I can filter through the categories. (1/2)
 * As a Consumer, I want to receive notifications when new threads are posted in the topics I like, so that I can stay up to date with the threads. (3/2)
 * As a Consumer, I want to save my preferences about topics that I like, so that I can easily browse through the topics I am interested in. (2/2)
 * As a Consumer, I want to be able to keep track of whether I have already viewed a thread, so that I know I already viewed the thread. (2/1)
 * As a Consumer, I want to be able to keep track of whether there have been updates to a thread since I last viewed it, so that I can see the new messages. (2/1)
 * As a Consumer, I want to be able to browse the content without creating an account, so that I can easily access the information. (2/3)
 * As a Producer, I want to create threads, so that I can start new discussions. (1/1)
 * As a Producer, I want to associate a thread with a topic, so that the thread will be grouped along with the other threads with the same topic. (1/1)
 * As a Producer, I want to be able to respond to threads, so that I can be involved in the discussion and communicate with other users. (2/2)
 * As a Producer, I want to create new topics if necessary to match the content of my threads, so that the thread can be associated with the most appropriate topic. (1/1)
 * As a Producer, I want to be notified when there are new replies to a thread I created, so that I can see the new replies and respond to new replies on time. (3/2)
 * As a Producer, I want to be able to edit posts which I have made to threads, so that I can fix any typos, and make any clarifications if necessary. (2/2)
 * As an Administrator, I want to post administrative bulletins which are automatically visible to all users, regardless of topic preference, so that all users can get important information on time. (3/2)
 * As an Administrator, I want to remove inappropriate topics, so that I can make sure that there are no improper topics. (2/2)
 * As an Administrator, I want to remove inappropriate threads, so that I can make sure there are no improper threads. (2/2)
 * As an Administrator, I want to remove inappropriate posts, so that I can make sure that there are no offending posts and replies. (2/2)
 * As an Administrator, I want to ban users which violate the rules, so that I can make sure that offending users cannot use the system. (2/2)
 * As an Administrator, I want to be able to create new categories, so that I can organize all threads using the appropriate categories. (3/2)
 * As an Administrator, I want to be able to edit user accounts, so that I can manage users. (2/2)
 * As The Social One, I want to send private messages (PM) directly to other users, so that I can communicate with other users. (3/1)
 * As The Social One, I want to maintain a list of contacts, so that I can communicate with other users. (3/2)
 * As The Social One, I want to browse a PM inbox, so that I can see the list of messages people sent me. (3/1)
 * As The Social One, I want to review messages which I have sent to other users, so that I can have a history of my conversation with other users. (3/1)

## MVP ##
Categories - Set by administrator, a thread may only be in one category
Topics - created by users, a thread may be in several topics
Threads - created by users, replied to by users. Category/topics set by original poster
Users can browse threads within a category, filter by topic
User can register an account and save preferences about favourite topics
Users can view a list of favourite topics and view threads from them
This product will organize discussion threads into categories. The categories are broad, and are not configurable by users. Threads may be posted in a single category, and may be tagged with topic attributes. These topic tags can be used to filter posts within a category. Users can create accounts, and post new threads within a category; while posting the thread, the user may choose one or several topics to tag the thread with. The user can choose from exisitng topics, or create new tags at the time of posting. Users can save lists of topics that they are interested in, and view posts from within those topic areas.

-MVP-
Our goal for the MVP is to implement this messaging tool on the Android platform with user-friendly features. It will be like a mobile forum with a multi-level filter to search for threads that interest the user. We will allow users to post their own threads at the MVP stage, and allowing them to tag their posts with topics they think are appropriate. It will help other users find similar posts within a few clicks. Also, logged in users can save their preferences on their area of interests, that means users can more easily find posts which are interesting to them.

## Release & Iteration Planning ##
User stories that we are planning to implement for our first release:
 * As a Consumer, I want to create an account, so that I can keep my own profile.
 * As a Consumer, I want to log into my account, so that I can use my own preferences and settings.
 * As a Consumer, I want to view threads that matches my specified topics, so that I can filter through the topics.
 * As a Consumer, I want to browse all threads within a broad category, so that I can filter through the categories.
 * As a Producer, I want to create threads, so that I can start new discussions.
 * As a Producer, I want to associate a thread with a topic, so that the thread will be grouped along with the other threads with the same topic.
 * As a Producer, I want to create new topics if necessary to match the content of my threads, so that the thread can be associated with the most appropriate topic.

The user stories that we are planning to implement in the first iteration are:
 * As a Consumer, I want to create an account, so that I can keep my own profile.
 * As a Consumer, I want to log into my account, so that I can use my own preferences and settings.
 * As a Producer, I want to create threads, so that I can start new discussions.
 * As a Producer, I want to associate a thread with a topic, so that the thread will be grouped along with the other threads with the same topic.

3 user stories that we decided to exclude from our first release:
  * Social One component
  * Administrator component
  * As a Consumer, I want to save my preferences about topics that I like, so that I can easily browse through the topics I am interested in.

All these components and user stories would requires additional tables in the database, and logic to maintain the features. Furthermore, these features would go beyond the features required for the MVP.

## CRC Cards ##
