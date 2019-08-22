package com.example.recommendation.web_client.profile.impl;

import com.example.recommendation.data.dto.Profile;
import com.example.recommendation.web_client.profile.ProfileWebClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ProfileWebClientImpl implements ProfileWebClient {

    private static final String URI = "/profile/{id}";

    private final WebClient webClient;

    public ProfileWebClientImpl(@Value("${service.profile.url}") String serverUrl) {
        this.webClient = WebClient.builder().baseUrl(serverUrl).build();

    }

    public Mono<Profile> getProfile(String profileId) {
        return webClient.get().uri(URI, profileId).retrieve()
                .bodyToMono(Profile.class);
    }
}
