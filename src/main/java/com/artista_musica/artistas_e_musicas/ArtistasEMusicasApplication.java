package com.artista_musica.artistas_e_musicas;

import com.artista_musica.artistas_e_musicas.main.Main;
import com.artista_musica.artistas_e_musicas.repository.ArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ArtistasEMusicasApplication implements CommandLineRunner {

	@Autowired
	private ArtistaRepository repositorio;

	public static void main(String[] args) {
		SpringApplication.run(ArtistasEMusicasApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Main main = new Main(repositorio);
		main.exibeMenu();
	}
}
