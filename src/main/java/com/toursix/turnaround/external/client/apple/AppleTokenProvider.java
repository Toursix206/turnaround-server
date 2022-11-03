package com.toursix.turnaround.external.client.apple;

public interface AppleTokenProvider {

    String getSocialIdFromIdToken(String idToken);
}
