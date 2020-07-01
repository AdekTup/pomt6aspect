package pomt6aop.demo.Controller;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pomt6aop.demo.Model.FilmApi;
import pomt6aop.demo.Service.FilmEmailService;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Arrays;

@Aspect
@Component
public class FilmApiAspect {
    public FilmEmailService filmEmailService;

    @Autowired
    public FilmApiAspect(FilmEmailService filmEmailService) {
        this.filmEmailService = filmEmailService;
    }

    @After("@annotation(FilmApiAspectAnnotation)")
    private void AfterHello(JoinPoint joinPoint) {
//        System.out.println("Agruments Passed=" + Arrays.toString(joinPoint.getArgs()));
        FilmApi filmApi = (FilmApi)joinPoint.getArgs()[0];
//        System.out.println("FA: " + filmApi);
        try {
            filmEmailService.sendEmailWithAttachment(filmApi);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Before("@annotation(FilmApiAspectAnnotation)")
    private void BeforeHello() {
        System.out.println("Before Hello");
    }
}
