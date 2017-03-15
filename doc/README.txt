#Introduction About The Project#

########################################################################

This is the server part for easy_delivery.

########################################################################

What This Sever Can Do:

1.Connect to database to CRUD

2.Get the request from the Android APP and handle the request

################################################################################################################################

How  To Use This Server:

#The Adress for this server is irregular becauser the IP changes by location.#

First you should use some identity to log in so that the server can provide service for you.

Your Personal information will be stored in the session part so that you can use this server later.

During the test, this is only one admin whose name is root and password is root.

You can login by this  "xxx.xxx.xxx.xxx:8080/login?name=xxx&password=xxx".

Then you can do something about the affairs.

################################################################################################################################

For the entity User which means the people who deliver the out-take.

It is defined as User(long id,String phone, String name,long shopId,String password,short status)

You can create a User by this "xxx.xxx.xxx.xxx:8080/createUser?phone=xxx&name=xxx&shopId=xxx&password=xxx&status=xxx";
The server will return some msg to tell you the result.

You can delete a User by this "xxx.xxx.xxx.xxx:8080/deleteUser?id=xxx";
The server will return some msg to tell you the result.

You can find a User by this "xxx.xxx.xxx.xxx:8080/getUserByName?name=xxx";
The server will return some msg to tell you the result.

You can update a User by this  "xxx.xxx.xxx.xxx:8080/updateUser?id=xxx&phone=xxx&name=xxx&shopId=xxx&password=xxx&status=xxx";
The server will return some msg to tell you the result.

################################################################################################################################

For the entity Client which means the people who order some food.

It is defined as Client(long id,short status,String info)

You can create a Client by this "xxx.xxx.xxx.xxx:8080/createClient?status=xxx&info=xxx"
The server will return some msg to tell you the result.

You can delete a Client by this "xxx.xxx.xxx.xxx:8080/deleteClient?id=xxx"
The server will return some msg to tell you the result.

You can update a Client by this "xxx.xxx.xxx.xxx:8080/updateClient?id=xxx&status=xxx&info=xxx"
The server will return some msg to tell you the result.

You can get one Client by this "xxx.xxx.xxx.xxx:8080/getOneClient?id=xxx"
The server will return one Client to you.

You can get all Client by this "xxx.xxx.xxx.xxx:8080/getAllClient"
The server will return Iterable<Client> to you.
################################################################################################################################

For the entity Shop which means the people who order some food.

It is defined as Shop (long id,short status,String info)

You can create a Shop by this "xxx.xxx.xxx.xxx:8080/createShop?status=xxx&info=xxx"
The server will return some msg to tell you the result.

You can delete a Shop by this "xxx.xxx.xxx.xxx:8080/deleteShop?id=xxx"
The server will return some msg to tell you the result.

You can update a Shop by this "xxx.xxx.xxx.xxx:8080/updateShop?id=xxx&status=xxx&info=xxx"
The server will return some msg to tell you the result.

You can get one Shop by this "xxx.xxx.xxx.xxx:8080/getOneShop?id=xxx"
The server will return one Shop to you.

You can get all Shop by this "xxx.xxx.xxx.xxx:8080/getAllShop"
The server will return Iterable<Shop> to you.

################################################################################################################################

For the entity Order which means the information of order

It is defined as Order(long id,long clientId, long shopId,long userId,double longitude,double latitude,short status)


/*
 *The status describe the the order.In the begining,we assume the value is 0 and it means this order has not been handled
 */

You can create a Order by this "xxx.xxx.xxx.xxx:8080/createOrder?clientId=xxx&shopId=xxx&userId=xxx&longitude=xxx&latitude=xxx&status=xxx"
The server will return some msg to tell you the result.

You can delete a Order by this "xxx.xxx.xxx.xxx:8080/deleteOrder?id=xxx"
The server will return some msg to tell you the result.

