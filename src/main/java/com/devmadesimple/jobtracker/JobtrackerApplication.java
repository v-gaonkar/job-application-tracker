package com.devmadesimple.jobtracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableCaching
@SpringBootApplication
public class JobtrackerApplication {

//    @Bean
//
//    public CommandLineRunner testUser(ContactRepository userRepo) {
//        return arg {
//
//            if(!userRepo.existsByEmailAndCompanyId("xz@unknown.com", 1L)) {
//				Contact user = new Contact();
//                user.setName("arya");
////                EntityManager.getReference(Company.class, 1L);
////                user.setCompany(company);
////                user.setPassword("bgh235662g");
//                user.setEmail("xz@unknown.com");
////                user.setRole(User.Role.ADMIN);
//                userRepo.save(user);
//            }
//        };
//    }

//	@Autowired
//    JavaMailSender javaMailSender;
//
//	@Bean
//	public CommandLineRunner testMail() {
//		return arg {
//			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//			simpleMailMessage.setTo("tomail@unknown.com");
//			simpleMailMessage.setFrom("frommail@unknown.com");
//			simpleMailMessage.setSubject("SpringBoot Email Send test");
//			simpleMailMessage.setText("Testing if sending mail from java mail sender works!");
//			javaMailSender.send(simpleMailMessage);
//			System.out.println("Mail send successfully " + simpleMailMessage.getTo());
//		};
//	}

	public static void main(String[] args) {
		SpringApplication.run(JobtrackerApplication.class, args);
	}

}
