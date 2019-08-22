package com.example.recommendation.web_client.profile;

import com.example.recommendation.data.dto.Profile;
import reactor.core.publisher.Mono;

public interface ProfileWebClient {

    Mono<Profile> getProfile(String profileId);
}
