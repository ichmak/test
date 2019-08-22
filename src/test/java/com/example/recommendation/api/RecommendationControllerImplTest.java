package com.example.recommendation.api;

import com.example.recommendation.AbstractIntegrationTest;
import com.example.recommendation.data.dto.Product;
import com.example.recommendation.data.dto.ProductType;
import com.example.recommendation.data.dto.Profile;
import com.example.recommendation.factory.ProductFactory;
import com.example.recommendation.factory.ProfileFactory;
import com.example.recommendation.service.product.ProductService;
import com.example.recommendation.service.profile.ProfileService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MvcResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RecommendationControllerImplTest extends AbstractIntegrationTest {

    private static final int ONE_THOUSAND = 1000;
    private static final int DEFAULT_PRODUCT_RISK_CATEGORY = 2;
    private static final String URI = "/recommendation";
    private static final String PROFILE_ID = "profileId";
    private static final String AMOUNT = "amount";
    private static final String TEST_AMOUNT_VALUE = "2000";
    private static final String PARAM_PRODUCT_ID = "productId=";
    private static final String PRODUCT_COUNT = ", count=1";
    private static final String TEST_RESULT_FAILED = "Test result failed";

    @Autowired
    private ProductFactory productFactory;

    @Autowired
    private ProfileFactory profileFactory;

    @MockBean
    private ProfileService profileService;

    @MockBean
    private ProductService productService;

    @Test
    public void givenRequestWithoutParams_whenGet_thenOk() throws Exception {
        //given
        Profile profile = profileFactory.create(DEFAULT_PRODUCT_RISK_CATEGORY);

        List<Product> products = new ArrayList<>();
        products.add(
                productFactory.create(
                        ProductType.INCOME, DEFAULT_PRODUCT_RISK_CATEGORY, BigDecimal.valueOf(ONE_THOUSAND)
                )
        );
        products.add(
                productFactory.create(
                        ProductType.INSURANCE, DEFAULT_PRODUCT_RISK_CATEGORY, BigDecimal.valueOf(ONE_THOUSAND)
                )
        );

        //when
        when(profileService.getProfile(profile.getId().toString())).thenReturn(Mono.just(profile));
        when(productService.getProductsByRiskCategory(profile.getRisk())).thenReturn(Flux.fromIterable(products));

        //then
        MvcResult result = this.mvc.perform(
                get(URI)
                        .param(PROFILE_ID, profile.getId().toString())
                        .param(AMOUNT, TEST_AMOUNT_VALUE)
        ).andExpect(request().asyncStarted())
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getAsyncResult().toString();
        products.forEach(
                product -> assertTrue(
                        responseBody.contains(PARAM_PRODUCT_ID + product.getId().toString() + PRODUCT_COUNT),
                        TEST_RESULT_FAILED
                )
        );
    }

}
