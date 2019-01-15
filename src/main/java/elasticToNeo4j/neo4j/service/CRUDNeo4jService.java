package elasticToNeo4j.neo4j.service;


import elasticToNeo4j.neo4j.domain.Movie;
import elasticToNeo4j.neo4j.domain.Person;
import elasticToNeo4j.neo4j.domain.Role;
import elasticToNeo4j.neo4j.repository.MovieRepository;
import elasticToNeo4j.neo4j.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Service
public class CRUDNeo4jService {

    public static String DEFAULT_PART_OF_TITLE_NAME = "Italian";
    public static String DEFAULT_TITLE_NAME = "The " + DEFAULT_PART_OF_TITLE_NAME + " Job";

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private MovieRepository movieRepository;

    @Transactional
    public void savingANewMovieObject() {
        Movie italianJob = new Movie();

        italianJob.setTitle(DEFAULT_TITLE_NAME);
        italianJob.setReleased(1999);
        movieRepository.save(italianJob);

        Person mark = new Person();
        mark.setName("Mark Wahlberg");
        personRepository.save(mark);

        Role charlie = new Role();
        charlie.setMovie(italianJob);
        charlie.setPerson(mark);

        Collection<String> roleNames = new HashSet();
        roleNames.add("Charlie Croker");
        charlie.setRoles(roleNames);

        List<Role> roles = new ArrayList();
        roles.add(charlie);
        italianJob.setRoles(roles);

        movieRepository.save(italianJob);
    }

    public Collection<Movie> retrievingAnExistingMovieObjectsByAPartOfTitle(String title) {
        Collection<Movie> result = movieRepository.findByTitleContaining(title);

        return result;
    }

    public Movie retrievingAnExistingMovieObjectByTitle(String title) {
        Movie result = movieRepository.findByTitle(title);

        return result;
    }

    public Collection<Movie> retrieveAllTheMovies() {
        Collection<Movie> result = (Collection<Movie>) movieRepository.findAll();

        return result;
    }

    @Transactional
    public void deleteAnExistingMovie() {
        movieRepository.delete(movieRepository.findByTitle(DEFAULT_TITLE_NAME));
    }

    @Transactional
    public void deleteAllInsertedData() {
        movieRepository.deleteAll();
    }
}
