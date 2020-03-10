package in.keepgrowing.springbootpostgresflyway.cookie;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CookieControllerTest {

    private final String apiPath = "/api/cookies";

    @MockBean
    private CookieRepository repository;

    @Autowired
    private MockMvc mvc;

    private JacksonTester<Cookie> jsonContent;

    @BeforeEach
    void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void shouldCreateNewCookie() throws Exception {
        var cookie = Cookie.from("chocolate");
        given(repository.save(cookie))
                .willReturn(cookie);

        mvc.perform(post(apiPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent.write(cookie).getJson()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flavour", is(cookie.getFlavour())));
    }

    @Test
    public void shouldThrowExceptionWhenFlavourIsNotSet() throws Exception {
        var cookie = Cookie.from("");

        mvc.perform(post(apiPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent.write(cookie).getJson()))
                .andExpect(status().isBadRequest());
    }
}