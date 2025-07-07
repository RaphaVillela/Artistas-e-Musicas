package com.artista_musica.artistas_e_musicas.repository;

import com.artista_musica.artistas_e_musicas.model.Artista;
import com.artista_musica.artistas_e_musicas.model.Musica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArtistaRepository extends JpaRepository<Artista, Long> {
    Optional<Artista> findByNomeContainingIgnoreCase(String nome);

    @Query("SELECT m FROM Artista a JOIN a.musicas m WHERE a.nome ILIKE %:nome%")
    List<Musica>buscaMusicasPorArtista(String nome);
}
