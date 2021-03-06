package com.safe_route.safe.document;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;

import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@Import(RestDocConfiguration.class)
@AutoConfigureRestDocs
@SpringBootTest
class LocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("PathFinder Test")
    void pathFinderTest() throws Exception {
        
        this.mockMvc.perform(get("/safe/routing?srcLati=35.242355&srcLongti=128.696837&dstLati=35.232539&dstLongti=128.674172&safeDegree=0"))
            .andDo(print())
            .andDo(document("pathfinder-get",
                requestParameters(
                    parameterWithName("srcLati").description("????????? ??????"),
                    parameterWithName("srcLongti").description("????????? ??????"),
                    parameterWithName("dstLati").description("????????? ??????"),
                    parameterWithName("dstLongti").description("????????? ??????"),
                    parameterWithName("safeDegree").description("????????? 0 ~ 1")
                ),
                responseFields( 
                    fieldWithPath("error").type(JsonFieldType.VARIES).description("?????? ??????"),
                    fieldWithPath("total").type(JsonFieldType.NUMBER).description("??? ????????? ??????"),
                    fieldWithPath("data.[].type").type(JsonFieldType.NUMBER).description("?????? ?????? (1 ~ 5)"),
                    fieldWithPath("data.[].name").type(JsonFieldType.STRING).description("?????? ??????"),
                    fieldWithPath("data.[].lati").type(JsonFieldType.NUMBER).description("????????? ?????? ??????"),
                    fieldWithPath("data.[].longti").type(JsonFieldType.NUMBER).description("????????? ?????? ??????"),
                    fieldWithPath("data.[].oriLati").type(JsonFieldType.VARIES).description("?????? ?????? ??????, ????????? "),
                    fieldWithPath("data.[].oriLongti").type(JsonFieldType.VARIES).description("?????? ?????? ??????, ?????????")
                ))
            )
        .andExpect(jsonPath("data.[0].type").isNumber())
        .andExpect(jsonPath("data.[0].lati").isNumber())
        .andExpect(jsonPath("data.[0].longti").isNumber());
    }
}