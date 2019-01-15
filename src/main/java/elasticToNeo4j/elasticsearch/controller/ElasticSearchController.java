package elasticToNeo4j.elasticsearch.controller;

import elasticToNeo4j.elasticsearch.domain.Article;
import elasticToNeo4j.elasticsearch.domain.Author;
import elasticToNeo4j.elasticsearch.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.regexpQuery;

@RestController
@RequestMapping("/elasticsearch")
public class ElasticSearchController {

    private static Logger logger = LoggerFactory.getLogger(ElasticSearchController.class);

    @Autowired
    private ArticleService articleService;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    final private static String TITLE_NAME = "title aa title";
    final private static String AUTHOR = "vasy";

    @PostMapping("/saveArticle")
    public void saveArticle(@RequestBody Article article) {
        articleService.save(article);
        logger.info("saveArticle success");
    }

    @RequestMapping("/saveArticleStub")
    public void saveArticleStub() {
        Article article = new Article();
        article.setId("1");

        Author author = new Author();
        author.setName("vasy");
        article.setAuthors(Collections.singletonList(author));

        String[] tags = {"Cheese", "Pepperoni", "Black Olives"};
        article.setTags(tags);

        article.setTitle(TITLE_NAME);

        articleService.save(article);
        logger.info("saveArticle success");
    }

    @RequestMapping("/methodNameBasedQuery")
    public Page<Article> methodNameBasedQuery() {
        String nameToFind = AUTHOR;
        Page<Article> articleByAuthorName
                = articleService.findByAuthorName(nameToFind, PageRequest.of(0, 10));

        return articleByAuthorName;
    }

    @RequestMapping("/aCustomQuery")
    public List<Article> aCustomQuery() {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withFilter(regexpQuery("title", ".*title.*"))
                .build();
        List<Article> articles = elasticsearchTemplate.queryForList(searchQuery, Article.class);

        return articles;
    }

    @RequestMapping("/update")
    public void update() {
        String articleTitle = TITLE_NAME;
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchQuery("title", articleTitle).minimumShouldMatch("5%"))
                .build();

        List<Article> articles = elasticsearchTemplate.queryForList(searchQuery, Article.class);

        articles.forEach(article -> articleService.fakeUpdate(article));
    }

    @RequestMapping("/delete")
    public void delete() {
        String articleTitle = TITLE_NAME;
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchQuery("title", articleTitle).minimumShouldMatch("75%"))
                .build();

        List<Article> articles = elasticsearchTemplate.queryForList(searchQuery, Article.class);


        articles.forEach(article -> articleService.fakeDelete(article));
    }
}