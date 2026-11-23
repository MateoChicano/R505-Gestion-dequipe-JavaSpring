package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipationRepository extends JpaRepository<Participation, Integer> {

    List<Participation> findByRencontreRencontreId(Integer rencontreId);

    boolean existsByRencontreRencontreIdAndJoueurJoueurId(Integer rencontreId, Integer joueurId);
}
