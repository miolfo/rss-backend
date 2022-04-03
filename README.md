#Rss backend
Backend app built with Spring Boot for rss reader application


##Api
WIP: Simple descriptions of application endpoints
###Feeds
Single feed can consist of multiple rss channels.

GET /api/v1/feed - get all feeds  
POST /api/v1/feed - create new feed  
GET /api/v1/feed/:feedId - get feed info  
PUT /api/v1/feed/:feedId - update feed info   
DELETE /api/v1/feed/:feedId - delete feed

GET /api/v1/feed/:feedId/items - get feed items

###Feed sources
Single feeds sources are handled through separate endpoints

GET /api/v1/feed/:feedId/source - get all sources for a single feed   
POST /api/v1/feed/:feedId/source - add a new source  
DELETE /api/v1/feed/:feedId/source/:sourceId - delete a source  
PUT /api/v1/feed/:feedId/source/:sourceId - edit a source
