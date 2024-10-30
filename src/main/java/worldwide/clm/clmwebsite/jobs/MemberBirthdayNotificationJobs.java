package worldwide.clm.clmwebsite.jobs;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import worldwide.clm.clmwebsite.data.models.MembersData;
import worldwide.clm.clmwebsite.data.repositories.MembersDataRepository;
import worldwide.clm.clmwebsite.dto.request.EmailNotificationRequest;
import worldwide.clm.clmwebsite.dto.request.Recipient;
import worldwide.clm.clmwebsite.services.notificationServices.mailServices.MailService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static worldwide.clm.clmwebsite.utils.AppUtils.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberBirthdayNotificationJobs {

    private final MembersDataRepository membersDataRepository;
    private final MailService mailService;

    @Value("${clmwebsite.recipient.emailAddresses}")
    private List<String> recipientEmailAddresses;

    @Value("${clmwebsite.default.member.picture}")
    private String defaultMemberPicture;

    @Scheduled(cron = "0 0 12 * * ?")
//    @EventListener(ApplicationReadyEvent.class)
    public void sendBirthdayNotification() {
        LocalDate today = LocalDate.now();
        List<MembersData> members = new ArrayList<>();

        // Check if today is the second-to-last day of the month
        if (today.getDayOfMonth() == today.lengthOfMonth() - 1) {

            log.info("sendBirthdayNotification called at startup");

            members = membersDataRepository.findAllByMonthOfBirth(today.getMonthValue() + 1);
            log.info("Members: {}", members);
            sendMail(members);
        }

        members = membersDataRepository.findAllByMonthOfBirthAndNotificationSent(today.getMonthValue(), false);
        log.info("Members: {}", members);
        sendMail(members);

    }

    private void markAsCompleted(List<MembersData> members) {
        if (!members.isEmpty()) {
            members.forEach(member -> member.setNotificationSent(true));
            membersDataRepository.saveAll(members);
        }
    }

    private void sendMail(List<MembersData> members) {
        EmailNotificationRequest emailRequest = new EmailNotificationRequest();
        emailRequest.setTo(getRecipients(recipientEmailAddresses));
        emailRequest.setSubject(BIRTHDAY_NOTIFICATION);
        emailRequest.setText(buildUserEmailContent(members));
        try {
            mailService.sendHtmlMail(emailRequest);
        } catch (MessagingException e) {
            log.info("Error sending mails: {}", e.getMessage());
        }
        log.info("Mail Notification: {}", "Sent Successfully!");

        markAsCompleted(members);
    }


    private List<Recipient> getRecipients(List<String> emailAddresses) {
        return emailAddresses.stream()
                .map(emailAddress -> Recipient.builder()
                        .email(emailAddress)
                        .build()
                ).collect(Collectors.toList());
    }

    public String buildUserEmailContent(List<MembersData> members) {
        StringBuilder emailContent = new StringBuilder();

        // Add some inline styling for the email
        emailContent.append("<div style=\"font-family: Arial, sans-serif; background-color: #f4f4f9; padding: 20px;\">");

        for (MembersData member : members) {
            String userCard = String.format(
                    "<div style=\"background-color: #ffffff; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); max-width: 400px; margin: 10px auto; padding: 20px; text-align: center;\">" +
                            "<img src=\"%s\" alt=\"No Picture Uploaded Yet\" style=\"width: 100%%; height: auto; border-radius: 8px;\">" +
                            "<h2 style=\"font-size: 24px; color: #333333; margin: 10px 0;\">%s</h2>" +
                            "<div style=\"text-align: left; margin-top: 10px;\">" +
                            "<p style=\"margin: 5px 0; font-size: 16px; color: #555555;\"><strong style=\"color: #333333;\">Location:</strong> %s</p>" +
                            "<p style=\"margin: 5px 0; font-size: 16px; color: #555555;\"><strong style=\"color: #333333;\">Phone Number:</strong> %s</p>" +
                            "<p style=\"margin: 5px 0; font-size: 16px; color: #555555;\"><strong style=\"color: #333333;\">Email Address:</strong> %s</p>" +
                            "<p style=\"margin: 5px 0; font-size: 16px; color: #555555;\"><strong style=\"color: #333333;\">Gender:</strong> %s</p>" +
                            "<p style=\"margin: 5px 0; font-size: 16px; color: #555555;\"><strong style=\"color: #333333;\">Date of Birth:</strong> %s</p>" +
                            "</div>" +
                            "</div>",
                    member.getPicture() == null || member.getPicture().isBlank() ? defaultMemberPicture : member.getPicture(),
                    member.getFirstname() + " " + member.getLastname(),
                    member.getLocation(),
                    member.getPhoneNumber(),
                    member.getEmailAddress(),
                    member.getGender(),
                    getDobFormat(member.getDob())
            );
            emailContent.append(userCard);
        }

        emailContent.append("</div>");
        return emailContent.toString();
    }

    private String getDobFormat(LocalDate dob) {
        if (dob != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM");
            return dob.format(formatter);
        } else return "DOB was not updated";
    }

}
