package ma.projet.service;

import ma.projet.classes.Categorie;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import java.util.List;

public class CategorieService implements IDao<Categorie> {

    @Override
    public boolean create(Categorie o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(o);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Categorie o) { /* similar */ return true; }
    @Override
    public boolean delete(Categorie o) { /* similar */ return true; }
    @Override
    public Categorie getById(int id) { /* similar */ return null; }
    @Override
    public List<Categorie> getAll() { /* similar */ return null; }
}
