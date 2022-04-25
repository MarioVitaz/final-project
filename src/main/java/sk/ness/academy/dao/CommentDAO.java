package sk.ness.academy.dao;

import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;

import java.util.List;

public interface CommentDAO {
    void persist(Comment comment);

    void deleteCommentWithId(Integer commentId);

    Comment findById(Integer commentId);

    List<Comment> findAll();

    void deleteAllCommentsOfArticle(Integer articleId);
}
