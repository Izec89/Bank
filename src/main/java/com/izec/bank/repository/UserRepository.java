package com.izec.bank.repository;

import com.izec.bank.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;


import java.util.List;

@Repository
@AllArgsConstructor
@Transactional
public class UserRepository {

    @PersistenceContext
    public EntityManager entityManager;

    /**
     * Get user from DB by ID
     *
     * @param id
     * @return
     */
    public User findById(long id) {
        return entityManager.find(User.class, id);
    }

    /**
     * The method saves the user to the DB
     *
     * @param user
     * @return
     */
    public long save(User user) {
        System.out.println(user);
        entityManager.persist(user);
        return user.getId();
    }

    /**
     * Returns all users
     *
     * @return
     */
    public List<User> getAll() {
        return entityManager.createQuery("from User")
                .getResultList();
    }

    /**
     * Returns user by phone number
     *
     * @param phoneNumber
     * @return
     */
    public User findByNumber(String phoneNumber) {
        return entityManager.createQuery("from User where phoneNumber = :phoneNumber", User.class)
                .setParameter("phoneNumber", phoneNumber)
                .getSingleResult();
    }

    /**
     * Returns user by email
     *
     * @param email
     * @return
     */
    public User findByEmail(String email) {
        return entityManager.createQuery("from User where email = :email", User.class)
                .setParameter("email", email)
                .getSingleResult();
    }


}
