package rares.web.ecommece.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rares.web.ecommece.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
