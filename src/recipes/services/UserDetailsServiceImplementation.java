package recipes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import recipes.models.User;
import recipes.models.UserDetailsImplementation;
import recipes.repositories.UserRepository;

import java.util.Optional;


@Service
public class UserDetailsServiceImplementation implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> searchResult = userRepository.findUserByEmail(username);
        if (searchResult.isEmpty()) {
            throw new UsernameNotFoundException(username + " not found!");
        }
        return new UserDetailsImplementation(searchResult.get());
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}
