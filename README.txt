I. Introduction

The goal of this project is to develop a Botnet Server. But, this project
needs a step by step plan before we could arrive at the final product.

Java Master will serve as the Server of the Clients. Clients will connect
to Java Master, and Java Master will issue commands to Clients from the
Bot Master.

For this project to become feasible, we need a small finite plan that could
expand into a bigger system. This project is very new to me, and this is my
dream project to begin with. I don't like to develop this system carelessly.
I would like to ensure that this project will succeed.

1. STEP #1 GOAL
Develop a general purpose Client and Server using UDP Protocol. Client should
be able to send request to server. And the server will reply appropriately to
the client, if the command sent by the client is correct.

Acknowledge connected bot using UDP.

1.1. Problem. How are we going to identify the connected client?
1.1.1. States
    1.1.1.1 Client Registration Phase
    Client: REGISTER <PC-NAME> <UNIQUE-RANDOM-ID>
    Server: REGISTERED <PC-NAME>-<UNIQUE-RANDOM-ID>-<SECRET-ID>
        1.1.1.1.1 Client registers with an existing unique random id
        Client: REGISTER <PC-NAME> <UNIQUE-RANDOM-ID>
        Server: ALREADY REGISTERED <PC-NAME> <UNIQUE-RANDOM-ID>
    1.1.1.2 Client Connection Phase
    Client: CONNECT <PC-NAME> <UNIQUE-RANDOM-ID> <SECRET-ID>
    Server: CONNECTED <PC-NAME> <UNIQUE-RANDOM-ID>
        1.1.1.2.1 Unique ID is invalid
        Client: CONNECT <PC-NAME> <UNIQUE-RANDOM-ID> <INVALID-SECRET-ID>
        Server: Server will not respond
        1.1.1.2.2 Client floods "CONNECT" command
        Client: CONNECT <PC-NAME> <UNIQUE-RANDOM-ID> <SECRET-ID> 100x
        Server: CONNECTED <PC-NAME> <UNIQUE-RANDOM-ID>
            - Server will reply "CONNECTED", but, server will only reply once every 5 seconds
            - Server will be treated client as connected once every 10 seconds of client ina-
            ctivity.
        1.1.1.2.3 Client connects with secret id, but client is not yet registered
        Client: CONNECT <PC-NAME> <UNIQUE-RANDOM-ID> <SECRET-ID>
        Server: NOT YET REGISTERED <PC-NAME> <UNIQUE-RANDOM ID>
        Client: REGISTER <PC-NAME> <UNIQUE-RANDOM-ID>
        Server: REGISTERED <PC-NAME> <UNIQUE-RANDOM-ID> <SECRET-ID>



Old botnet designs are deployed through IRC server. But it has weakness like
it can only receive command. Sure, that client can submit information like
computer information, steal serial keys from users. But it must be very
limited to textual data. And this system should conquer that limitation.

Develop a message relaying server similar to how IRC servers are implemented.
But, it must be different from IRC because it will allow transmission of non-
textual data like binary data.

# Things that I envisioned that we can implement today:
* 2-way file transfer
    * Has execute option (if file is executable)
* File download from URL
    * Has execute option (if file is executable)
* Live OS Command Line

As of the moment, I think that UDP must only be used for sending command