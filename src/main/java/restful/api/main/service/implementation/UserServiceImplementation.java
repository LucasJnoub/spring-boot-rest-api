package restful.api.main.service.implementation;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import restful.api.main.domain.model.User;
import restful.api.main.domain.repository.UserRepository;
import restful.api.main.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("User not found with id: " + id)
        );
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User create(User userToCreate) {
        if (userRepository.existsByAccountNumber(userToCreate.getAccount().getNumber())) {
            throw new IllegalArgumentException("Account number already exists with id: " + userToCreate.getId());
        }
        return userRepository.save(userToCreate);
    }

    @Override
    public User update(Long id, User userToUpdate) {
        return userRepository.save(userToUpdate);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }


    @Override
    public void partialUserUpdate(Long id, Map<String, Object> updates) {
        User existingUser = userRepository.findById(id).orElse(null);

        if (existingUser != null) {
            if (updates.containsKey("name")) {
                existingUser.setName((String) updates.get("name"));
            }

            userRepository.save(existingUser);
        }
    }


}
