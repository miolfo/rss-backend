#Rss backend
Backend app built with Spring Boot for rss reader application


##Api
WIP: Simple descriptions of application endpoints
###Feeds
Single feed can consist of multiple rss channels. Create new feed with

POST /api/v1/feed - create new feed  
GET /api/v1/feed/:feedId - get items from feed  
POST /api/v1/feed/:feedId - add rss to feed
