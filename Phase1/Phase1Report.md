# Project, Phase 1 - Planning #

 * [Introducing the team](#introducing-the-team)
 * [Choosing the project](#choosing-the-project)
 * [Personas](#personas)
 * [User Stories](#user-stories)
 * [MVP](#mvp)
 * [Release & Iteration Planning](#release--iteration-planning)
 * [CRC cards](#crc-cards)

## Introducing the team ##

![Group Picture](https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/blob/master/Phase1/groupPicture.jpg)

#### Russell Self ####
I am a Computer Science student working towards an undergraduate degree. I am also working full time as a network and systems administrator. I used to have other interests but now I am a shell of a person that does nothing but sit at a desk and push buttons.

#### Felix Lu ####
I am a third year Computer Science student. I hope to find a PEY job to pay off my student loans. I want to finish school as soon as possible and go into the industry.

#### Guohao Yan ####
I am a third year student specializing in Computer Science at the University of Toronto. I work part time after school at Transport Canada. I enjoy playing chess and competitive games. I envy those who can draw very well, because my drawings are terrible.

#### Frances (Fangzhou) Yu ####
I am a third year Computer Science student. I like travelling and photography. Visiting the desert was one of the most amazing experience I had.

#### Vince (Xie) Yu ####
I am a third year Computer Science student. I like playing video games, reading, and watching TV series whenever I have a chance.

#### Gabriel Luong ####
I am in my final year of study as a Computer Science and Human Biology student. Previously, I interned at Mozilla and IBM, and contributed a lot to open source projects.

#### Ou Ye ####
I am a third year Computer Science student. I am looking to go to PEY next year, but I previously worked at a small startup. I like to play boardgames during my free time.

## Choosing the project ##

For the upcoming project, we plan on implementing an Android version of the messaging application for universities. A quick poll among the team was done to decide the platform and the project. We came to an unanimous decision on the messaging application since this project presented an opportunity to produce the highest quality final product considering the amount of time required with a lot of potential opportunities to extend the original features.

## Personas ##

#### Christine the Consumer ####
![Christine the Consumer](http://christianeducatorsacademy.com/wp-content/uploads/2014/03/image010.jpg)

__Christine Campbell__ 18 years old from Toronto

Christine Campbell is a 18 year old University of Toronto student in her first year of Computer Science. She lives on residence so she has a lot of time to socialize with others on campus. Christine spends a lot of time on her phone everyday. She likes to keep up with the latest events happening on campus and the world. Christine is often busy with studying and doing assignments. She likes to read headlines on Google news to keep up with the latest world news. However, Christine gets annoyed by websites which force her to create an account in order to read content.

#### Phil the Producer ####
![Phil The Producer](http://lototees.com/media/catalog/product/cache/1/image/9df78eab33525d08d6e5fb8d27136e95/2/7/279-lototees-do-you-even-lift-bro-cool-story-mens-tank-top-t-shirt-tanktop-tshirt-sleeveless-beach-tee-500.jpg)

__Phil McRoy__ 21 years old from Toronto

Phil McRoy is a 4th year political science student and likes to share his opinions. He likes getting involved in deep discussions with others who share the same interests with him. Phil loves bodybuilding. He goes to the gym every single day of the week in hopes of becomming a professional bodybuilder. He likes arguing with people on Facebook about politics and talking to people he doesn't know while riding the bus. In addition, he likes to open up discussion about the latest fitness trends and provide his own opinions to the best fitness practise.

#### Alex The Administrator ####
![Alex The Administrator](http://i2.cdn.turner.com/money/dam/assets/130614125251-professor-retire-620xa.jpg)

__Alex Smith__ 47 years old, married with 2 children from Toronto

Alex Smith is a Bioinformatics instructor at the University of Toronto. He is happily married, and commutes daily to the St. George campus. He wants to be able to start discussions with his students about topics related to the courses that he teaches more easily. Students are usually silent when he tries asking questions during class. Alex is very passionate about the subjects he teaches and wants to ensure that students are able to understand the topics better and inform them about the latest research and developments in bioinformatics. During the weekends, Alex likes to go hikings in the woods and fishing.

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

The MVP of our messaging application for universities will present threads where users can go into and discuss the subject of the thread. Discussion threads are organized into categories. These categories are broad topics, and are not configurable by users. Threads may be posted in a single category, and may be tagged with topic attributes. These topic tags can be used to filter threads within a category. Users can create accounts, and post new threads within a category. While posting the thread, the user may choose one or several topics to associate with the thread. The user can choose from exisitng topics, or create new topic tags at the time of posting. Users can save lists of topics that they are interested in, and view threads from within those topic areas. Tagging threads with topics will help users find similar threads with the same topic easily and provide better organizations of the list of threads visible to the users at any given time. Furthermore, we plan to integrate a social platform later to allow users to communicate more easily with eachother.

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

Go to [CRC Cards](https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/blob/master/Phase1/CRC%20cards.pdf?raw=true)
