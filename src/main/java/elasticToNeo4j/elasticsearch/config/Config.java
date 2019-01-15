package elasticToNeo4j.elasticsearch.config;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "elasticToNeo4j.elasticsearch.repository")
@ComponentScan(basePackages = { "elasticToNeo4j.elasticsearch.service" })
public class Config {

//    @Value("${elasticsearch.home:/Users/y.glushenkov/elasticsearch}")
//    private String elasticsearchHome;

    @Value("${elasticsearch.cluster.name:docker-cluster}")
    private String clusterName;

    @Bean
    public Client client() {
        TransportClient client = null;
        try {
            final Settings elasticsearchSettings = Settings.builder()
//                    .put("client.transport.sniff", true) // TODO execption with a docker
//                    .put("path.home", elasticsearchHome)
                    .put("cluster.name", clusterName)
                    .build();
            client = new PreBuiltTransportClient(elasticsearchSettings);
            client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return client;
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchTemplate(client());
    }
}