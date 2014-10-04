Why did we choose the project?
This topic presented an opportunity to produce the highest quality final product considering the amount of time required. We felt that the other options would have taken more effort to deliver lesser results.
The decision to choose this project was unanimous. We all started the discussion with the same topic in mind.
Personas
    Administrative users can post bulletins that are visible to all users
    
    Non-administrative users can also post bulletins, but they are not forced to be visible
    
    Other users can choose to filter out topics that they are not interested in
User stories
    The Consumer
        The Consumer wants to read information about topics that are interesting. (High)The Consumer wants to be able to list topics of interest, and automatically be provided with discussion threads which match those topics. (low). The Consumer would like to be able to browse topics, and view discussion threads within those topics (high). The consumer would like to receive notifications when new topics are available which match his preferences (Low). The consumer would like to be able to register an account, and save preferences/subscriptions about discussion topics (high). Track whether a user has viewed a thread, highlight it if there are new posts since it was last viewed (low).
        
        The Outsider - The Outsider does not want to create an account,  but still wishes to be able to browse public discussions anonymously (low).
        
        The Producer
        The Producer wants to write posts online, and  be able to apply attributes to those posts to have them associated with topics (high). The Producer wants other users to be able to respond to posts which have been written (medium), and those users should be able to discover them by specifying a topic which the original post has been tagged with (high). The producer would like to be notified when replies are received to his posts (low)
        
        The Administrator
        The administrator would like to post administrative bulletins which are automatically highly visible to all users, regardless of which topics they are interested in (medium). The administrator enjoys censorship and would like to be able to remove topics that are determined to be inappropriate (medium). The administrator would like to be able to create and destroy topics (low) (there will default topics provided). The administrator would like to be able to disable and ban accounts (medium).
        
    The Social One - this user would like to communicate directly with other users in private discussions, one-to-one messaging (low). The Social One would also like to maintain a contacts list (low).
    
MVP
Categories - Set by administrator, a thread may only be in one category
Topics - created by users, a thread may be in several topics
Threads - created by users, replied to by users. Category/topics set by original poster
Users can browse threads within a category, filter by topic
User can register an account and save preferences about favourite topics
Users can view a list of favourite topics and view threads from them
This product will organize discussion threads into categories. The categories are broad, and are not configurable by users. Threads may be posted in a single category, and may be tagged with topic attributes. These topic tags can be used to filter posts within a category. Users can create accounts, and post new threads within a category; while posting the thread, the user may choose one or several topics to tag the thread with. The user can choose from exisitng topics, or create new tags at the time of posting. Users can save lists of topics that they are interested in, and view posts from within those topic areas.


Server component - The server will provide data via HTTP to the android client. It will also accept updates.

Server URLs -
    /categories
        List of category IDs and names
    /category_topics/{category_id}
        For a given category ID, the topics in this category. IDs and names
    /threads/{thread_id}/{page number}
        The thread which has the given ID (all data), replies on the given page number
    /theads_by_topic/{topic_id}/{page number}
        All thread headings that match a given topic, on the given page number (sorted)
    /threads_by_category/{category_id}/{page_number}
        All thread headings associated with the given category, on the given page number (sorted)
    /register
        Register an account (client sends POST to server)
    /login
        Log in to an existing account (client sends POST to server)
    /logout
        Log off from session
    /new_thread
        Create a new thread (paramenters in POST payload)
    /reply
        Reply to an existing thread
    /subscribe
        Add topic to list of favorites
    /subscriptions/{category}
        List of topics subscribed to, in a given category
    /contacts
        Get list of contacts
    /addcontact
        Add user to contacts list
    /deletecontact
        Delect a contact from the list
    /messages/{page number}
        View all private message by page
    /messages_user/{userID}/{page number}
        View private messages from a specific user
    /messages/sent/{page number}
        View sent messages
    /message/{message_id}
        View private message
    /sendmsg
        Send private message
    /admin/ban_user
        Ban a user
    /admin/delete_thread
        Delete a thread
    /admin/delete_reply
        Delete a reply
    /admin/create_category
        Create a category
    /admin/delete_category
        Delete a category
    /admin/modify_user
        Modify an existing user
