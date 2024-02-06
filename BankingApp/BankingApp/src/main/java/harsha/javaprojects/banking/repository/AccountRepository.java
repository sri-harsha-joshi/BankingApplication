package harsha.javaprojects.banking.repository;

import harsha.javaprojects.banking.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {

}
