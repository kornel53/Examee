# Examee
## Description
Examee is an console application that supports teachers in examining students. The teacher chooses exam by creating new or reading from file and runs the server. 
The student runs client app, enters name, server's IP address and solves exam sent by teacher. The application includes Java Core implementations like:
- I/O reading and writing
- Object serialization
- Collections 
- Sockets
- Threads support (TODO!)
## How to run?
* To run server you need to execute Main.main() function.
* To run client you need to execute ExamClient.main() function.
## How to use?
* When server side is run, main menu is drawn where you can create a new exam or read one from file. Then, you can choose option "Start exam", which starts server instance and waiting for client to log in.
* When client side is run, you need to enter an IP address of server and your name. Then, client is looking for server for max 15 seconds (5 retries x 3 seconds). If found, client can confirm readiness and start solving exam.
## How to run in Docker?
Move into server's directory and run a command:
```
docker build . -t examee:server
```
and client's directory
```
docker build . -t examee:client
```
Then, you have to create new volumes in order to save results and exams to file and make them able to read when exam is finished.
```
docker volume create quiz
docker volume create results
```
That's all!
Now, you can start the server
```
docker run -it -v quiz:/app/quiz/ -v results:/app/results examee:server
```
and client
```
docker run -it examee:client
```
