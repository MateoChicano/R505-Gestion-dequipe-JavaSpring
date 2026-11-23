package com.example.demo;

public record CommentaireResponse(Integer commentaire_id, String contenu, String date) {

    public static CommentaireResponse from(Commentaire commentaire) {
        return new CommentaireResponse(
                commentaire.getCommentaireId(),
                commentaire.getContenu(),
                commentaire.getDate() == null ? null : commentaire.getDate().toString());
    }
}
