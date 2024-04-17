package ru.kpfu.itis.arifulina.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.kpfu.itis.arifulina.base.Messages;
import ru.kpfu.itis.arifulina.base.ParamsKey;
import ru.kpfu.itis.arifulina.config.CurrencyApiConfig;
import ru.kpfu.itis.arifulina.config.SecurityConfig;
import ru.kpfu.itis.arifulina.util.TestConstants;
import ru.kpfu.itis.arifulina.util.httpclient.HttpClient;
import ru.kpfu.itis.arifulina.util.httpclient.exc.HttpClientException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExchangeRatesController.class)
@ExtendWith(SpringExtension.class)
@Import({SecurityConfig.class, CurrencyApiConfig.class})
public class ExchangeRatesControllerTest {

    public static final String CURRENCY_API_RESPONSE_BODY_EXAMPLE = "{\"meta\":{\"last_updated_at\":\"2024-04-16T23:59:59Z\"},\"data\":{\"EUR\":{\"code\":\"EUR\",\"value\":0.010002421},\"USD\":{\"code\":\"USD\",\"value\":0.010625725}}}";
    public static final String CONTROLLER_RESPONSE_BODY_EXAMPLE = "--- EXCHANGE RATES --- 1 EUR = 99,976 RUB --- 1 USD = 94,111 RUB --- ON " + LocalDate.now() + " ---";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HttpClient httpClient;

    @MockBean
    private CurrencyApiConfig config;

    private Map<String, String> params;

    @BeforeEach
    public void beforeEach() {
        params = new HashMap<>();
        params.put("apikey", config.getApikey());
        params.put("base_currency", "RUB");
        params.put("currencies", "EUR,USD");
    }

    @Test
    public void testGetExchangeRates() throws Exception {
        given(httpClient.get(config.getUrl(), params, null)).willReturn(CURRENCY_API_RESPONSE_BODY_EXAMPLE);

        mockMvc.perform(get(ParamsKey.EXCHANGE_RATES_RM + ParamsKey.NOW_RM)
                        .with(user(TestConstants.DEFAULT_USERNAME).roles(ParamsKey.ROLE_USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(content().string(CONTROLLER_RESPONSE_BODY_EXAMPLE));
    }

    @Test
    public void testGetExchangeRatesFail() throws Exception {
        given(httpClient.get(config.getUrl(), params, null)).willThrow(HttpClientException.class);

        mockMvc.perform(get(ParamsKey.EXCHANGE_RATES_RM + ParamsKey.NOW_RM)
                        .with(user(TestConstants.DEFAULT_USERNAME).roles(ParamsKey.ROLE_USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(content().string(Messages.DEF_FAILURE_MSG));
    }
}
