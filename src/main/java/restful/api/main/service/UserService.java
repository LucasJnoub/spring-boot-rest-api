package restful.api.main.service;

import restful.api.main.domain.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    User findById(Long id);

    List<User> findAll();
    User create(User user);

    User update(Long id, User user);
    void delete(Long id);

    void partialUserUpdate(Long id, Map<String, Object> updates);

}
