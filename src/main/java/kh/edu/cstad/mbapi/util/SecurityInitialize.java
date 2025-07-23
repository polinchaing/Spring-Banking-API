package kh.edu.cstad.mbapi.util;

import jakarta.annotation.PostConstruct;
import kh.edu.cstad.mbapi.domain.Role;
import kh.edu.cstad.mbapi.domain.User;
import kh.edu.cstad.mbapi.repository.RoleRepository;
import kh.edu.cstad.mbapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;


@Component
@RequiredArgsConstructor
public class SecurityInitialize {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    void Init(){
        if(roleRepository.count()==0){

            Role roleUser = new Role();
            roleUser.setRoleName("USER");

            Role roleStaff = new Role();
            roleStaff.setRoleName("STAFF");

            Role roleCustomer = new Role();
            roleCustomer.setRoleName("CUSTOMER");

            Role roleAdmin = new Role();
            roleAdmin.setRoleName("ADMIN");

            roleRepository.saveAll(List.of(roleAdmin,roleStaff,roleCustomer,roleUser));

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("pwd@123"));
            admin.setIsEnable(true);
            admin.setRoles(Set.of(roleAdmin,roleUser));

            User staff = new User();
            staff.setUsername("staff");
            staff.setPassword(passwordEncoder.encode("pwd@123"));
            staff.setIsEnable(true);
            staff.setRoles(Set.of(roleStaff,roleUser));

            User customer = new User();
            customer.setUsername("customer");
            customer.setPassword(passwordEncoder.encode("pwd@123    "));
            customer.setIsEnable(true);
            customer.setRoles(Set.of(roleCustomer,roleUser));

            userRepository.saveAll(List.of(admin,staff,customer));

        }
    }
}
