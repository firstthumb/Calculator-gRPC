# Calculator gRPC

I created a simple service using gRPC to learn how it works. This is architecture behind.

![gRPC concept diagram](https://cloud.githubusercontent.com/assets/66023/16005823/03ff03aa-3172-11e6-9019-02c0fec55c2d.png)

## What is gRPC?
In gRPC a client application can directly call methods on a server application on a different machine as if it was a local object, making it easier for you to create distributed applications and services. As in many RPC systems, gRPC is based around the idea of defining a service, specifying the methods that can be called remotely with their parameters and return types. On the server side, the server implements this interface and runs a gRPC server to handle client calls. On the client side, the client has a stub that provides exactly the same methods as the server.

To get more information, http://www.grpc.io/docs/
