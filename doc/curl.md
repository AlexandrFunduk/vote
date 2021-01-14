### curl samples (application deployed at application context `vote`).
> For windows use `Git Bash`

#### get All Users
`curl --location --request GET http://localhost:8080/vote/rest/admin/users --user admin@gmail.com:admin`

#### get Users 100001
`curl --location --request GET http://localhost:8080/vote/rest/admin/users/100001 --user admin@gmail.com:admin`

#### register Users
`curl -s -i -X POST -d '{"name": "NewUser","email": "Newuser@yandex.ru","enabled": true,"password": "newpassword","roles": ["USER","ADMIN"]}'  -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/vote/rest/admin/users --user admin@gmail.com:admin`

#### set disable for User 100002
`curl --location --request PATCH http://localhost:8080/vote/rest/admin/users/100003?enabled=false --user admin@gmail.com:admin`

#### update User 100002
`curl -s -X PUT -d '{"id": 100002,"name": "User3update","email": "Usr3update@yandex.ru","enabled": true,"password": "updatepassword","roles": ["ADMIN"]}' -H 'Content-Type: application/json' http://localhost:8080/vote/rest/admin/users/100002 --user admin@gmail.com:admin`

#### delete User 100002
`curl -s -X DELETE http://localhost:8080/vote/rest/admin/users/100002 --user admin@gmail.com:admin`

#### get Profile
`curl -s http://localhost:8080/vote/rest/profile --user user@yandex.ru:password`

#### update Profile
`curl -s -X PUT -d '{"id": 100000,"name": "User3update","email": "Usr3update@yandex.ru","password": "updatepassword"}' -H 'Content-Type: application/json' http://localhost:8080/vote/rest/profile --user user@yandex.ru:password`

#### delete Profile
`curl -s -X DELETE http://localhost:8080/vote/rest/profile --user user@yandex.ru:password`

#### register Users
`curl -s -i -X POST -d '{"name":"New User","email":"test@mail.ru","password":"test-password"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/vote/rest/profile/register`

#### add Restaurant
`curl -s -i -X POST -d '{"name": "newRestaurant"}'  -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/vote/rest/admin/restaurants --user admin@gmail.com:admin`

#### update Restaurant 100004
`curl -s -X PUT -d '{"id": 100004,"name": "updateRestaurant"}' -H 'Content-Type: application/json' http://localhost:8080/vote/rest/admin/restaurants/100004 --user admin@gmail.com:admin`

#### delete Restaurant 100004
`curl -s -X DELETE http://localhost:8080/vote/rest/admin/restaurants/100004 --user admin@gmail.com:admin`

#### get all Restaurants
`curl -s http://localhost:8080/vote/rest/restaurants --user user@yandex.ru:password`

#### get Restaurant 100004
`curl -s http://localhost:8080/vote/rest/restaurants/100004 --user user@yandex.ru:password`

#### add Menu
`curl -s -i -X POST -d '{"day": "2020-12-27","restaurantId": 100004,"dishPrise": {"A": 100,"B": 200,"C": 300}}'  -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/vote/rest/admin/menus?restaurantId=100004 --user admin@gmail.com:admin`

#### update Menu 100007
`curl -s -X PUT -d '{"id": 100007,"day": "2020-10-01","restaurantId": 100005,"dishPrise": {"A": 111,"B": 222,"C": 333}}' -H 'Content-Type: application/json' http://localhost:8080/vote/rest/admin/menus/100007 --user admin@gmail.com:admin`

#### delete Menu 100007
`curl -s -X DELETE http://localhost:8080/vote/rest/admin/menus/100007 --user admin@gmail.com:admin`

#### get Menus 100007
`curl -s http://localhost:8080/vote/rest/menus/100007 --user user@yandex.ru:password`

#### get today Menus
`curl -s http://localhost:8080/vote/rest/menus/today --user user@yandex.ru:password`

#### filter Menus
`curl -s 'http://localhost:8080/vote/rest/menus/filter?restaurantId=100004&startDate=2020-12-01&endDate=2020-12-02' --user user@yandex.ru:password`

#### add Vote
`curl -s -i -X POST --data-raw '100004' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/vote/rest/profile/votes --user user@yandex.ru:password`

#### update Vote 100020
`curl -s -X PUT --data-raw '100006' -H 'Content-Type: application/json' http://localhost:8080/vote/rest/profile/votes/100020 --user user1@yandex.ru:123456`

#### delete Vote 100014
`curl -s -X DELETE http://localhost:8080/vote/rest/profile/votes/100014 --user user@yandex.ru:password`

#### get all Vote
`curl -s http://localhost:8080/vote/rest/profile/votes --user user@yandex.ru:password`

#### get Vote 100014
`curl -s http://localhost:8080/vote/rest/profile/votes/100014 --user user@yandex.ru:password`


#### get today Vote
`curl -s http://localhost:8080/vote/rest/profile/votes/today --user user1@yandex.ru:123456`

#### filter Vote
`curl -s 'http://localhost:8080/vote/rest/profile/votes/filter?startDate=2020-12-02&endDate=2020-12-04' --user user1@yandex.ru:123456`

