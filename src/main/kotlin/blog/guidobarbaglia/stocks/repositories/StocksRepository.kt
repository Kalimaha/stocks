package blog.guidobarbaglia.stocks.repositories

import blog.guidobarbaglia.stocks.models.Stock
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface StocksRepository : ReactiveCrudRepository<Stock, String> {
  fun findByCode(stockCode: String): Mono<Stock>
}