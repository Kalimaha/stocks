package blog.guidobarbaglia.stocks.initialisers

import blog.guidobarbaglia.stocks.models.Stock
import blog.guidobarbaglia.stocks.repositories.StocksRepository
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import javax.annotation.PostConstruct

@Component
class StocksInitializer(val stocksRepository: StocksRepository) {
  @Suppress("unused")
  @PostConstruct
  fun init() {
    stocksRepository
      .deleteAll()
      .thenMany(
        Flux
          .just("ASX:REA", "ASX:OPT", "ASX:SZL")
          .map { Stock(code = it) }
          .flatMap { stocksRepository.save(it) }
      )
      .thenMany(stocksRepository.findAll())
      .subscribe { println(it) }
  }
}