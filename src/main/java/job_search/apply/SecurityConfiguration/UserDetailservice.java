package job_search.apply.SecurityConfiguration;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import job_search.apply.models.Users;
import job_search.apply.repositories.UserRepository;

@Service
public class UserDetailservice implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users currentUsers=userRepository.findByUsername(username);
        if(currentUsers!=null){
           return User.builder().username(currentUsers.getUsername()).password(currentUsers.getPassword()).build();
        }
      throw new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'");
   }
 
    
}
