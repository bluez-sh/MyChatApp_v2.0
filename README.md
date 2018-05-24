# MyChatApp_v2.0
A command line chat app for two users with additional features using java.

## How to run?
### Run the server:
Change the current directory to <code> MyChatApp_v2.0/bin </code>
<br> Now run this command:
<code> java server.ChatServer 4444 </code>
<br><br> where, "4444" is the port number, could be anything between 0 to 65535 (Use higher ones, many lower ones are reserved).
### Run the client:
On the same directory, run the command: <code> java client.Client localhost 4444 </code>
<br><br> where, "localhost" is for testing it in the same machine but could be the IP address of the machine 
<br> that is running the ChatServer.
<br> and, "4444" is the same port which was used by the server.
### Run another client:
Similarly, run a client on another terminal using the same "localhost" argument and now the two client terminals can
<br> pass messages between them. And of course you can do this over any network using IP addresses.
<br><br> Now this app allows only two users (who's handles are hardcoded as the variables *user1* and *user2*
<br> in the server side) and thus allows only those two usernames to connect to the server.

## Features and Directions

- The two variables named *user1* and *user2* in the server side must be changed to desired handles.
Nothing fancy, just acts as additional security. 
- A message queue is implemented in the server, which can enqueue the messages if the other user is offline, and dequeues them when the other user comes online.
(These messages are stored in the memory, so of course the server needs to be up all the time (duh!))
- Every message in the conversation is logged into a serialized file named "conv.ser" on both the client machines.
User can read them by typing ":read" and pressing enter. A prompt will ask for the number of messages to be displayed, the most recent ones.
(Enter 0 to display all)
- Always end the client by typing "tata" and pressing enter. If terminated otherwise, the other user won't be able to send you messages when you are offline.
In case of poor network connection, try sending "tata" multiple times until you disconnect. Or just <code>Ctrl+C</code> it and run the client again, and then send "tata" to close.
(You know what they say about goodbyes...)
- Well, that's it for now, might add something later. :)
