package fii.practic.health.service.impl;


import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import fii.practic.health.service.MailAppointmentService;
import fii.practic.health.entity.model.Appointment;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;


import org.springframework.ui.velocity.VelocityEngineUtils;

@Service("mailAppointmentService")
public class MailAppointmentServiceImpl implements MailAppointmentService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    VelocityEngine velocityEngine;

    public void sendEmail(final Object object) {

        Appointment appointment = (Appointment) object;

        MimeMessagePreparator preparatorDoctor = getMessagePreparatorDoctor(appointment);
        MimeMessagePreparator preparatorPatient = getMessagePreparatorPatient(appointment);

        try {
            mailSender.send(preparatorDoctor);
            System.out.println("Message has been sent to doctor.............................");
            try {
                mailSender.send(preparatorPatient);
                System.out.println("Message has been sent to patient.............................");
            } catch (MailException ex) {
                System.err.println(ex.getMessage());
            }
        } catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private MimeMessagePreparator getMessagePreparatorDoctor(final Appointment appointment) {

        MimeMessagePreparator preparator = new MimeMessagePreparator() {

            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

                helper.setSubject("You have successfully appointment");
                helper.setFrom("valentinmelen@gmail.com");
                helper.setTo(appointment.getDoctor().getEmail().getEmail());


                Map<String, Object> model = new HashMap<String, Object>();
                model.put("appointment", appointment);

                String text = geVelocityTemplateContent_Doctor(model);
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

    private MimeMessagePreparator getMessagePreparatorPatient(final Appointment appointment) {

        MimeMessagePreparator preparator = new MimeMessagePreparator() {

            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

                helper.setSubject("You have successfully appointment");
                helper.setFrom("valentinmelen@gmail.com");
                helper.setTo(appointment.getPatient().getEmail().getEmail());

                Map<String, Object> model = new HashMap<String, Object>();
                model.put("appointment", appointment);

                String text = geVelocityTemplateContent_Patient(model);
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

    public String geVelocityTemplateContent_Doctor(Map<String, Object> modelDoctor) {
        StringBuffer content = new StringBuffer();
        try {
            content.append(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                    "/vmtemplates/velocity_mailTemplate_doctorAppointment.vm", modelDoctor));
            return content.toString();
        } catch (Exception e) {
            System.out.println("Exception occured while processing velocity template:" + e.getMessage());
        }
        return "";
    }

    public String geVelocityTemplateContent_Patient(Map<String, Object> modelPatient) {
        StringBuffer content = new StringBuffer();
        try {
            content.append(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                    "/vmtemplates/velocity_mailTemplate_patientAppointment.vm", modelPatient));
            return content.toString();
        } catch (Exception e) {
            System.out.println("Exception occured while processing velocity template:" + e.getMessage());
        }
        return "";
    }

}
