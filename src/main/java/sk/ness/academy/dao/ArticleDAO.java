package sk.ness.academy.dao;

import java.util.List;

import sk.ness.academy.domain.Article;
import sk.ness.academy.dto.ArticleWithOutComments;

public interface ArticleDAO {

	  /** Returns {@link Article} with provided ID */
	  Article findByID(Integer articleId);

	  /** Returns all available {@link Article}s
	   * @return*/
	  List<ArticleWithOutComments> findAll();

	  /** Persists {@link Article} into the DB */
	  void persist(Article article);

      void deleteArticleWithId(Integer articleId);

	  void deleteAllArticles();

	List<ArticleWithOutComments> searchArticleWithText(String searchText);
}
