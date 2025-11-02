package ma.projet.service;

import ma.projet.dao.IDao;
import ma.projet.classes.Produit;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.List;

public class ProduitService implements IDao<Produit> {

    @Override
    public boolean create(Produit p) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(p);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Produit p) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.update(p);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Produit p) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.delete(p);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Produit getById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Produit.class, id);
        }
    }

    @Override
    public List<Produit> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Produit", Produit.class).list();
        }
    }

    // --- Méthodes spécifiques ---
    public List<Produit> findByCategorie(int idCategorie) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "from Produit p where p.categorie.id = :idCat", Produit.class)
                    .setParameter("idCat", idCategorie)
                    .list();
        }
    }

    public List<Produit> findProduitsCommandesEntreDates(Date date1, Date date2) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "select distinct l.produit from LigneCommandeProduit l " +
                                    "where l.commande.date between :d1 and :d2", Produit.class)
                    .setParameter("d1", date1)
                    .setParameter("d2", date2)
                    .list();
        }
    }

    public List<Produit> findProduitsParCommande(int idCommande) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "select l.produit from LigneCommandeProduit l " +
                                    "where l.commande.id = :idCom", Produit.class)
                    .setParameter("idCom", idCommande)
                    .list();
        }
    }

    public List<Produit> findPrixSuperieur100() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createNamedQuery("Produit.findPrixSuperieur100", Produit.class).list();
        }
    }

    public void afficherProduitsParCategorie(int idCategorie) {
        List<Produit> produits = findByCategorie(idCategorie);
        if (produits.isEmpty()) {
            System.out.println("Aucun produit trouvé pour cette catégorie.");
        } else {
            System.out.println("Produits de la catégorie " + idCategorie + " :");
            for (Produit p : produits) {
                System.out.println("- " + p.getReference() + " | " + p.getPrix() + " DH");
            }
        }
    }
}
