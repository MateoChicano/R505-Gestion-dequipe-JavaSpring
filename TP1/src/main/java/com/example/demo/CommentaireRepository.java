package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentaireRepository extends JpaRepository<Commentaire, Integer> {

    List<Commentaire> findByJoueurJoueurId(Integer joueurId);
}
