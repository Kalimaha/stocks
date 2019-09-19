package blog.guidobarbaglia.stocks.services

import blog.guidobarbaglia.stocks.models.Stock
import blog.guidobarbaglia.stocks.models.StockPrice
import blog.guidobarbaglia.stocks.repositories.StocksRepository
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration
import java.time.Instant
import java.util.*


@Service
class StocksService(val stocksRepository: StocksRepository) {
  fun stocks(): Flux<Stock> = stocksRepository.findAll()

  fun stock(stockCode: String): Mono<Stock> = stocksRepository.findByCode(stockCode)

  @HystrixCommand(fallbackMethod = "defaultSlowStock")
  fun slowStock(stockCode: String): Mono<Stock> {
    Thread.sleep(5_000)
    return stocksRepository.findByCode(stockCode)
  }

  fun prices(stockId: String) =
    Flux
      .interval(Duration.ofSeconds(1))
      .onBackpressureDrop()
      .map { StockPrice(stockId, randomPrice(), Instant.now()) }

  private fun randomPrice(): Float = Random().nextInt(100).toFloat()

  @Suppress("unused", "UNUSED_PARAMETER")
  private fun defaultSlowStock(stockCode: String): Mono<Stock> = Mono.just(Stock())
}