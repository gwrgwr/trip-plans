package br.com.murilo.tripplans.user;

import br.com.murilo.tripplans.mapper.DozerMapper;
import br.com.murilo.tripplans.user.VO.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserVO save(UserVO userVO) {
        User user = userRepository.save(DozerMapper.parseObject(userVO, User.class));
        UserVO vo =  DozerMapper.parseObject(user, UserVO.class);
        vo.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).findOne(vo.getUserId())).withSelfRel());
        return vo;
    }

    public UserVO findById(UUID id) {
        User user = userRepository.findById(id).orElseThrow();
        System.out.println(user.getConnection().getId());
        UserVO vo = DozerMapper.parseObject(user, UserVO.class);
        vo.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).findOne(vo.getUserId())).withSelfRel());
        return vo;
    }

    public List<UserVO> findAll() {
        List<User> users = userRepository.findAll();
        List<UserVO> vo = DozerMapper.parseListObjects(users, UserVO.class);
        vo.forEach(p -> p.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).findOne(p.getUserId())).withSelfRel()));
        return vo;
    }

    public UserVO update(UserVO user) {
        User userEntity = userRepository.findById(user.getUserId()).orElseThrow();
        userEntity.setEmail(user.getEmail());
        userEntity.setName(user.getName());
        userEntity.setPassword(user.getPassword());
        UserVO vo = DozerMapper.parseObject(userRepository.save(userEntity), UserVO.class);
        vo.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).update(vo)).withSelfRel());
        return vo;
    }
    public void delete(UUID id) {
        userRepository.deleteById(id);
    }

    public UserVO generateCode(UUID id) {
        User user = userRepository.findById(id).orElseThrow();
        if (Objects.equals(user.getCode(), "")) {
            SecureRandom random = new SecureRandom();
            int num = random.nextInt(0x1000000);
            String hexNum = String.format("%06x", num);
            user.setCode(hexNum);
            System.out.println("Code: " + hexNum);
            UserVO vo = DozerMapper.parseObject(userRepository.save(user), UserVO.class);
            vo.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).generateCode(vo.getUserId())).withSelfRel());
            return vo;
        }
        throw new RuntimeException("Code already exists");
    }
}
