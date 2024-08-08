package com.cunoc.edu.gt.service.auth;

import com.cunoc.edu.gt.data.request.auth.UserLoginRequest;
import com.cunoc.edu.gt.data.request.auth.UserRequest;
import com.cunoc.edu.gt.data.response.auth.UserResponse;
import com.cunoc.edu.gt.data.validator.UserHelper;
import com.cunoc.edu.gt.dmimpl.auth.UserDM;
import com.cunoc.edu.gt.eventlistener.EventListenerAdapter;
import com.cunoc.edu.gt.exception.BadOperationException;
import com.cunoc.edu.gt.exception.InvalidDataException;
import com.cunoc.edu.gt.exception.NotFoundException;
import com.cunoc.edu.gt.model.auth.UserDTO;
import com.cunoc.edu.gt.opextends.UserOP;
import com.cunoc.edu.gt.ports.output.EventPublisher;
import com.cunoc.edu.gt.ports.output.TransactionId;
import com.cunoc.edu.gt.ports.output.event.DisplayEvent;
import com.cunoc.edu.gt.ucextends.auth.UserUC;
import lombok.SneakyThrows;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDateTime;

/**
 * Service to manage the user transactions
 *
 * @Author: Augusto Vicente
 */
public class UserService implements UserUC {

    @Override
    @SneakyThrows
    public UserResponse login(UserLoginRequest loginRequest) {
        if(loginRequest == null){
            throw new BadOperationException(String.format("In the %s method of %s the login request is required.", "login", "UserService"));
        }

        if(loginRequest.getUsername() == null || loginRequest.getUsername().isEmpty()){
            throw new InvalidDataException(String.format("In the %s method of %s the username is required.", "login", "UserService"));
        }

        if(loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()){
            throw new InvalidDataException(String.format("In the %s method of %s the password is required.", "login", "UserService"));
        }

        return getByUsername(loginRequest.getUsername(), loginRequest.getPassword());
    }

    @Override
    public UserResponse register(UserRequest request) {
        //Validate request
        UserHelper.validateUserRequest(request);

        if(request.getPassword().compareTo(request.getPasswordConfirmation()) != 0){
            throw new InvalidDataException("Passwords do not match");
        }

        return save(request);
    }

    /**
     * Save entity
     *
     * @param request the entity to save
     * @return QueryResponse the saved entity
     */
    @Override
    public UserResponse save(UserRequest request) {
        UserDTO dto = domainMapper.requestToDto(request);
        dto.setPassword(BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt()));

        dto = outputPort.save((UserDTO) auditAttributeAuthService.getAuditAttributeAuthForNew(dto));

        eventPublisher.handle(new DisplayEvent<>("User saved", LocalDateTime.now(), "System" ,"UserService", "save", TransactionId.generateTransactionId(), dto.getId()));

        return domainMapper.dtoToResponse(dto);
    }

    /**
     * Delete entity by id
     *
     * @param id the id of the entity to be deleted
     */
    @Override
    public void deleteById(Integer id) {

    }

    /**
     * Check if entity exists by id
     *
     * @param id id of the object to be retrieved
     * @return boolean
     */
    @Override
    public boolean existsById(Integer id) {
        return false;
    }

    /**
     * Get all objects
     *
     * @param ID id of the object to be retrieved
     * @return Response
     */
    @Override
    public UserResponse getById(Integer ID) {
        return null;
    }

    /**
     * Get user by username
     *
     * @param username the username to search
     * @param password the password to match
     * @return UserResponse the user found
     */
    @Override
    @SneakyThrows
    public  UserResponse getByUsername(String username, String password){
        UserResponse response = outputPort.getByUsername(username, password)
                .map(domainMapper::dtoToResponse)
                .orElseThrow(() -> new NotFoundException("Username / Password is incorrect"));

        eventPublisher.handle(new DisplayEvent<>("User retrieved by username", LocalDateTime.now(), "System" ,"UserService", "getByUsername", TransactionId.generateTransactionId(), response.getId()));

        return response;
    }

    /**
     * Update object by id
     *
     * @param createRequest the object to be updated
     * @param id            id of the object to be updated
     * @return QueryResponse the updated object
     */
    @Override
    public UserResponse updateById(UserRequest createRequest, Integer id) {
        return null;
    }

    private UserService(UserOP outputPort) {
        this.outputPort = outputPort;
        this.domainMapper = UserDM.getInstance();
        this.eventPublisher = EventListenerAdapter.getInstance();

        this.auditAttributeAuthService = AuditAttributeAuthService.getInstance();
    }

    public static UserService getInstance(UserOP outputPort) {
        if (instance == null) {
            instance = new UserService(outputPort);
        }
        return instance;
    }

    private static UserService instance;
    private final UserOP outputPort;
    private final UserDM domainMapper;

    private final EventPublisher eventPublisher;
    private final AuditAttributeAuthService auditAttributeAuthService;
}