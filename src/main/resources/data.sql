INSERT INTO Feed(id, name) VALUES (1, 'Sample Feed');
INSERT INTO Feed(id, name) VALUES (2, 'feed');
INSERT INTO FEED(id, name) VALUES (3, 'news');
INSERT INTO Feed_Source(id, feed_id, name, source) VALUES (1, 1, 'HS', 'https://www.hs.fi/rss/tuoreimmat.xml');
INSERT INTO Feed_Source(id, feed_id, name, source) VALUES (2, 1, 'YLE', 'https://feeds.yle.fi/uutiset/v1/majorHeadlines/YLE_UUTISET.rss');
INSERT INTO Feed_Source(id, feed_id, name, source) VALUES (3, 2, 'Reddit news', 'https://www.reddit.com/r/news/.rss');