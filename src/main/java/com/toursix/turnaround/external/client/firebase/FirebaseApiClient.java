package com.toursix.turnaround.external.client.firebase;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "firebaseApiClient", url = "${cloud.firebase.credentials.uri}")
public interface FirebaseApiClient {

    @PostMapping()
    @Headers("Content-Type: application/json; UTF-8")
    void requestFcmMessaging(@RequestHeader("Authorization") String accessTokenWithBearer,
            @RequestBody String requestBody);
}
