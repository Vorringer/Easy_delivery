drop procedure if exists createData;
delimiter //
create procedure createData()
begin
declare dataNum,i int;
/* dataNum is the scale of testing data
   you can set dataNum here to change the scale of testing data
*/
set dataNum=10,i=0;
while i <dataNum do 
/* if use in different database,
   you should change the name of database,
   eg:change elemetester into "your database name"
*/
	insert into elemetester.orders values(null,rand()*1000,rand()*1000,rand()*1000,convert((rand()*180),decimal),cast(rand()*180 as decimal),0);
	set i=i+1;
end while;
end;
//