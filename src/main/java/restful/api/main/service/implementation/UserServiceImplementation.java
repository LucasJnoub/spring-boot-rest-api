package restful.api.main.service.implementation;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import restful.api.main.domain.model.*;
import restful.api.main.domain.repository.UserRepository;
import restful.api.main.service.UserService;

import java.math.BigDecimal;
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
            // Verifica se o mapa de atualizações não está vazio
            if (!updates.isEmpty()) {
                // Atualiza o nome do usuário, se presente nas atualizações
                if (updates.containsKey("name")) {
                    existingUser.setName((String) updates.get("name"));
                }

                // Atualiza a conta do usuário, se presente nas atualizações
                if (updates.containsKey("account")) {
                    Map<String, Object> accountUpdates = (Map<String, Object>) updates.get("account");
                    if (accountUpdates != null) {
                        Account userAccount = existingUser.getAccount();
                        if (userAccount != null) {
                            // Atualiza o número da conta, se presente nas atualizações da conta
                            if (accountUpdates.containsKey("number")) {
                                userAccount.setNumber((String) accountUpdates.get("number"));
                            }
                            // Atualiza a agência, se presente nas atualizações da conta
                            if (accountUpdates.containsKey("agency")) {
                                userAccount.setAgency((String) accountUpdates.get("agency"));
                            }
                            // Atualiza o saldo, se presente nas atualizações da conta
                            if (accountUpdates.containsKey("balance")) {
                                Double balanceValue = (Double) accountUpdates.get("balance");
                                BigDecimal balance = BigDecimal.valueOf(balanceValue);
                                userAccount.setBalance(balance);
                            }
                            // Atualiza o limite, se presente nas atualizações da conta
                            if (accountUpdates.containsKey("limit")) {
                                Double limitValue = (Double) accountUpdates.get("limit");
                                BigDecimal limit = BigDecimal.valueOf(limitValue);
                                userAccount.setLimit(limit);
                            }
                        }
                    }
                }

                // Atualiza os recursos do usuário, se presente nas atualizações
                if (updates.containsKey("features")) {
                    List<Map<String, Object>> featureUpdates = (List<Map<String, Object>>) updates.get("features");
                    if (featureUpdates != null) {
                        List<Feature> userFeatures = existingUser.getFeatures();
                        if (userFeatures != null) {
                            // Limpa os recursos existentes para adicionar os atualizados
                            userFeatures.clear();
                            for (Map<String, Object> featureUpdate : featureUpdates) {
                                Feature feature = new Feature();
                                // Atualiza o ícone do recurso, se presente nas atualizações
                                if (featureUpdate.containsKey("icon")) {
                                    feature.setIcon((String) featureUpdate.get("icon"));
                                }
                                // Atualiza a descrição do recurso, se presente nas atualizações
                                if (featureUpdate.containsKey("description")) {
                                    feature.setDescription((String) featureUpdate.get("description"));
                                }
                                userFeatures.add(feature);
                            }
                        }
                    }
                }

                // Atualiza o cartão do usuário, se presente nas atualizações
                if (updates.containsKey("card")) {
                    Map<String, Object> cardUpdates = (Map<String, Object>) updates.get("card");
                    if (cardUpdates != null) {
                        Card userCard = existingUser.getCard();
                        if (userCard != null) {
                            // Atualiza o número do cartão, se presente nas atualizações do cartão
                            if (cardUpdates.containsKey("number")) {
                                userCard.setNumber((String) cardUpdates.get("number"));
                            }
                            // Atualiza o limite do cartão, se presente nas atualizações do cartão
                            if (cardUpdates.containsKey("limit")) {
                                Double cardLimitValue = (Double) cardUpdates.get("limit");
                                BigDecimal cardLimit = BigDecimal.valueOf(cardLimitValue);
                                userCard.setLimit(cardLimit);
                            }
                        }
                    }
                }

                // Atualiza as notícias do usuário, se presente nas atualizações
                if (updates.containsKey("news")) {
                    List<Map<String, Object>> newsUpdates = (List<Map<String, Object>>) updates.get("news");
                    if (newsUpdates != null) {
                        List<News> userNews = existingUser.getNews();
                        if (userNews != null) {
                            // Limpa as notícias existentes para adicionar as atualizadas
                            userNews.clear();
                            for (Map<String, Object> newsUpdate : newsUpdates) {
                                News news = new News();
                                // Atualiza o ícone da notícia, se presente nas atualizações
                                if (newsUpdate.containsKey("icon")) {
                                    news.setIcon((String) newsUpdate.get("icon"));
                                }
                                // Atualiza a descrição da notícia, se presente nas atualizações
                                if (newsUpdate.containsKey("description")) {
                                    news.setDescription((String) newsUpdate.get("description"));
                                }
                                userNews.add(news);
                            }
                        }
                    }
                }

                // Salva o usuário atualizado no repositório
                userRepository.save(existingUser);
            }
        }
    }



}
