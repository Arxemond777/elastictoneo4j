package elasticToNeo4j.neo4j.service;

import elasticToNeo4j.neo4j.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class MovieService {

    private static Logger logger = LoggerFactory.getLogger(MovieService.class);

    @Autowired
    private MovieRepository movieRepository;

    private Map<String, Object> toD3Format(Iterator<Map<String, Object>> result) {
        List<Map<String, Object>> nodes = new ArrayList<>();
        List<Map<String, Object>> rels = new ArrayList<>();
        int i = 0;
        while (result.hasNext()) {
            Map<String, Object> row = result.next();
            nodes.add(map("title", row.get("movie"), "label", "movie"));
            int target = i;
            i++;
            for (Object name : (String[]) row.get("cast")) {
                Map<String, Object> actor = map("title", name, "label", "actor");
                int source = nodes.indexOf(actor);
                if (source == -1) {
                    nodes.add(actor);
                    source = i++;
                }
                rels.add(map("source", source, "target", target));
            }
        }
        return map("nodes", nodes, "links", rels);
    }

    private Map<String, Object> map(String key1, Object value1, String key2, Object value2) {
        Map<String, Object> result = new HashMap<>(2);
        result.put(key1, value1);
        result.put(key2, value2);
        return result;
    }

    public Map<String, Object> graph(int limit) {
        Iterator<Map<String, Object>> result = movieRepository.graph(limit).iterator();

        logger.info("Limit {}", limit);
        return toD3Format(result);
    }
}
