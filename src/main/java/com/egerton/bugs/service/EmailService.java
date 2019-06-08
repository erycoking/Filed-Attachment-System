
package com.egerton.bugs.service;
import com.egerton.bugs.components.MailContent;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.activation.DataSource;
import java.nio.charset.StandardCharsets;

@Service
public class EmailService {

	 @Autowired
	 private JavaMailSender mailSender;

	@Autowired
	private SpringTemplateEngine templateEngine;





	

	
	@Async
	public void sendEmail(MailContent mailContent,String emailTemplate,DataSource dataSource) {
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,
					MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

			messageHelper.setFrom(mailContent.getFrom());
			messageHelper.setTo(mailContent.getTo());
			messageHelper.setSubject(mailContent.getSubject());

			Context context = new Context();
			context.setVariables(mailContent.getModel());

			String contentTemplate = templateEngine.process(emailTemplate,context);


			messageHelper.setText(contentTemplate, true);
			messageHelper.addAttachment("example.pdf",dataSource);
		};


		mailSender.send(messagePreparator);
	}



}


