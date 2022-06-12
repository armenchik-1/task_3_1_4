package springBoot.web.dao;

import org.springframework.stereotype.Repository;
import springBoot.web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void removeUser(long id) {

        entityManager.remove(entityManager.find(User.class, id));

    }

   @Override
   public void updateUser(User user) {
       entityManager.merge(user);
   }

    // нельзя добавить пользователей с одинаковым email
    @Override
    public boolean isNotReg(String email) {
        return getAllUsers()
                .stream()
                .anyMatch((e) -> e.getEmail().hashCode() == email.hashCode());
    }

    @Override
    public User getUserById(long id) {
        return (User)entityManager.createQuery("From User where id =:pId")
                .setParameter("pId", id).getSingleResult();
    }

    @Override
    public User getUserByName(String email) {
        return (User) entityManager.createQuery("From User where email =:pEmail")
                .setParameter("pEmail", email).getSingleResult();
    }

}
