package vn.zeus.web.security;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.zeus.web.domain.model.User;
import vn.zeus.web.exception.UserNotFoundException;
import vn.zeus.web.service.UserService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UserService userService;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String userName)
          throws UsernameNotFoundException {
    User user = Optional.of(userService.findUserByUsername(userName)).orElseThrow(() ->
            new UserNotFoundException("userName", userName, new Throwable()
            ));

    return UserPrincipal.create(user);
  }

  @Transactional
  public UserDetails loadUserById(Integer id) {

    User user = Optional.of(userService.findUserById(Long.valueOf(id))).orElseThrow(() ->
            new UserNotFoundException("id", id, new Throwable())
    );

    return UserPrincipal.create(user);
  }
}