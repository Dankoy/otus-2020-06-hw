package ru.dankoy.otus.jpql.core.dao;

import ru.dankoy.otus.jpql.core.model.User;
import ru.dankoy.otus.jpql.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface UserDao {
    Optional<User> findById(long id);

    long insertUser(User user);

    void updateUser(User user);

    void insertOrUpdate(User user);

    SessionManager getSessionManager();
}
