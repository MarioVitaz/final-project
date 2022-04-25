package sk.ness.academy.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.dto.ArticleWithOutComments;
import sk.ness.academy.dto.Author;
import sk.ness.academy.dto.AuthorStats;
import sk.ness.academy.service.ArticleService;
import sk.ness.academy.service.AuthorService;
import sk.ness.academy.service.CommentService;

@RestController
public class BlogController {

  @Resource
  private ArticleService articleService;

  @Resource
  private AuthorService authorService;

  @Resource
  private CommentService commentsService;


  // ~~ Article
  @RequestMapping(value = "articles", method = RequestMethod.GET)

  public List<ArticleWithOutComments> getAllArticles() {

    try {
      return this.articleService.findAll();
    } catch (NullPointerException ne) {
      throw new NullPointerException("Empty database");
    }
  }

  @RequestMapping(value = "articles/{articleId}", method = RequestMethod.GET)
  public Article getArticle(@PathVariable final Integer articleId) {
    try {
      return this.articleService.findByID(articleId);
    } catch (NullPointerException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,"article " + articleId + " oes not exist");
    }

  }

  @RequestMapping(value = "articles/search/{searchText}", method = RequestMethod.GET)
  public List<ArticleWithOutComments> searchArticle(@PathVariable final String searchText) {
    return this.articleService.searchArticleWithText(searchText);
  }

  @RequestMapping(value = "articles", method = RequestMethod.PUT)
  public void addArticle(@RequestBody final Article article) {
    this.articleService.createArticle(article);
  }

  @DeleteMapping(value = "articles/{articleId}")
  public void deleteArticle(@PathVariable final Integer articleId) {
    try {
      this.articleService.deleteArticleWithId(articleId);
    } catch (IllegalArgumentException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping(value = "articles")
  public void deleteAllArticle() {
    this.articleService.deleteAllArticle();
  }

  // ~~ Author
  @RequestMapping(value = "authors", method = RequestMethod.GET)
  public List<Author> getAllAuthors() {
    return this.authorService.findAll();
  }

  @RequestMapping(value = "authors/stats", method = RequestMethod.GET)
  public List<AuthorStats> authorStats() {
    return this.authorService.getAllAuthorsStats();
  }

  // ~~ Comments
  @RequestMapping(value = "comments", method = RequestMethod.PUT)
  public void addComment(@RequestBody final Comment comment) {
    Article articel = articleService.findByID(comment.getArticleid());
    if(articel != null){
      this.commentsService.createComment(comment);
    }else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Article with " + comment.getArticleid() + "does not exist");
    }

  }

  @DeleteMapping(value = "comments/{commentId}")
  public void deleteCommentWithId(@PathVariable final Integer commentId) {
    try {
      this.commentsService.deleteCommentWithId(commentId);
    }catch (IllegalArgumentException e){
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
  }

  @RequestMapping(value = "comments", method = RequestMethod.GET)
  public List<Comment> getAllComments() {
    return this.commentsService.findAll();
  }

  @RequestMapping(value = "comments/{commentId}", method = RequestMethod.GET)
  public Comment findById(@PathVariable Integer commentId) {
    try {
      return this.commentsService.findById(commentId);
    } catch (IllegalArgumentException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping(value = "articles/{articleId}/comments")
  public void deleteAllCommentsOfArticle(@PathVariable final Integer articleId) {
    try {
      this.commentsService.deleteAllCommentsOfArticle(articleId);
    }catch (IllegalArgumentException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
  }
}
