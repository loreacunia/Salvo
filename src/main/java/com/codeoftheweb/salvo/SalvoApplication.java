package com.codeoftheweb.salvo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(PlayerRepository playerRepository, GameRepository gameRepository, GamePlayerRepository gamePlayerRepository, ShipRepository shipRepository) {
		return (args) -> {
			// save a couple of customers
			Player player1 = new Player ("j.bauer@ctu.gov");
			Player player2 = new Player("c.obrian@ctu.gov");
			Player player3 = new Player("kim_bauer@gmail.com");
			Player player4 = new Player("t.almeida@ctu.gov");

			playerRepository.save(player1);
			playerRepository.save(player2);
			playerRepository.save(player3);
			playerRepository.save(player4);

			Game game1 = new Game();
			Game game2 = new Game (3600);
			Game game3 = new Game(7200);

			gameRepository.save(game1);
			gameRepository.save(game2);
			gameRepository.save(game3);

			GamePlayer gamePlayer1 = new GamePlayer(player1, game1);
			GamePlayer gamePlayer2 = new GamePlayer(player2, game1);
			GamePlayer gamePlayer3 = new GamePlayer(player3, game2);
			GamePlayer gamePlayer4 = new GamePlayer(player4, game2);
			GamePlayer gamePlayer5 = new GamePlayer(player1, game3);
			GamePlayer gamePlayer6 = new GamePlayer(player4, game3);

			gamePlayerRepository.save(gamePlayer1);
			gamePlayerRepository.save(gamePlayer2);
			gamePlayerRepository.save(gamePlayer3);
			gamePlayerRepository.save(gamePlayer4);
			gamePlayerRepository.save(gamePlayer5);
			gamePlayerRepository.save(gamePlayer6);

			Set<String> shipL1 = new HashSet<>(Arrays.asList("H2","H3","H4"));
			Set<String> shipL2 = new HashSet<>(Arrays.asList("E1","F1","G1"));
			Set<String> shipL3 = new HashSet<>(Arrays.asList("B4","B5"));
			Set<String> shipL4 = new HashSet<>(Arrays.asList("B5","C5","D5"));
			Set<String> shipL5 = new HashSet<>(Arrays.asList("F1","F2"));

			Ship ship1 = new Ship(gamePlayer1,"Destroyer",shipL1);
			Ship ship2 = new Ship(gamePlayer1,"Submarine",shipL2);
			Ship ship3 = new Ship(gamePlayer3,"Patrol Boat",shipL3);
			Ship ship4 = new Ship(gamePlayer2,"Destroyer",shipL4);
			Ship ship5 = new Ship(gamePlayer2,"Destroyer",shipL5);


			shipRepository.saveAll(Arrays.asList(ship1,ship2,ship3,ship4,ship5));



		};
	}
}