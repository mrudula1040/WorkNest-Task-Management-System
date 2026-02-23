package com.worknest.service;

import com.worknest.dto.UserBasicDto;
import com.worknest.entity.Role;
import com.worknest.entity.User;
import com.worknest.exception.UserAlreadyExistsException;
import com.worknest.mapper.UserMapper;
import com.worknest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    // ================= REGISTER =================
    public void register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
    }

    // ================= LOGIN =================
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email"));

        if (!passwordEncoder.matches(password,user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        return user;
    }

    // ================= HR =================
    public List<UserBasicDto> getAllEmployees() {
        return userRepository.findByRole(Role.USER)
                .stream()
                .map(userMapper::toBasicDto)
                .toList();
    }

    public List<User> getAllEmployeesEntity() {
        return userRepository.findByRole(Role.USER);
    }

    public List<User> getAllManagers() {
        return userRepository.findByRole(Role.MANAGER);
    }

   /* public void assignManager(Long userId, Long managerId) {
        User user = getUserById(userId);
        User manager = getUserById(managerId);

        if (!"MANAGER".equalsIgnoreCase(manager.getRole())) {
            throw new RuntimeException("Selected user is not a manager");
        }

        user.setManager(manager);
        userRepository.save(user);
    }*/

    public void assignManager(Long userId, Long managerId) {

        User user = getUserById(userId);
        User manager = getUserById(managerId);

        // 🔐 BUSINESS VALIDATION
        if (user.getRole()!= Role.USER) {
            throw new RuntimeException("Only employees can be assigned a manager");
        }

        if (manager.getRole()!=Role.MANAGER) {
            throw new RuntimeException("Selected user is not a manager");
        }

        user.setManager(manager);
        userRepository.save(user);
    }

    // ================= MANAGER =================
    public List<User> getTeamMembers(User manager) {
        return userRepository.findByManager(manager);
    }

    // ================= COMMON =================
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public long getTotalUsersCount() {
        return userRepository.countByRole(Role.USER);
    }
}
