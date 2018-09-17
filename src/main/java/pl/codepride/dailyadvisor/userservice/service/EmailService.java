package pl.codepride.dailyadvisor.userservice.service;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);
}
