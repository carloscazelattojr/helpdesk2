package br.com.carlosjunior.authserviceapi.repositories;

import br.com.carlosjunior.authserviceapi.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(final String email);
}
