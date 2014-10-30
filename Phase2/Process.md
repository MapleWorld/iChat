<h1>Scrum-like process report</h1>

 * [Sprint meetings](#sprint-meetings)
 * [Daily Scrum](#daily-scrum)
 * [Issue management system](#issue-management-system)
 * [Other decisions](#other-decisions)

## Sprint meetings ##
We had two sprints, one for the server side, the other for client side.  

For the first sprint, we had a planning meeting at BA3195 on Sept. 18th, our group came up with a rough plan for the first release which includes the most important features.  

We decided to use MySql for the database storage and Java Servlet to build and host the server for database communication.  

At the end of first sprint, we got 100% of what planned done, the server is fully running there is no difficulty communicating between the Android client and Java server. <br>

During the planning meeting for second sprint, we decided to work on client part of our product, we added some additional features after meeting with TA, and discussed how to setup android to connect to server.  

## Daily Scrum ##
Daily scrum meetings done online, many of them are helping each others. We faced problem setting up the android, connecting to server, and coding, meetings were helpful to resolve those problem and keep us going.
Please include date and location.
<br>

<h5> Chat history/highlights </h5>
*Russell Self*<br>
I have created a settings activity so that we can configure the server connection<br>
I already changed the registration activity to read the server connection settings from the preferences<br>
These changes are in the branch "https_custom". What other branches should I merge into it to make the changes?<br>
*Russell Self*
10/26, 4:32pm<br>
we need to change all functions in DAO.java and Server.java to use the new connection mechanism<br></p>

*Gabriel Luong* <br>
how do i get the android app to communicate with csc301.rs-ns.net:27487<br>
*Alex Yan* <br>
setting, when you open messageApp  right top corner<br>
*Russell Self*
10/28, 6:51pm <br>
in either LoginActivity or RegisterActivity you can open the settings activity<br>
I think the menu on the other screens has not been enabled yet<br>
*Ou Ye*<br>
completed view a thread, view thread by topic id, view thread by category ID. The GUI looks ugly though.<br>
*Frances Yu*<br>
Got a question, for checking the category is existing. The checking is in the server part code. before insert to the database. check the category is existing?<br>
*Ou Ye*
10/25, 5:17pm<br>
yes, it auto check if there is duplicate username when i try to register an account<br>

## Issue management system ##

We used the issue tracking system to log all of the tasks required for each sprint. For the server phase, all issues were titled with "Server - ", and for the Android client phase we titled them "Client - ". We assigned issues to the person who was currently working on the feature, and once that person had finished, he or she would put a note stating which commit the issue had been resolved with. For this phase, most of the issues we logged were for features described in the release plan. We also logged additional issues to track certain bug fixes or additional requirements that needed extra work.

## Other decisions ##
Our team decided to have Russell as our Scrum Master, he was in charge of keeping us work together and catch mini-deadlines on time
