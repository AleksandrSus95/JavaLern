package hibernate;

import hibernate.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;
import java.util.UUID;

public class HibernateUtils {
    private static SessionFactory sessionFactory;

    private HibernateUtils(){}

    public static SessionFactory getSessionFactory() {
        if(sessionFactory == null){
            Configuration configuration = new Configuration();
//            configuration.addAnnotatedClass(User.class);
            configuration.configure();
            sessionFactory = configuration.buildSessionFactory();
        }
        return sessionFactory;
    }

    public static User addNewUser(String userLogin, String userPassword) {
        User user = User.builder().userId(UUID.randomUUID())
                .login(userLogin)
                .password(userPassword)
                .registrationDate(LocalDate.now())
                .build();

        try (SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            return user;
        }
    }

}
