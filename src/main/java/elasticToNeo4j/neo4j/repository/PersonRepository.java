package elasticToNeo4j.neo4j.repository;

import elasticToNeo4j.neo4j.domain.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends Neo4jRepository<Person, Long> {
    //
}
