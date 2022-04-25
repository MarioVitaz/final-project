package sk.ness.academy.dao;

import java.util.List;

import javax.annotation.Resource;
import javax.print.attribute.standard.NumberUp;

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import org.springframework.web.server.ResponseStatusException;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.dto.ArticleWithOutComments;
import sk.ness.academy.exeption.ApiRequestExeption;

@Repository
public class ArticleHibernateDAO implements ArticleDAO {

  @Resource(name = "sessionFactory")
  private SessionFactory sessionFactory;

  @Resource
  private CommentDAO commentDAO;

  @Override
  public Article findByID(final Integer articleId) {
   try {
     Article article = this.sessionFactory.getCurrentSession().get(Article.class, articleId);
      //Hibernate.initialize(article.getComments());

      return article;
    }catch (NullPointerException e){
      throw new NullPointerException("Article with " + articleId + "does not exist");
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<ArticleWithOutComments> findAll() {
    //return this.sessionFactory.getCurrentSession().createSQLQuery("select * from articles").addEntity(ArticleWithComments.class).list();
    return this.sessionFactory.getCurrentSession().createQuery("select new sk.ness.academy.dto.ArticleWithOutComments(id, title, text, author,createTimestamp) from Article", ArticleWithOutComments.class).list();
  }

  @Override
  public void persist(final Article article) {
    this.sessionFactory.getCurrentSession().saveOrUpdate(article);
  }

  @Override
  public void deleteArticleWithId(Integer articleId) {
    Article art = (Article) this.sessionFactory.getCurrentSession().get(Article.class, articleId);
    List<Comment> commentsList = this.sessionFactory.getCurrentSession().createSQLQuery("select * from comments where articleid = " + articleId).list();
      try {
        this.sessionFactory.getCurrentSession().delete(art);
        this.sessionFactory.getCurrentSession().flush();
      } catch (RuntimeException runex) {
        throw runex;
      }
    }


  @Override
  public void deleteAllArticles() {
    this.sessionFactory.getCurrentSession().delete(findAll());
  }

  @Override
  public List<ArticleWithOutComments> searchArticleWithText(String searchText) {
    return this.sessionFactory.getCurrentSession()
            .createQuery("select new sk.ness.academy.dto.ArticleWithOutComments(id, title, text, author,createTimestamp) " +
                    "from Article where author like '%"+ searchText+ "%' OR title like '%"+ searchText+ "%' or text like '%"+ searchText+ "%'", ArticleWithOutComments.class).list();

  }

}
