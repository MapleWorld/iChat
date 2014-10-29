<h1>Scrum-like process report (not finished)</h1>

 * [Sprint meetings](#Sprint-meetings)
 * [Daily Scrum](#Daily-Scrum)
 * [Issue management system](#Issue-management-system)
 * [Other decisions](#Other-decisions)

## Sprint meetings ##
We had two sprints, one for the server side, the other for client side.  

For the first sprint, we had a planning meeting at BA3195 on Sept. 18th, our group came up with a rough plan for the first release which includes the most important features.  

We decided to use MySql for the database storage and Java Servlet to build and host the server for database communication.  

At the end of first sprint, we got 100% of what planned done, the server is fully running there is no difficulty communicating between the Android client and Java server. <br>

During the planning meeting for second sprint, we decided to work on client part of our product, we added some additional features after meeting with TA, and discussed how to setup android to connect to server.  

For the review/retrospective meeting, we would like to see some numbers and/or interesting statistics.
For example: Percentage of planned work that was actually completed, distribution of completed work among team members, percentage of planned work completed categorized by priority, etc.
The point is to evaluate the estimations that you've made during the planning meeting.  

## Daily Scrum ##
Daily scrum meetings done online, many of them are helping each others. We faced problem setting up the android, connecting to server, and coding, meetings were helpful to resolve those problem and keep us going.
Please include date and location.
<br>

<h5> Chat history/highlights </h5>
<p>Russell Self<br>
I have created a settings activity so that we can configure the server connection<br>
I already changed the registration activity to read the server connection settings from the preferences<br>
These changes are in the branch "https_custom". What other branches should I merge into it to make the changes?<br>
Russell Self<br>
10/26, 4:32pm<br>
Russell Self<br>
we need to change all functions in DAO.java and Server.java to use the new connection mechanism<br></p>
Description of how you used GitHub issue management system (e.g. naming conventions, labels, team's conventions, etc.)
Description of any other major decisions/conventions you may have made/used during the process, and why.
For example: Did you have a "scrum master"? Why? Do you think it was the right decision?

## Issue management system ##
<h4> Use of Github Issue Management System </h4>

We used the issue tracking system to log all of the tasks required for each sprint. For the server phase, all issues were titled with "Server - ", and for the Android client phase we titled them "Client - ". We assigned issues to the person who was currently working on the feature, and once that person had finished, he or she would put a note stating which commit the issue had been resolved with. For this phase, most of the issues we logged were for features described in the release plan. We also logged additional issues to track certain bug fixes or additional requirements that needed extra work.

## Other decisions ##
