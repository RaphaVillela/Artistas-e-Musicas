package com.artista_musica.artistas_e_musicas.main;

import com.artista_musica.artistas_e_musicas.model.Artista;
import com.artista_musica.artistas_e_musicas.model.Musica;
import com.artista_musica.artistas_e_musicas.model.TipoArtista;
import com.artista_musica.artistas_e_musicas.repository.ArtistaRepository;
import com.artista_musica.artistas_e_musicas.service.ApiGroq;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private final ArtistaRepository repositorio;
    private Scanner leitura = new Scanner(System.in);

    public Main(ArtistaRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void exibeMenu() {

        String menu = """
                Digite o número correspondente:
                1 - Cadastrar artista
                2 - Cadastrar música
                3 - Listar músicas
                4 - Pesquisar música por artista
                5 - Pesquisar dados de um artista
                6 - Listar artistas cadastrados
                7 - Saber sobre a história de uma música
                0 - Sair""";

        var opcao = -1;

        while (opcao != 0) {
            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarArtista();
                    break;
                case 2:
                    cadastrarMusica();
                    break;
                case 3:
                    listarMusicas();
                    break;
                case 4:
                    pesquisarmusicaPorArtista();
                    break;
                case 5:
                    pesquisarDadoDeArtista();
                    break;
                case 6:
                    listarArtistasCadastrados();
                    break;
                case 7:
                    historiaDaMusica();
                    break;
                case 0:
                    System.out.println("Encerrando programa...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    public void cadastrarArtista() {
        var continuaCadastro = "S";

        while(continuaCadastro.equalsIgnoreCase("S")) {
            System.out.println("Digite o nome do artista:");
            var nome = leitura.nextLine();

            System.out.println("Digite o tipo de artista: (solo, dupla ou banda)");
            var tipo = leitura.nextLine();
            TipoArtista tipoArtista = TipoArtista.valueOf(tipo.toUpperCase());

            Artista artista = new Artista(nome, tipoArtista);

            repositorio.save(artista);

            System.out.println("Cadastrar outro artista? (s/n)");
            continuaCadastro = leitura.nextLine();
        }
    }

    public void cadastrarMusica() {
        var continuaCadastro = "S";

        while(continuaCadastro.equalsIgnoreCase("S")) {
            System.out.println("Cadastar música de que artista?");
            var nome = leitura.nextLine();

            Optional<Artista> artista = repositorio.findByNomeContainingIgnoreCase(nome);

            if (artista.isPresent()) {
                System.out.println("Digite o nome da música:");
                var titulo = leitura.nextLine();

                Musica musica = new Musica(titulo, artista.get());
                artista.get().getMusicas().add(musica);
                repositorio.save(artista.get());

            } else {
                System.out.println("Artista não cadastrado");
            }

            System.out.println("Cadastrar outra música? (s/n)");
            continuaCadastro = leitura.nextLine();
        }
    }

    public void listarMusicas() {
        List<Artista> artistas = repositorio.findAll();
        artistas.forEach(a -> a.getMusicas().forEach(System.out::println));
    }

    public void pesquisarmusicaPorArtista() {
        System.out.println("Qual artista deseja ver as múscas?");
        var nome = leitura.nextLine();
        List<Musica> musicas = repositorio.buscaMusicasPorArtista(nome);
        musicas.forEach(System.out::println);
    }

    public void pesquisarDadoDeArtista() {
        System.out.println("De qual artista deseja saber mais?");
        var artista = leitura.nextLine();

        System.out.println(ApiGroq.InfoArtistaGroq(artista));
    }

    public void listarArtistasCadastrados() {
        List<Artista> artistas = repositorio.findAll();

        artistas.forEach(a -> System.out.println(a.getNome()));
    }

    public void historiaDaMusica() {
        System.out.println("De qual música deseja saber a história?");
        var musica = leitura.nextLine();

        System.out.println(ApiGroq.historiaMusicaGroq(musica));
    }
}