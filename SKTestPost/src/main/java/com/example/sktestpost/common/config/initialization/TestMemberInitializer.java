package com.example.sktestpost.common.config.initialization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.sktestpost.application.facade.MemberFacade;
import com.example.sktestpost.common.dto.request.JoinReqDTO;

@Configuration
public class TestMemberInitializer {

	private final MemberFacade memberFacade;

	@Autowired
	public TestMemberInitializer(MemberFacade memberFacade) {
		this.memberFacade = memberFacade;
	}

	@Bean
	public CommandLineRunner initializeDefaultMembers() {
		return args -> {
			JoinReqDTO joinReqDTO1 = new JoinReqDTO();
			joinReqDTO1.setName("admin1");
			joinReqDTO1.setAccountId("admin1");
			joinReqDTO1.setAccountPwd("admin1");
			joinReqDTO1.setRole("ROLE_USER");

			JoinReqDTO joinReqDTO2 = new JoinReqDTO();
			joinReqDTO2.setName("admin2");
			joinReqDTO2.setAccountId("admin2");
			joinReqDTO2.setAccountPwd("admin2");
			joinReqDTO2.setRole("ROLE_USER");

			memberFacade.join(joinReqDTO1);
			memberFacade.join(joinReqDTO2);
		};
	}

}
