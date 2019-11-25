package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class SalvoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SalvoApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(PlayerRepository playerRepository, GameRepository gameRepository, GamePlayerRepository gamePlayerRepository, ShipRepository shipRepository, SalvoRepository salvoRepository, ScoreRepository scoreRepository) {
        return (args) -> {
            // save a couple of customers
            Player player1 = new Player("j.bauer@ctu.gov", "24");
            Player player2 = new Player("c.obrian@ctu.gov",  "42");
            Player player3 = new Player("kim_bauer@gmail.com", "kb");
            Player player4 = new Player("t.almeida@ctu.gov",  "mole");

            playerRepository.save(player1);
            playerRepository.save(player2);
            playerRepository.save(player3);
            playerRepository.save(player4);

            Game game1 = new Game();
            Game game2 = new Game(3600);
            Game game3 = new Game(7200);
            Game game4 = new Game ();

            gameRepository.save(game1);
            gameRepository.save(game2);
            gameRepository.save(game3);
            gameRepository.save(game4);

            GamePlayer gamePlayer1 = new GamePlayer(player1, game1);
            GamePlayer gamePlayer2 = new GamePlayer(player2, game1);
            GamePlayer gamePlayer3 = new GamePlayer(player3, game2);
            GamePlayer gamePlayer4 = new GamePlayer(player4, game2);
            GamePlayer gamePlayer5 = new GamePlayer(player1, game3);
            GamePlayer gamePlayer6 = new GamePlayer(player4, game3);
            GamePlayer gamePlayer7 = new GamePlayer(player1, game4);

            gamePlayerRepository.save(gamePlayer1);
            gamePlayerRepository.save(gamePlayer2);
            gamePlayerRepository.save(gamePlayer3);
            gamePlayerRepository.save(gamePlayer4);
            gamePlayerRepository.save(gamePlayer5);
            gamePlayerRepository.save(gamePlayer6);
            gamePlayerRepository.save(gamePlayer7);

            List<String> shipL1 = Arrays.asList("H2", "H3", "H4");
            List<String> shipL2 = Arrays.asList("E1", "F1", "G1");
            List<String> shipL3 = Arrays.asList("B4", "B5");
            List<String> shipL4 = Arrays.asList("B5", "C5", "D5");
            List<String> shipL5 = Arrays.asList("F1", "F2");

            Ship ship1 = new Ship(gamePlayer1, "Destroyer", shipL1);
            Ship ship2 = new Ship(gamePlayer1, "Submarine", shipL2);
            Ship ship3 = new Ship(gamePlayer3, "Patrol Boat", shipL3);
            Ship ship4 = new Ship(gamePlayer2, "Carrier", shipL4);
            Ship ship5 = new Ship(gamePlayer2, "Battleship", shipL5);


            shipRepository.saveAll(Arrays.asList(ship1, ship2, ship3, ship4, ship5));

            List<String> salvoL1 = Arrays.asList("H4", "H3", "H2");
            List<String> salvoL2 = Arrays.asList("B4", "B5", "B6");
            List<String> salvoL3 = Arrays.asList("E1", "E2");
            List<String> salvoL4 = Arrays.asList("B5", "C5", "D5");
            List<String> salvoL5 = Arrays.asList("F2", "F3");

            Salvo salvo1 = new Salvo(gamePlayer1, 1, salvoL1);
            Salvo salvo2 = new Salvo(gamePlayer2, 1, salvoL1);
            Salvo salvo3 = new Salvo(gamePlayer1, 2, salvoL3);
            Salvo salvo4 = new Salvo(gamePlayer2, 2, salvoL4);
            Salvo salvo5 = new Salvo(gamePlayer1, 2, salvoL5);

            salvoRepository.saveAll(Arrays.asList(salvo1, salvo2, salvo3, salvo4, salvo5));

            Score score1 = new Score(1, game1, player1);
            Score score2 = new Score(1, game1, player2);
            Score score3 = new Score(1, game2, player3);
            Score score4 = new Score(0.5, game2, player4);
            Score score5 = new Score(0, game3, player1);
            Score score6 = new Score(0, game3, player4);

            scoreRepository.saveAll(Arrays.asList(score1, score2, score3, score4, score5, score6));
        };
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}

@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(inputName-> {
            Player player = playerRepository.findByUserName(inputName);
            if (player != null) {
                return new User(player.getUserName(), passwordEncoder.encode( player.getPassword()),
                        AuthorityUtils.createAuthorityList("USER"));
            } else {
                throw new UsernameNotFoundException("Unknown user: " + inputName);
            }
        }).passwordEncoder(passwordEncoder);
    }
}

@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
            .antMatchers("/web/games.html").permitAll()
            .antMatchers("/web/css/**").permitAll()
            .antMatchers("/web/js/**").permitAll()
            .antMatchers("/web/dist/**").permitAll()
            .antMatchers( "/api/games" ,"/api/login").permitAll()
            .antMatchers(  "/api/players").permitAll()
            .antMatchers( "/api/leaderboard").permitAll()
            .antMatchers( "/api/games/*/players").permitAll()
            .antMatchers("/api/game_view/**", "/web/game.html*").hasAuthority("USER")
            .antMatchers("/api/games/players/*/ships").hasAuthority("USER")
            .antMatchers("/web/grid.html").hasAuthority("USER")
            .antMatchers( "/rest/*").hasAuthority("ADMIN")
            .anyRequest().denyAll();

        http.formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginPage("/api/login");
        http.logout().logoutUrl("/api/logout");


            // turn off checking for CSRF tokens
            http.csrf().disable();

            // if user is not authenticated, just send an authentication failure response
            http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

            // if login is successful, just clear the flags asking for authentication
            http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

            // if login fails, just send an authentication failure response
            http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

            // if logout is successful, just send a success response
            http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
        }

        private void clearAuthenticationAttributes(HttpServletRequest request) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            }
        }
    }
