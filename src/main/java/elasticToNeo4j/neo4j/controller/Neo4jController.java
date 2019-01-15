package elasticToNeo4j.neo4j.controller;

import elasticToNeo4j.neo4j.domain.Movie;
import elasticToNeo4j.neo4j.service.CRUDNeo4jService;
import elasticToNeo4j.neo4j.service.MovieService;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Map;

import static elasticToNeo4j.neo4j.service.CRUDNeo4jService.*;

@RestController
@RequestMapping("/neo4j")
public class Neo4jController {

    private static Logger logger = LoggerFactory.getLogger(Neo4jController.class);

    @Autowired
    private CRUDNeo4jService crudNeo4jService;
    @Autowired
    private MovieService movieService;

    @RequestMapping("/savingANewMovieObject")
    public void savingANewMovieObject() {
        crudNeo4jService.savingANewMovieObject();
        logger.info("savingANewMovieObject success");
    }

    @RequestMapping("/retrievingAnExistingMovieObjectsByAPartOfTitle")
    public Collection<Movie> retrievingAnExistingMovieObjectsByAPartOfTitle() {
        Collection<Movie> movies = crudNeo4jService.retrievingAnExistingMovieObjectsByAPartOfTitle(DEFAULT_PART_OF_TITLE_NAME);
        logger.info("retrievingAnExistingMovieObjectsByAPartOfTitle success");

        return movies;
    }

    @RequestMapping("/retrievingAnExistingMovieObjectByTitle")
    public Movie retrievingAnExistingMovieObjectByTitle() {
        Movie movie = crudNeo4jService.retrievingAnExistingMovieObjectByTitle(DEFAULT_TITLE_NAME);
        logger.info("retrievingAnExistingMovieObjectByTitle success");

        return movie;
    }

    @RequestMapping("/retrieveAllTheMovies")
    public Collection<Movie> retrieveAllTheMovies() {
        Collection<Movie> movies = crudNeo4jService.retrieveAllTheMovies();
        logger.info("retrieveAllTheMovies success");

        return movies;
    }

    @RequestMapping("/deleteAnExistingMovie")
    public void deleteAnExistingMovie() {
        crudNeo4jService.deleteAnExistingMovie();
        logger.info("retrieveAllTheMovies success");
    }

    @RequestMapping("/deleteAllInsertedData")
    public void deleteAllInsertedData() {
        crudNeo4jService.deleteAllInsertedData();
        logger.info("deleteAllInsertedData success");
    }

    @RequestMapping("/graph/{limit}")
    public Map<String, Object> graph(@PathVariable int limit) {
        return movieService.graph(limit);
    }
}