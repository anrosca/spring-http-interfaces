package inc.evil.spring.http.interfaces;

import inc.evil.spring.http.interfaces.domain.ChuckNorrisQuote;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.List;

@SpringBootApplication
@Slf4j
public class SpringHttpInterfacesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringHttpInterfacesApplication.class, args);
    }

    @Bean
    public ChuckNorrisClient chuckNorrisClient() throws Exception {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://api.chucknorris.io/")
                .build();
        HttpServiceProxyFactory factory = new HttpServiceProxyFactory(new WebClientAdapter(webClient));
        factory.afterPropertiesSet();
        return factory.createClient(ChuckNorrisClient.class);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ChuckNorrisClient client) {
        return args -> {
            ChuckNorrisQuote randomQuote = client.getRandomQuote();
            log.info("Random Chuck Norris quote: {}", randomQuote);
            log.info("Categories: {}", client.getCategories());
            log.info("Joke from money category: {}", client.getQuoteFromCategory("money"));
        };
    }
}

@HttpExchange(url = "/jokes")
interface ChuckNorrisClient {
    @HttpExchange(url = "/random", method = "GET")
    ChuckNorrisQuote getRandomQuote();

    @GetExchange("/random")
    ChuckNorrisQuote getQuoteFromCategory(@RequestParam("category") String category);

    @GetExchange("/categories")
    List<String> getCategories();
}
