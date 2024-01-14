package io.security.basicsecurity.repository;

import io.security.basicsecurity.vo.User;
import org.springframework.data.jpa.repository.JpaRepository;

// CRUD 함수를 jpa레포지토리가 들고있음
// @Repository 없어도 IoC됨. Jpa레포지토리를 상속했기 때문.
public interface UserRepository extends JpaRepository<User, Integer> { // PK타입:integer
    // findBy규칙 -> Username문법
    // select * from user where username = ?
    public User findByUsername(String username); // Jpa Query Methods
}
