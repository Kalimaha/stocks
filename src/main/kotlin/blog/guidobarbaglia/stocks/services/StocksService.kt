package blog.guidobarbaglia.stocks.services

import blog.guidobarbaglia.stocks.models.Stock
import blog.guidobarbaglia.stocks.repositories.StocksRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class StocksService(val stocksRepository: StocksRepository) {
  fun stocks(): Flux<Stock> = stocksRepository.findAll()

  fun stock(stockCode: String): Mono<Stock> = stocksRepository.findByCode(stockCode)
}