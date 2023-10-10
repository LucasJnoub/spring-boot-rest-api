package restful.api.main.service.implementation;
import org.springframework.stereotype.Service;
import restful.api.main.domain.model.*;
import restful.api.main.domain.repository.UserRepository;
import restful.api.main.service.UserService;
import java.math.BigDecimal;
import java.util.*;

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
        User existingUser = userRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("User not found with id: " + id)
        );

        if (existingUser != null) {
            if (!updates.isEmpty()) {
                if (updates.containsKey("name")) {
                    existingUser.setName((String) updates.get("name"));
                }

                if (updates.containsKey("account")) {
                    Map<String, Object> accountUpdates = (Map<String, Object>) updates.get("account");
                    if (accountUpdates != null) {
                        Account userAccount = existingUser.getAccount();
                        if (userAccount != null) {
                            if (accountUpdates.containsKey("number")) {
                                userAccount.setNumber((String) accountUpdates.get("number"));
                            }
                            if (accountUpdates.containsKey("agency")) {
                                userAccount.setAgency((String) accountUpdates.get("agency"));
                            }
                            Object balanceValue = accountUpdates.get("balance");
                            if (balanceValue instanceof Integer) {
                                Integer balanceInteger = (Integer) balanceValue;
                                String balanceString = balanceInteger.toString();
                                userAccount.setBalance(new BigDecimal(balanceString));
                            } else if (balanceValue instanceof String) {
                                String balanceString = (String) balanceValue;
                                userAccount.setBalance(new BigDecimal(balanceString));
                            }

                            if (accountUpdates.containsKey("limit")) {
                                BigDecimal limit = new BigDecimal((String) accountUpdates.get("limit"));
                                userAccount.setLimit(limit);
                            }
                        }
                    }
                }

                if (updates.containsKey("card")) {
                    Map<String, Object> cardUpdates = (Map<String, Object>) updates.get("card");
                    if (cardUpdates != null) {
                        Card userCard = existingUser.getCard();
                        if (userCard != null) {
                            if (cardUpdates.containsKey("number")) {
                                userCard.setNumber((String) cardUpdates.get("number"));
                            }
                            Object cardLimitValue = cardUpdates.get("limit");
                            if (cardLimitValue != null) {
                                if (cardLimitValue instanceof Integer) {
                                    Integer cardLimitInteger = (Integer) cardLimitValue;
                                    String cardLimitString = cardLimitInteger.toString();
                                    userCard.setLimit(new BigDecimal(cardLimitString));
                                } else if (cardLimitValue instanceof String) {
                                    String cardLimitString = (String) cardLimitValue;
                                    userCard.setLimit(new BigDecimal(cardLimitString));
                                }
                            }
                        }
                    }
                }

                userRepository.save(existingUser);
            }
        }
    }
}
