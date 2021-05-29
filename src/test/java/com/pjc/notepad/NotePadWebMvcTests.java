package com.pjc.notepad;


import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Random;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pjc.notepad.member.service.dto.MemberDto;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


@AutoConfigureMockMvc
@SpringBootTest
class NotePadWebMvcTests {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MockMvc mockMvc; 

	@Test
	void LoginGet() throws Exception {
		this.mockMvc.perform(get("/login"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("ID를 입력해주세요.")));
		LOGGER.info("get test done!");
	}

	@Test
	void SignInPost() throws Exception {
		LOGGER.info("MockMvcSignInPost Init");
		int minCase = 100;
		int maxCase = 100;
		Random random = new Random();
		int testCase = minCase + random.nextInt(maxCase - minCase + 1);
		LOGGER.info("MockMvcSignInPost testCase {}", testCase);
		for (int i = 0; i < testCase; i++) {
			
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			params.add("memberId", UUID.randomUUID().toString());
			params.add("memberPw", "test");
			params.add("memberMail", UUID.randomUUID().toString().substring(0,10)+"@"+"random.com");
			LOGGER.info("random Member {}", params.get("memberId"));
			
			this.mockMvc.perform(post("/member")
			.params(params))
			.andExpect(status().isCreated());
		}
	}
	@Test
	void SignInAndLoginTest() throws Exception {
		MemberDto memberDto = new MemberDto("test", "test", "test@mail.com", 1, 0, null, null);
		memberDto = SignIn(memberDto);
		memberDto = Login(memberDto);
	}

	MemberDto SignIn(MemberDto memberDto) throws Exception {

		MultiValueMap<String, String> testAccount = new LinkedMultiValueMap<>();

		testAccount.add("memberId", memberDto.getMemberId());
		testAccount.add("memberPw", memberDto.getMemberPw());
		testAccount.add("memberMail", memberDto.getMemberMail());

		this.mockMvc.perform(post("/member")
			.params(testAccount))
			.andExpect(status().isCreated());

		return memberDto;
	}

	MemberDto Login(MemberDto memberDto) throws Exception {
		MultiValueMap<String, String> login = new LinkedMultiValueMap<>();

		login.add("memberId", memberDto.getMemberId());
		login.add("memberPw", memberDto.getMemberPw());
	
		this.mockMvc.perform(post("/login")
			.params(login))
			.andExpect(status().isOk());
		
		return memberDto;
	}

	@Test
	void NoteInsertTest() throws Exception {
		MemberDto memberDto = new MemberDto("test", "test", "test@mail.com", 1, 0, null, null);
		SignIn(memberDto);
		Login(memberDto);
		// TODO : NOTE INSERT 
	}

	public static String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}  

}
