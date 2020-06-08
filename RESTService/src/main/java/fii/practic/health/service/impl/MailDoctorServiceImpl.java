package fii.practic.health.service.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import fii.practic.health.service.MailDoctorService;
import fii.practic.health.entity.model.Doctor;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;


import org.springframework.ui.velocity.VelocityEngineUtils;

@Service("mailDoctorService")
public class MailDoctorServiceImpl implements MailDoctorService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    VelocityEngine velocityEngine;

    public void sendEmail(final Object object) {

        Doctor doctor = (Doctor) object;
        MimeMessagePreparator preparator = getMessagePreparator(doctor);

        try {
            mailSender.send(preparator);
            System.out.println("Message has been sent to doctor.............................");
        } catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private MimeMessagePreparator getMessagePreparator(final Doctor doctor) {

        MimeMessagePreparator preparator = new MimeMessagePreparator() {

            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

                helper.setSubject("You have successfully registered");
                helper.setFrom("valentinmelen@gmail.com");
                helper.setTo(doctor.getEmail().getEmail());

                Map<String, Object> model = new HashMap<String, Object>();
                Date currentDate = new Date();
                model.put("currentDate",currentDate);
                model.put("doctor", doctor);

                String text = geVelocityTemplateContent(model);
                System.out.println("Template content : " + text);

                /*
                 * use the true flag to indicate you need a multipart message
                 */
                helper.setText(text, true);

                /*
                 * Additionally, let's add a resource as an attachment as well.
                 */
                helper.addAttachment("cutie.png", new ClassPathResource("fiipractic-icon.png"));

            }
        };
        return preparator;
    }

    public String geVelocityTemplateContent(Map<String, Object> model) {
        StringBuffer content = new StringBuffer();
        try {
            content.append(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "/vmtemplates/velocity_mailTemplate_Doctor.vm", model));
            return content.toString();
        } catch (Exception e) {
            System.out.println("Exception occured while processing velocity template:" + e.getMessage());
        }
        return "";
    }

}