You can update a Order by this "xxx.xxx.xxx.xxx:8080/updateOrder?id=xxx&clientId=xxx&shopId=xxx&userId=xxx&longitude=xxx&latitude=xxx&status=xxx"
The server will return some msg to tell you the result.

You can get one Order by this "xxx.xxx.xxx.xxx:8080/getOneOrder?id=xxx"
The server will return one order to you.

You can get all Order by this "xxx.xxx.xxx.xxx:8080/getAllOrder"
The server will return Iterable<Order> to you.

You can get one User's Orders by this "xxx.xxx.xxx.xxx:8080/getOrderByUser?id=xxx"
The server will return Iterable<Order> to you.

You can get one Shop's Order by this "xxx.xxx.xxx.xxx:8080/getOrderByShop?id=xxx"
The server will return Iterable<Order> to you.

You can get one Client's Order by this "xxx.xxx.xxx.xxx:8080/getOrderByClient?id=xxx"
The server will return Iterable<Order> to you.

/*
 *The so called new order means this order hasn't been handled and the value of its status is 0
 */

You can get one User's New Orders by this "xxx.xxx.xxx.xxx:8080/getNewOrderByUser?id=xxx"
The server will return Iterable<Order> to you.

You can get one Shop's New Order by this "xxx.xxx.xxx.xxx:8080/getNewOrderByShop?id=xxx"
The server will return Iterable<Order> to you.

You can get one Client's New Order by this "xxx.xxx.xxx.xxx:8080/getNewOrderByClient?id=xxx"
The server will return Iterable<Order> to you.

You can get all New Order by this "xxx.xxx.xxx.xxx:8080/getAllNewOrder"
The server will return Iterable<Order> to you.


################################################################################################################################
For the entity Item which means the items of shop

It is defined as Item(long id, String name, String Price, long shopId, short status)


/*
 *The status describe the the how the Item is liked by clients
 */

You can create a Item by this "xxx.xxx.xxx.xxx:8080/createItem?name=xxx&shopId=xxx&price=xxx&status=xxx"
The server will return some msg to tell you the result.

You can delete a Item by this "xxx.xxx.xxx.xxx:8080/deleteItem?id=xxx"
The server will return some msg to tell you the result.

You can get one Shop's Item by this "xxx.xxx.xxx.xxx:8080/getItemByShopId?shopId=xxx"
The server will return Iterable<Item> to you.


################################################################################################################################
For the entity Food which means the items of order

It is defined as Food(long id, long itemId, long orderId)

You can create a Food by this "xxx.xxx.xxx.xxx:8080/createFood?itemId=xxx&orderId=xxx";
The server will return some msg to tell you the result.

You can delete a Food by this "xxx.xxx.xxx.xxx:8080/deleteFood?id=xxx"
The server will return some msg to tell you the result.

You can get one Order's Item by this "xxx.xxx.xxx.xxx:8080/getFoodByOrderId?orderId=xxx"
The server will return Iterable<Item> to you.


################################################################################################################################
For the entity Message which means the communciation between users

It is defined as Message(long id, String info,String time,long sender,String senderName,long receiver,String receiverName, short status)

You can create a Message by this "xxx.xxx.xxx.xxx:8080/addMessage?status=xxx&info=xxx&sender=xxx&receiver=xxx";
The server will return some msg to tell you the result.

You can delete a Message by this "xxx.xxx.xxx.xxx:8080/deleteMessage?id=xxx"
The server will return some msg to tell you the result.

You can get one User's message by this "xxx.xxx.xxx.xxx:8080/getMessageBySenderId?senderId=xxx"
The server will return Iterable<Message> to you.

You can get one User's message by this "xxx.xxx.xxx.xxx:8080/getMessageByReceiverId?receiverId=xxx"
The server will return Iterable<Message> to you.


You can get one User's message by this "xxx.xxx.xxx.xxx:8080/getMessageBySenderIdAndReceiverId?senderId=xxx&receiverId=xxx"
The server will return Iterable<Message> to you.
















