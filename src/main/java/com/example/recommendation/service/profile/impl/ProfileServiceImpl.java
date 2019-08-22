package com.example.recommendation.service.profile.impl;

import com.example.recommendation.data.dto.Profile;
import com.example.recommendation.service.profile.ProfileService;
import com.example.recommendation.web_client.profile.ProfileWebClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileWebClient profileWebClient;

    public Mono<Profile> getProfile(String profileId) {
        return profileWebClient.getProfile(profileId);
    }
}
