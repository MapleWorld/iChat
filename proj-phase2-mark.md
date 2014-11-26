# Phase 2 Marks

__Total:__ 68 out of 75.

## Live Demo

| Component | Mark | Out of |
| --------- | ---- | ------ |
| Logical flow of the demo        | 7 | 7 |
| Answering questions clearly     | 7 | 7 |
| Overall clarity of presentation | 6 | 6 |
| __Total__                       | 20 | 20 |

## Product.md

| Component | Mark | Out of |
| --------- | ---- | ------ |
| The report is focused on the product (i.e. __what__)             | 7 | 7 |
| Report quality - Clear writing & proper grammar                  | 8 | 8 |
| __Total__                                                        | 15 | 15 |


## Process.md

| Component | Mark | Out of |
| --------- | ---- | ------ |
| Review & planning meetings highlights                                           | 3 | 3 |
| Highlights are presented with an argument explaining the team's reasoning       | 1 | 3 |
| Improving the presentation using links to issues/source/commits and/or diagrams | 0 | 2 |
| Including interesting statistics or charts (e.g. burndown charts)               | 0 | 2 |
| Description of "daily" meetings                                                 | 2 | 2 |
| Description of how the team used GitHub's issue management system               | 2 | 2 |
| Clear writing & proper grammar                                                  | 1 | 1 |
| __Total__ | 9 | 15 |


## Overall TA Evaluation

| Component | Mark | Out of |
| --------- | ---- | ------ |
| Delivered everything that was promised in the approved release plan | 10 | 10 |
| Overall product quality                                             | 9 | 9  |
| Code quality                                                        | 5 | 6  |
| __Total__ | 24 | 25 |

Comments on code
- I think DTO for server side actually functions as DAO. As far as I know, DTO should be very simple class consisting of
getter/setter to reduce expensive network communication.
- I would actually break apart server-side DAO into two parts. Instead of having separate DAO methods for each REST API call,
I would have simple DAO interface that returns and accepts Objects such as User, Message, Topic etc. I would have separate logic for
displaying these objects in JSON or whatever format. Also, to update DB (e.g. banning user), I would change these objects and then call update method of DAO. (e.g. set banned field of User object to true and call updateUser on UserDAO providing the User object)
- With above approach, you can reuse business logic that works with Objects returned from DAO and only need to write code for how the Objects are persisted. (e.g. DB, File, Memory...)
- Android's DAO seems like a mega class with many responsibilities. I would first break it into separate DAOs one for each DB table.
