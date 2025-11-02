package ma.projet.service;

import ma.projet.classes.Commande;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import java.util.List;

public class CommandeService implements IDao<Commande> {

    @Override
    public boolean create(Commande o) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        s.save(o);
        s.getTransaction().commit();
        s.close();
        return true;
    }

    @Override
    public boolean update(Commande o) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        s.update(o);
        s.getTransaction().commit();
        s.close();
        return true;
    }

    @Override
    public boolean delete(Commande o) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        s.delete(o);
        s.getTransaction().commit();
        s.close();
        return true;
    }

    @Override
    public Commande getById(int id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Commande c = s.get(Commande.class, id);
        s.close();
        return c;
    }

    @Override
    public List<Commande> getAll() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Commande> list = s.createQuery("from Commande", Commande.class).list();
        s.close();
        return list;
    }
}
