package ma.projet.service;

import ma.projet.classes.LigneCommandeProduit;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import java.util.List;

public class LigneCommandeService implements IDao<LigneCommandeProduit> {

    @Override
    public boolean create(LigneCommandeProduit o) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        s.save(o);
        s.getTransaction().commit();
        s.close();
        return true;
    }

    @Override
    public boolean update(LigneCommandeProduit o) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        s.update(o);
        s.getTransaction().commit();
        s.close();
        return true;
    }

    @Override
    public boolean delete(LigneCommandeProduit o) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        s.delete(o);
        s.getTransaction().commit();
        s.close();
        return true;
    }

    @Override
    public LigneCommandeProduit getById(int id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        LigneCommandeProduit lc = s.get(LigneCommandeProduit.class, id);
        s.close();
        return lc;
    }

    @Override
    public List<LigneCommandeProduit> getAll() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<LigneCommandeProduit> list = s.createQuery("from LigneCommandeProduit", LigneCommandeProduit.class).list();
        s.close();
        return list;
    }

    public List<LigneCommandeProduit> findByCommande(int idCommande) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "from LigneCommandeProduit l where l.commande.id = :idCom", LigneCommandeProduit.class)
                    .setParameter("idCom", idCommande)
                    .list();
        }
    }
}
