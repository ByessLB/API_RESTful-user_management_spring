package com.learn.api.user_management_2.user.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.learn.api.user_management_2.user.dtos.UserDTO;
import com.learn.api.user_management_2.user.dtos.requests.CreateOrUpdateUserDTO;
import com.learn.api.user_management_2.user.dtos.requests.RegisterUserAccountDTO;
import com.learn.api.user_management_2.user.entities.Address;
import com.learn.api.user_management_2.user.entities.Contact;
import com.learn.api.user_management_2.user.entities.Gender;
import com.learn.api.user_management_2.user.entities.Role;
import com.learn.api.user_management_2.user.entities.User;
import com.learn.api.user_management_2.user.exceptions.InvalidEmailException;
import com.learn.api.user_management_2.user.exceptions.InvalidLoginException;
import com.learn.api.user_management_2.user.exceptions.InvalidUserDataException;
import com.learn.api.user_management_2.user.exceptions.InvalidUserIdentifierException;
import com.learn.api.user_management_2.user.exceptions.InvalidUsernameException;
import com.learn.api.user_management_2.user.exceptions.RoleNotFoundException;
import com.learn.api.user_management_2.user.exceptions.UserIsSecuredException;
import com.learn.api.user_management_2.user.exceptions.UserNotFoundException;
import com.learn.api.user_management_2.user.repositories.RoleRepository;
import com.learn.api.user_management_2.user.repositories.UserRepository;
import com.learn.api.user_management_2.user.services.validation.EmailValidator;
import com.learn.api.user_management_2.user.services.validation.PasswordValidator;
import com.learn.api.user_management_2.user.services.validation.PhoneValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private PasswordValidator passwordValidator;
    private EmailValidator emailValidator;
    private PhoneValidator phoneValidator;

    public UserService() {
        passwordValidator = new PasswordValidator();
        emailValidator = new EmailValidator();
        phoneValidator = new PhoneValidator();
    }

    /**
     * Get user persentation List
     *
     * @return
     */

    public List<UserDTO> getUserPresentationList() {
        ArrayList<UserDTO> listDto = new ArrayList<>();
        Iterable<User> list = getUserList();
        list.forEach(e -> listDto.add(new UserDTO(e)));
        return listDto;
    }

    /**
     * Get user by Id
     *
     * @param id
     * @return
     */

    public User getUserById(Integer id) {
        if (id == null) {
            throw  new InvalidUserIdentifierException("User Id cannot be null");
        }
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            return userOpt.get();
        }
        throw new UserNotFoundException(String.format("User not found for Id = %s", id));
    }

    /**
     * Get user by username
     *
     * @param username
     * @return
     */

    public User getUserByUsername(String username) {
        if (username == null) {
            throw new InvalidUsernameException("Username cannot be null");
        }
        return userRepository.findByUsername(username);
    }

    /**
     * Get user by email
     *
     * @param email
     * @return
     */

    public User getUserByEmail(String email) {
        if (email == null) {
            throw new InvalidEmailException("Email cannot be null");
        }
        return userRepository.findByEmail(email);
    }

    /**
     * Register user account
     *
     * @param registerUserAccountDTO
     * @return
     */

    @Transactional
    public User registerUserAccount(RegisterUserAccountDTO registerUserAccountDTO) {
        if (registerUserAccountDTO == null) {
            throw new InvalidUserDataException("User account data cannot be null");
        }

        checkIfUsernameNotUsed(registerUserAccountDTO.getUsername());
        passwordValidator.checkPassword(registerUserAccountDTO.getPassword());
        emailValidator.checkEmail(registerUserAccountDTO.getEmail());

        checkIfEmailNotUsed(registerUserAccountDTO.getEmail());

        // Create the new user account: not all the user information required
        User user = new User();
        user.setUsername(registerUserAccountDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerUserAccountDTO.getPassword()));

        user.setLastname(registerUserAccountDTO.getLastname());
        user.setFirstname(registerUserAccountDTO.getFirstname());
        user.setEnabled(true);
        user.setSecured(false);

        // Set gender
        Gender gender = Gender.getValidGender(registerUserAccountDTO.getGender());
        user.setGender(gender);

        addUserRole(user, Role.USER);
        user.setCreationDate(LocalDateTime.now());

        User userCreated = userRepository.save(user);

        // Set contact
        Contact contact = new Contact();
        contact.setEmail(registerUserAccountDTO.getEmail());

        addContactOnUser(userCreated, contact);

        // Set empty address
        addAddressOnUser(userCreated, new Address());

        userCreated = userRepository.save(userCreated);

        log.info(String.format("User %s has been created", userCreated.getId()));
        return userCreated;
    }

    /**
     * Create user
     *
     * @param createUserDTO
     * @return
     */

    @Transactional
    public User createUser(CreateOrUpdateUserDTO createUserDTO) {
        if (createUserDTO == null) {
            throw new InvalidUserDataException("User account data cannot be null");
        }

        checkIfUsernameNotUsed(createUserDTO.getUsername());
        checkIfEmailNotUsed(createUserDTO.getEmail());
        passwordValidator.checkPassword(createUserDTO.getPassword());
        emailValidator.checkEmail(createUserDTO.getEmail());
        phoneValidator.checkPhone(createUserDTO.getPhone());

        // Create the user
        User user = new User();
        user.setUsername(createUserDTO.getUsername());
        user.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));

        user.setLastname(createUserDTO.getLastname());
        user.setFirstname(createUserDTO.getFirstname());

        // Set gender
        Gender gender = Gender.getValidGender(createUserDTO.getGender());
        user.setGender(gender);

        // Date of birth
        user.setBirthDate(createUserDTO.getBirthDate());

        user.setEnabled(true);
        user.setSecured(createUserDTO.isSecured());

        user.setNote(createUserDTO.getNote());
        user.setCreationDate(LocalDateTime.now());

        // Set default user the role
        addUserRole(user, Role.USER);

        User userCreated = userRepository.save(user);

        // Set contact
        Contact contact = new Contact();
        contact.setEmail(createUserDTO.getEmail());
        contact.setPhone(createUserDTO.getPhone());
        contact.setNote(createUserDTO.getNote());

        addContactOnUser(userCreated, contact);

        // Set address
        Address address = new Address();
        address.setAddress(createUserDTO.getAddress());
        address.setAddress2(createUserDTO.getAddress2());
        address.setCity(createUserDTO.getCity());
        address.setCountry(createUserDTO.getCountry());
        address.setZipCode(createUserDTO.getZipCode());

        addAddressOnUser(userCreated, address);

        userCreated = userRepository.save(userCreated);

        log.info(String.format("User %s has been created", userCreated.getId()));
        return userCreated;
    }

    /**
     * Update user
     *
     * @param id
     * @param updateUserDTO
     * @return
     */

    @Transactional
    public User updateUser(Integer id, CreateOrUpdateUserDTO updateUserDTO){
        if (id == null) {
            throw new InvalidUserIdentifierException("Id cannot be null");
        }
        if (updateUserDTO == null) {
            throw new InvalidUserDataException("User account data cannot be null");
        }

        Optional<User> userOpt = userRepository.findById(id);
        if (!userOpt.isPresent()) {
            throw new UserNotFoundException(String.format("The user with Id = %s doesn't exists", id));
        }
        User user = userOpt.get();

        // Check if the username has not been registered
        User userByUsername = getUserByUsername(updateUserDTO.getUsername());
        if (userByUsername != null) {
            // Check if the user's id differnt than the actual user
            if (!user.getId().equals(userByUsername.getId())) {
                String msg = String.format("The username %s it's already in use from another user with Id = %s", updateUserDTO.getUsername(), userByUsername.getId());
                log.error(msg);
                throw new InvalidUserDataException(msg);
            }
        }

        passwordValidator.checkPassword(updateUserDTO.getPassword());
        emailValidator.checkEmail(updateUserDTO.getEmail());
        phoneValidator.checkPhone(updateUserDTO.getPhone());

        // Check if the new email has not been registered yet
        User userEmail = getUserByEmail(updateUserDTO.getEmail());
        if (userEmail != null) {
            String msg = String.format("The email it's already in use from another user with Id = %s", updateUserDTO.getEmail(), userEmail.getId());
            log.error(msg);
            throw new InvalidUserDataException(msg);
        }

        // Update the user
        user.setUsername(updateUserDTO.getUsername());
        user.setPassword(passwordEncoder.encode(updateUserDTO.getPassword()));
        user.setLastname(updateUserDTO.getLastname());
        user.setFirstname(updateUserDTO.getFirstname());

        // Set gender
        Gender gender = Gender.getValidGender(updateUserDTO.getGender());
        user.setGender(gender);

        // date of birth
        user.setBirthDate(updateUserDTO.getBirthDate());

        user.setEnabled(updateUserDTO.isEnabled());
        user.setNote(updateUserDTO.getNote());

        // Set contact: entity always present
        Contact contact = user.getContact();
        contact.setEmail(updateUserDTO.getEmail());
        contact.setPhone(updateUserDTO.getPhone());
        contact.setNote(updateUserDTO.getNote());

        user.setUpdatedDate(LocalDateTime.now());

        // Set address
        Address address = user.getAddress();
        if (address == null) {
            address = new Address();
        }
        address.setAddress(updateUserDTO.getAddress());
        address.setAddress2(updateUserDTO.getAddress2());
        address.setCity(updateUserDTO.getCity());
        address.setCountry(updateUserDTO.getCountry());
        address.setZipCode(updateUserDTO.getZipCode());

        addAddressOnUser(user, address);

        User userUpdated = userRepository.save(user);
        log.info(String.format("User %s has been updated", user.getId()));

        return userUpdated;
    }

    /**
     * Delete user by id
     *
     * @param id
     */

    @Transactional
    public void deleteUserById(Integer id) {
        if (id == null) {
            throw new InvalidUserIdentifierException("Id cannot be null");
        }

        Optional<User> userOpt = userRepository.findById(id);
        if (!userOpt.isPresent()) {
            throw new UserNotFoundException(String.format("User not found with Id = %s", id));
        }

        // Only not secured users can be deleted
        User user = userOpt.get();
        if (user.isSecured()) {
            throw new UserIsSecuredException(String.format("User %s is secured and cannot be deleted", id));
        }

        userRepository.deleteById(id);
        log.info(String.format("User %s has been deleted", id));
    }

    /**
     * Login
     *
     * @param username
     * @param password
     * @return
     */

    @Transactional
    public User login(String username, String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            throw new InvalidLoginException("Username or password cannot be null or empty");
        }

        User user = getUserByUsername(username);
        if (user == null) {
            // Invalid username
            throw new InvalidLoginException("Invalid username or password");
        }

        log.info(String.format("Login request from %s", username));

        // Check the password
        if (passwordEncoder.matches(password, user.getPassword())) {
            // Check if the user is enabled
            if (!user.isEnabled()) {
                // Not enabled
                throw new InvalidLoginException("User is not enabled");
            }

            // Update the last login timestamp
            user.setLoginDate(LocalDateTime.now());
            userRepository.save(user);

            log.info(String.format("Valid login for %s", username));
        } else {
            throw new InvalidLoginException("Invalid username or password");
        }
        return user;
    }

    /**
     * Add role
     * @param id
     * @param roleId
     * @return
     */

    @Transactional
    public User addRole(Integer id, Integer roleId) {
        // Check user
        Optional<User> userOpt = userRepository.findById(id);
        if (!userOpt.isPresent()) {
            throw new UserNotFoundException(String.format("User not found with Id = %s", id));
        }
        User user = userOpt.get();

        // Check role
        Optional<Role> roleOpt = roleRepository.findById(roleId);
        if (!roleOpt.isPresent()) {
            throw new RoleNotFoundException(String.format("Role not found with Id = %s", roleId));
        }
        Role role = roleOpt.get();

        user.getRoles().add(role);
        user.setUpdatedDate(LocalDateTime.now());

        userRepository.save(user);
        log.info(String.format("Added role %s on user Id = %s", role.getRole(), user.getId()));

        return user;
    }

    /**
     * Remove role
     *
     * @param id
     * @param roleId
     * @return
     */

    @Transactional
    public User removeRole(Integer id, Integer roleId) {
        // Check user
        Optional<User> userOpt = userRepository.findById(id);
        if (!userOpt.isPresent()) {
            throw new UserNotFoundException(String.format("User not found with Id = %s", id));
        }
        User user = userOpt.get();

        // Check role
        Optional<Role> roleOpt = roleRepository.findById(roleId);
        if (!roleOpt.isPresent()) {
            throw new RoleNotFoundException(String.format("Role not found with Id = %s", roleId));
        }
        Role role = roleOpt.get();

        user.getRoles().remove(role);
        user.setUpdatedDate(LocalDateTime.now());

        userRepository.save(user);
        log.info(String.format("Remove role %s on user Id = %s", role.getRole(), user.getId()));

        return user;
    }


    /**
     * Get all users
     *
     * @return
     */

    public Iterable<User> getUserList() {
        return userRepository.findAll();
    }

    /**
     * Check if the username has not been registeres
     *
     * @param username
     */

    public void checkIfUsernameNotUsed(String username) {
        User userByUsername = getUserByUsername(username);
        if (userByUsername != null) {
            String msg = String.format("The username %s it's already in user from another user with Id = %s", userByUsername.getUsername(), userByUsername.getId());
            log.error(msg);
            throw new InvalidUserDataException(msg);
        }
    }

    /**
     * Check if the email has not been registered
     *
     * @param email
     */

    public void checkIfEmailNotUsed(String email) {
        User userByEmail = getUserByEmail(email);
        if (userByEmail != null) {
            String msg = String.format("The email %s it's already in use from another user with Id = %s", userByEmail.getContact().getEmail(), userByEmail.getId());
            log.error(msg);
            throw new InvalidUserDataException(String.format("This email %s it's already in use.", userByEmail.getContact().getEmail()));
        }
    }

    /**
     * Add contact on user
     *
     * @param user
     * @param contact
     */

    public void addContactOnUser(User user, Contact contact) {
        contact.setUser(user);
        user.setContact(contact);

        log.debug(String.format("Contact information set on User %s", user.getId()));
    }

    /**
     * Add address on user
     *
     * @param user
     * @param address
     */

    public void addAddressOnUser(User user, Address address) {
        address.setUser(user);
        user.setAddress(address);

        log.debug(String.format("Address information set on User %s", user.getId()));
    }

    /**
     * Add user role
     *
     * @param user
     * @param roleId
     */

    public void addUserRole(User user, Integer roleId) {
        Optional<Role> roleOpt = roleRepository.findById(roleId);
        if (!roleOpt.isPresent()) {
            throw new RoleNotFoundException("Role cannot be null");
        }
        user.getRoles().add(roleOpt.get());
    }
}
