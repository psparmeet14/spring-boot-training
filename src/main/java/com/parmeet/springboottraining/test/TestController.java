package com.parmeet.springboottraining.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    /*
        Versioning REST API - Factors
        1. URI Pollution
        2. Misuse of HTTP Headers
        3. Caching
        4. Can we execute the request on the browser?
        5. API Documentation

        Examples of Versioning REST API:
        1. URI Versioning
            - Twitter
                https://api.twitter.com/1.1/statuses/home_timeline.json
            - Microsoft
                https://graph.microsoft.com/v1.0/me
            - My Person class
                http://localhost:8080/api/v1/person
                http://localhost:8080/api/v2/person
        2. Request Parameter Versioning
            - Amazon
                http://amazon.com/api/products?version=1
            - My Person class
                http://localhost:8080/api/person?version=1
                http://localhost:8080/api/person?version=2
        3. Custom Header Versioning
            - Microsoft
                https://graph.microsoft.com/me
                X-API-VERSION: 1.0
            - My Person class
                SAME-URL headers=[X-API-VERSION=1]
                SAME-URL headers=[X-API-VERSION=2]
        4. Media Type Versioning (a.k.a "content negotiation" or "accept header" or "mime type" versioning)
            - GitHub
                https://api.github.com/repos/octocat/Hello-World
                Accept: application/vnd.github.v3+json
            - My Person class
                SAME-URL headers=[Accept=application/vnd.parmeet.api.v1+json] & produces=application/vnd.parmeet.api.v1+json
                SAME-URL headers=[Accept=application/vnd.parmeet.api.v2+json] & produces=application/vnd.parmeet.api.v2+json

        Recommendation:
        1. Think about versioning even before you need it
        2. One enterprise - One Versioning Approach

     */

    @GetMapping("/v1/person")
    public PersonV1 getPersonV1_URIVersioning() {
        return new PersonV1("Parmeet Singh");
    }

    @GetMapping("/v2/person")
    public PersonV2 getPersonV2_URIVersioning() {
        return new PersonV2(new Name("Parmeet", "Singh"));
    }

    @GetMapping(path = "/person", params = "version=1")
    public PersonV1 getPersonV1_RequestParameterVersioning() {
        return new PersonV1("Parmeet Singh");
    }

    @GetMapping(path = "/person", params = "version=2")
    public PersonV2 getPersonV2_RequestParameterVersioning() {
        return new PersonV2(new Name("Parmeet", "Singh"));
    }

    @GetMapping(path = "/person/header", headers = "X-API-VERSION=1")
    public PersonV1 getPersonV1_CustomHeaderVersioning() {
        return new PersonV1("Parmeet Singh");
    }

    @GetMapping(path = "/person/header", headers = "X-API-VERSION=2")
    public PersonV2 getPersonV2_CustomHeaderVersioning() {
        return new PersonV2(new Name("Parmeet", "Singh"));
    }

    @GetMapping(path = "/person/accept", produces = "application/vnd.company.app-v1+json")
    public PersonV1 getPersonV1_MediaTypeAcceptHeaderVersioning() {
        return new PersonV1("Parmeet Singh");
    }


    @GetMapping(path = "/person/accept", produces = "application/vnd.company.app-v2+json")
    public PersonV2 getPersonV2_MediaTypeAcceptHeaderVersioning() {
        return new PersonV2(new Name("Parmeet", "Singh"));
    }
}
