package com.MOA.backend.global.auth.refresh.repository;

import com.MOA.backend.global.auth.refresh.dto.Token;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends CrudRepository<Token, Long> {
}
