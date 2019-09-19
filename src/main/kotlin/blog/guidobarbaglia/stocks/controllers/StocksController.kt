package blog.guidobarbaglia.stocks.controllers

import blog.guidobarbaglia.stocks.services.StocksService
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@EnableCircuitBreaker
@RequestMapping("/stocks")
class StocksController(val stocksService: StocksService) {
  @GetMapping
  fun stocks() = stocksService.stocks()

  @GetMapping("/{stockId}")
  fun stock(@PathVariable("stockId") stockId: String) = stocksService.stock(stockId)

  @GetMapping("/{stockId}/prices", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
  fun prices(@PathVariable("stockId") stockId: String) = stocksService.prices(stockId)

  @GetMapping("/{stockId}/slow")
  fun slowStock(@PathVariable("stockId") stockId: String) = stocksService.slowStock(stockId)
}