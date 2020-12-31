package com.fabian.vacationapi.email;

public interface EmailService {
	 void sendSimpleMessage(String to,
             String subject,
             String text);
}
