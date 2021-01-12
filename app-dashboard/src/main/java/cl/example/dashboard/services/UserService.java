package cl.example.dashboard.services;

import cl.example.entities.domain.entities.ClientEntity;
import cl.example.entities.domain.entities.UserEntity;
import cl.example.entities.domain.entities.vo.UserVO;
import cl.example.entities.domain.repositories.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    public UserVO getUserById(int userId, int clientId) {
        UserEntity entity = userRepository.findByIdAndClient_Id(userId, clientId);
        if (entity == null) {
            throw new RuntimeException("The selected user does not exist.");
        }

        return entity.toVO();
    }

    public UserVO saveUser(UserVO vo) {
        UserEntity entity = new UserEntity();
        if (vo == null) {
            return null;
        }

        if (vo.getId() != null) {
            Optional<UserEntity> opt = userRepository.findById(vo.getId());
            if (opt.isPresent()) {
                entity = opt.get();
            } else {
                throw new RuntimeException("The user to edit does not exist.");
            }
        }

        if (StringUtils.isEmpty(vo.getFullName())) {
            throw new RuntimeException("Name cannot be null.");
        }

        if (StringUtils.isEmpty(vo.getPassword())) {
            throw new RuntimeException("Password cannot be null.");
        }

        if (StringUtils.isEmpty(vo.getUsername())) {
            throw new RuntimeException("Username cannot be null.");
        }

        UserEntity u = userRepository.findByUsernameAndClient_Id(vo.getUsername(), vo.getClientId());
        if (u != null && !u.getId().equals(vo.getId())) {
            throw new RuntimeException("A user with that username already exists.");
        }

        String password = new BCryptPasswordEncoder().encode(vo.getPassword());
        vo.setPassword(password);

        BeanUtils.copyProperties(vo, entity);
        entity.setClient(entityManager.getReference(ClientEntity.class, vo.getClientId()));
        entity = userRepository.saveAndFlush(entity);

        return entity.toVO();
    }

}
