package org.example.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.example.entities.Artist;
import org.example.repository.context.DbContext;
import org.hibernate.HibernateException;

import java.util.List;

public class ArtistRepository implements Repository<Artist,Integer> {

    EntityManager em;

    public ArtistRepository() {
        em = DbContext.getInstance().getManager();
    }

    @Override
    public void save(Artist entity) {
        try {
            em.getTransaction().begin();
            em.persist(entity);
        }catch (HibernateException e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            throw e;
        }finally {
            em.getTransaction().commit();
        }
    }

    @Override
    public Artist findById(Integer id) {
        return em.find(Artist.class, id);
    }

    @Override
    public List<Artist> findAll() {
        return List.of();
    }

    @Override
    public void update(Artist entity) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Artist findByName(String name) {
        try {
            String jpql = "SELECT a FROM Artist a WHERE a.name = :name";
            return em.createQuery(jpql, Artist.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
