package elasticToNeo4j.elasticsearch.service;

import java.util.Optional;

import elasticToNeo4j.elasticsearch.domain.Article;
import elasticToNeo4j.elasticsearch.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public Article save(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public Optional<Article> findOne(String id) {
        return articleRepository.findById(id);
    }

    @Override
    public Iterable<Article> findAll() {
        return articleRepository.findAll();
    }

    @Override
    public Page<Article> findByAuthorName(String name, Pageable pageable) {
        return articleRepository.findByAuthorsName(name, pageable);
    }

    @Override
    public Page<Article> findByAuthorNameUsingCustomQuery(String name, Pageable pageable) {
        return articleRepository.findByAuthorsNameUsingCustomQuery(name, pageable);
    }

    @Override
    public Page<Article> findByFilteredTagQuery(String tag, Pageable pageable) {
        return articleRepository.findByFilteredTagQuery(tag, pageable);
    }

    @Override
    public Page<Article> findByAuthorsNameAndFilteredTagQuery(String name, String tag, Pageable pageable) {
        return articleRepository.findByAuthorsNameAndFilteredTagQuery(name, tag, pageable);
    }

    @Override
    public long count() {
        return articleRepository.count();
    }

    @Override
    public void delete(Article article) {
        articleRepository.delete(article);
    }

    @Override
    public void fakeUpdate(Article article) {
        article.setTitle("Getting started title with Search Engines");
        this.save(article);
    }

    @Override
    public void fakeDelete(Article article) {
        this.delete(article);
    }
}