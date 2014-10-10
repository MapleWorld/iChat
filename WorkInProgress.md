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

## Choosing the project ##

For the upcoming project, we plan on implementing an Android version of the messaging application for universities. A quick poll among the team was done to decide the platform and the project. We came to an unanimous decision on the messaging application since this project presented an opportunity to produce the highest quality final product considering the amount of time required with a lot of potential opportunities to extend the original features.

## Personas ##

Craig the Consumer: Craig is 18 years old, and has not yet developed opinions which are worth sharing. Therefore, he only wants to read the topics posted by other people. Craig gets annoyed by websites which force him to create an account in order to read content. He just wants to browse topics about the latest dope parties that are happening in the hood.

Philip the Producer likes to share his opinions. He likes getting involved in deep discussions with others who share the same interests with him. He likes arguing with people on Facebook about politics and talking to people he doesn't know while riding the bus.

The Administrator: Alex is an instructor at a university. He wants to be able to start discussions with his students about topics related to the courses that he teaches. He also would like the ability to moderate the discussions so that they don't become an impediment to the learning process.

## User Stories ##

Guide to numbers: (priority/effort)
Priority: 1 = high, 2 = medium, 3 = low
Effort: 1 = high, 2 = medium, 3 = low
 * As a Consumer, I want to create an account (1/2)
 * As a Consumer, I want to be able to log in with my account (1/2)
 * As a Consumer, I want to specify topics that interest me, and see threads that match those topics. (1/1)
 * As a Consumer, I want to browse all threads within a broad category. (1/2)
 * As a Consumer, I want to receive notifications when new threads are posted in the topics I like. (3/2)
 * As a Consumer, I want to save my preferences about topics that I like, and have them available when I log in. (2/2)
 * As a Consumer, I want to be able to keep track of whether I have already viewed a thread, and whether there have been updates since I last viewed it. (2/1)
 * As a Consumer, I want to be able to browse the content without creating an account. (2/3)
 * As a Producer, I want to create threads and have them associated with topics of my choosing.So my threads will be found with 
  appropriate topics easily by other Consumers. (1/1)
 * As a Producer, I want to respond to threads which have been started by other users, so I can be involved in the discussion and 
   communicate with other users. (2/2)
 * As a Producer, I want to create new topics if necessary to match the content of my threads.So the threads can be found by 
   searching the new topics. (1/1)
 * As a Producer, I want to be notified when replies are contributed to the threads I create.So i can see the new replies and 
   respond to new replies on time. (3/2)
 * As a Producer, I want to be able to edit posts which I have made to threads. (2/2) 
 * As an Administrator, I want to post administrative bulletins which are automatically visible to all users, regardless of       topic preference.So all users can get important information on time. (3/2)
 * As an Administrator, I want to remove topics, threads and users which violate the rules.So i can make sure that there is no
   improper posts and replies. (2/2)
 * As an Administrator, I want to be able to create new categories for organizing threads (distinct from topics).So i can 
   administrate all threads by using appropriate categories. (3/2)
 * As an Administrator, I want to be able to edit user accounts, so I can manage users. (2/2)
 * As The Social One, I want to send private messages directly to other users. (3/1)
 * As The Social One, I want to maintain a list of contacts. (3/2)
 * As The Social One, I want to have a PM inbox that I can sort based on which user contacted me. (3/1)
 * As The Social One, I want to review messages which I have sent to other users (3/1)

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
 * As a Consumer, I want to create an account and log in.
 * As a Consumer, I want to browse all topics within a broad category.
 * As a Producer, I want to create threads and have them associated with topics of my choosing.
 * As a Producer, I want to create new topics if necessary to match the content of my threads.

The User Stories  that we are planning to implement in the first iteration are:
 * As a Consumer, I want to create an account and log in.
 * As a Producer, I want to create threads and have them associated with topics of my choosing.

3 user stories that we decided to exclude from our first release:
  * Social One component
  * Administrator component
  * As a Consumer, I want to save my preferences about topics that I like, and have them available when I log in.

All these components and user stories would requires additional tables in the database, and logic to maintain the features. Furthermore, these features would go beyond the features required for the MVP.

## CRC Cards ##

Personas
    Administrative users can post bulletins that are visible to all users

    Non-administrative users can also post bulletins, but they are not forced to be visible

    Other users can choose to filter out topics that they are not interested in
User stories
    The Consumer
        The Consumer wants to read information about topics that are interesting. (High)The Consumer wants to be able to list topics of interest, and automatically be provided with discussion threads which match those topics. (low). The Consumer would like to be able to browse topics, and view discussion threads within those topics (high). The consumer would like to receive notifications when new topics are available which match his preferences (Low). The consumer would like to be able to register an account, and save preferences/subscriptions about discussion topics (high). Track whether a user has viewed a thread, highlight it if there are new posts since it was last viewed (low). The consumer may also want to browser public discussion anonymously without creating an account (low).

        The Producer
        The Producer wants to write posts online, and  be able to apply attributes to those posts to have them associated with topics (high). The Producer wants other users to be able to respond to posts which have been written (medium), and those users should be able to discover them by specifying a topic which the original post has been tagged with (high). The producer would like to be notified when replies are received to his posts (low).

        The Administrator
        The administrator would like to post administrative bulletins which are automatically highly visible to all users, regardless of which topics they are interested in (medium). The administrator enjoys censorship and would like to be able to remove topics that are determined to be inappropriate (medium). The administrator would like to be able to create and destroy topics (low) (there will default topics provided). The administrator would like to be able to disable and ban accounts (medium).

    The Social One - this user would like to communicate directly with other users in private discussions, one-to-one messaging (low). The Social One would also like to maintain a contacts list (low).
