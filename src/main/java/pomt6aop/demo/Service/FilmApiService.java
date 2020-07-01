package pomt6aop.demo.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pomt6aop.demo.Model.FilmApi;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FilmApiService {
    List<FilmApi> filmApiList;
    List<FilmApi> myfilmApiList;

    public FilmApiService() {
        this.filmApiList = getFilmApiList_from_File();
        this.myfilmApiList = new ArrayList<>();
    }

    public List<FilmApi> getFilmApiList() {
        return filmApiList;
    }

    public void setFilmApiList(List<FilmApi> filmApiList) {
        this.filmApiList = filmApiList;
    }

    public List<FilmApi> getMyfilmApiList() { return myfilmApiList; }

    public void setMyfilmApiList(List<FilmApi> myfilmApiList) {
        this.myfilmApiList = myfilmApiList;
    }

    public Optional<FilmApi> getCarByTitle(String filmApiGet) {
        return filmApiList.stream().filter(film -> film.getTitle().equals(filmApiGet)).findFirst();

    }

    public void addFilmToMyList(FilmApi filmApi) {
        System.out.println("addFilmToMyList - 2");
        myfilmApiList.add(filmApi);
    }

    private List<FilmApi> getFilmApiList_from_File() {
        List<FilmApi> filmApiList = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();
        ObjectMapper mapper = new ObjectMapper();
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("Film.JSON");

        try  {
            Reader reader = new InputStreamReader(inputStream);
            Object obj = jsonParser.parse(reader);
            JSONArray filmList = (JSONArray) obj;
            //Iterate over filmList array
            for(int i = 0; i < filmList.size(); i++) {
                JSONObject jsfilmApi = (JSONObject)filmList.get(i);
                //System.out.println("FAJ: " + jsfilmApi .toString());
                String recapi = jsfilmApi.toString();
                FilmApi filmApi = mapper.readValue(recapi, FilmApi.class);
                //System.out.println("FA: " + filmApi.toString());
                filmApiList.add(filmApi);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return filmApiList;
    }
}
