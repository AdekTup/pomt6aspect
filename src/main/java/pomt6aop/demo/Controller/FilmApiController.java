package pomt6aop.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pomt6aop.demo.Model.FilmApi;
import pomt6aop.demo.Service.FilmApiService;
import pomt6aop.demo.Service.FilmEmailService;

import java.util.List;
import java.util.Optional;

@Controller
public class FilmApiController {
    public FilmApiService filmService;


    ///        try {
//            filmEmailService.sendEmailWithAttachment(filmApi);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    @Autowired
    public FilmApiController(FilmApiService filmService) {
        this.filmService = filmService;
    }

    //@FilmApiAspectAnnotation
    @GetMapping("/filmapi")
    public String getFilmApi(Model model) {
        List<FilmApi> filmApiList = filmService.getFilmApiList();
        model.addAttribute("filmApi", filmApiList.get(0));
        return "filmDataShow";
    }

    @GetMapping("/filmapilist")
    public String getFilmApiList(Model model) {
        FilmApi filmApi = new FilmApi();
        List<FilmApi> filmApiList = filmService.getFilmApiList();
        model.addAttribute("filmApiList", filmApiList);
        model.addAttribute("filmApi", filmApi);
        return "filmListBase";
    }

    @GetMapping("/filmapimylist")
    public String getFilmApiMyList(Model model) {
        FilmApi filmApi = new FilmApi();
        List<FilmApi> filmApiList = filmService.getMyfilmApiList();
        model.addAttribute("filmApiList", filmApiList);
        model.addAttribute("filmApi", filmApi);
        return "filmMyListBase";
    }

    @PostMapping("/filmshow")
    public String showFilm(FilmApi filmApiGet, Model model) {
        Optional<FilmApi> getfilmApi = filmService.getCarByTitle(filmApiGet.getTitle());
        if (getfilmApi.isPresent()) {
            model.addAttribute("filmApi", getfilmApi.get());
            return "filmDataShow";
        }
        return "not_found_film";
    }

    @FilmApiAspectAnnotation
    @PostMapping("/filmadd")
    public String addFilmToMyList(FilmApi filmApiGet, Model model) {
        Optional<FilmApi> getfilmApi = filmService.getCarByTitle(filmApiGet.getTitle());
        if (getfilmApi.isPresent()) {
            filmService.addFilmToMyList(getfilmApi.get());
            model.addAttribute("filmApiList", filmService.getMyfilmApiList());
            return "filmMyListBase";
        }
        return "not_found_film";
    }
}
