package com.example.recommendation.service.profile;

import com.example.recommendation.data.dto.Profile;
import reactor.core.publisher.Mono;

public interface ProfileService {

    Mono<Profile> getProfile(String profileId);
}
