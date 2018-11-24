# TinyUrl
***
This is basic replication of https://tinyurl.com , TinyUrl is a URL shortening web service, which provides short aliases for redirection of long URLs.  

##### Live Demo
TinyUrl: http://ec2-18-206-89-128.compute-1.amazonaws.com:8080/tinyurl

##### Functionalities
There are two main functionalities of TinyUrl service
    
- It allows users to create short urls from the long url.
- It allows users to access long urls by querying short url.

##### Technology

Glassfish Jersey is used as a web framework to implement RESTful service. TinyUrl services implements 3 APIs.

1. GET /{shorturl}
```Json
Response: It redirects the user to it's corresponding mapped long URL
```
2. GET /api/url/
```
Request:
{
    "bigUrl": "long url"
}
Response:
{
    "url": "short url"
}
```
3. POST /api/url/
```
Request:
{
    "url": "long url" 
}
Response:
{
    "ok": "ture"/"false"
    "url": {
        "url": "short url"
    }
}
```


##### Implementation
TinyUrl is generated by hashing log url using SHA-256 hashing scheme. This generated url is then stored on a database (In this case we are using MySql). Database table consists of two basic fields <LONGURL, HASH> where "HASH" is the first 7 digits of hash value generated using SHA-256 and "LONGURL" is the URL that user wants to shorten.

It might be possible that firt 7 digits of two hash values may be same (i.e. XYZ url and ABC url might have same first-7 digits in hash value 7u2Z0qw). However, the probability of that is very negligible. Limiting a hash value to 7 digit gives us 2<sup>62</sup> (which is equivalent to some 2 Quintillion) possible combinations, considering each place can be filled by eiter A-Z | a-z | 0-9. Thus the probability of conflict is 1 in 1 Quintillion.

The implementation also ensures that no two write calls are simultaneously happening on database. There by ensuring that first call that comes to server is stored in database and the logic will not regenerate the same URL.
    
##### Deployment
Currently the project is hosted on T2.Micro AWS instance. The server uses Amazon Linux as OS and is running Tomcat-7 server. Database is implemented using mysql server which is also running on same instance.