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
import ru.kpfu.itis.arifulina.config.OpenWeatherConfig;
import ru.kpfu.itis.arifulina.config.SecurityConfig;
import ru.kpfu.itis.arifulina.util.TestConstants;
import ru.kpfu.itis.arifulina.util.httpclient.HttpClient;
import ru.kpfu.itis.arifulina.util.httpclient.exc.HttpClientException;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(KazanWeatherController.class)
@ExtendWith(SpringExtension.class)
@Import({SecurityConfig.class, OpenWeatherConfig.class})
public class KazanWeatherControllerTest {

    public static final String OPEN_WEATHER_RESPONSE_BODY_EXAMPLE = "{\"coord\":{\"lon\":49.1221,\"lat\":55.7887},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04n\"}],\"base\":\"stations\",\"main\":{\"temp\":17.52,\"feels_like\":17.34,\"temp_min\":15.92,\"temp_max\":18.34,\"pressure\":1000,\"humidity\":77,\"sea_level\":1000,\"grnd_level\":987},\"visibility\":10000,\"wind\":{\"speed\":8.33,\"deg\":234,\"gust\":17.33},\"clouds\":{\"all\":100},\"dt\":1713380710,\"sys\":{\"type\":2,\"id\":48937,\"country\":\"RU\",\"sunrise\":1713317565,\"sunset\":1713369173},\"timezone\":10800,\"id\":551487,\"name\":\"Kazan’\",\"cod\":200}";
    public static final String CONTROLLER_RESPONSE_BODY_EXAMPLE = "--- WEATHER IN KAZAN --- TEMPERATURE: 17.52 C --- HUMIDITY: 77% --- PRECIPITATION: overcast clouds ---";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HttpClient httpClient;

    @MockBean
    private OpenWeatherConfig config;

    private Map<String, String> params;

    @BeforeEach
    public void beforeEach() {
        params = new HashMap<>();
        params.put("q", "kazan");
        params.put("appid", config.getAppid());
        params.put("units", "metric");
    }

    @Test
    public void testGetCurrentKazanWeather() throws Exception {
        given(httpClient.get(config.getUrl(), params, null)).willReturn(OPEN_WEATHER_RESPONSE_BODY_EXAMPLE);

        mockMvc.perform(get(ParamsKey.KAZAN_WEATHER_RM + ParamsKey.NOW_RM)
                        .with(user(TestConstants.DEFAULT_USERNAME).roles(ParamsKey.ROLE_USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(content().string(CONTROLLER_RESPONSE_BODY_EXAMPLE));
    }

    @Test
    public void testGetCurrentKazanWeatherFail() throws Exception {
        given(httpClient.get(config.getUrl(), params, null)).willThrow(HttpClientException.class);

        mockMvc.perform(get(ParamsKey.KAZAN_WEATHER_RM + ParamsKey.NOW_RM)
                        .with(user(TestConstants.DEFAULT_USERNAME).roles(ParamsKey.ROLE_USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(content().string(Messages.DEF_FAILURE_MSG));
    }
}
