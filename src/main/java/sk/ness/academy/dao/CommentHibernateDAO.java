package sk.ness.academy.dao;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class CommentHibernateDAO implements CommentDAO{

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public void persist(Comment comment) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(comment);
    }

    @Override
    public void deleteCommentWithId(Integer commentId) {
        Comment comment = this.sessionFactory.getCurrentSession().get(Comment.class, commentId);
        sessionFactory.getCurrentSession().delete(comment);

    }

    @Override
    public Comment findById(final Integer commentId) {
        return this.sessionFactory.getCurrentSession().get(Comment.class, commentId);
    }

    @Override
    public List<Comment> findAll() {
        return this.sessionFactory.getCurrentSession().createSQLQuery("select * from comments").addEntity(Comment.class).list();
    }

    @Override
    public void deleteAllCommentsOfArticle(Integer articleId) {
        List<Comment> list = this.sessionFactory.getCurrentSession().createSQLQuery("select * from comments").addEntity(Comment.class).list();
        list.forEach(A->this.sessionFactory.getCurrentSession().delete(A));
    }

}
