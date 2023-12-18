package com.ibookleague.user;


import com.ibookleague.book.exception.DataFoundException;
import com.ibookleague.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User create(String email, String username, String password)
    {
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(user);
        return user;
    }

    public User getUser(String email)
    {
        Optional<User> user = this.userRepository.findByEmail(email);
        if (user.isPresent()) { return user.get(); }
        else { throw new DataFoundException("User not found"); }
    }
}
