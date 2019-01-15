package elasticToNeo4j.neo4j.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

import java.util.Collection;

@JsonIdentityInfo(generator = JSOGGenerator.class)
@RelationshipEntity(type = "ACTED_IN")
public class Role {
    @Id @GeneratedValue
    Long id;
    private Collection<String> roles;
    @StartNode
    private elasticToNeo4j.neo4j.domain.Person person;
    @EndNode
    private elasticToNeo4j.neo4j.domain.Movie movie;

    public Role() {
    }

    public Collection<String> getRoles() {
        return roles;
    }

    public elasticToNeo4j.neo4j.domain.Person getPerson() {
        return person;
    }

    public elasticToNeo4j.neo4j.domain.Movie getMovie() {
        return movie;
    }

    public void setRoles(Collection<String> roles) {
        this.roles = roles;
    }

    public void setPerson(elasticToNeo4j.neo4j.domain.Person person) {
        this.person = person;
    }

    public void setMovie(elasticToNeo4j.neo4j.domain.Movie movie) {
        this.movie = movie;
    }
}