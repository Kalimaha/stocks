package blog.guidobarbaglia.stocks.controllers

import blog.guidobarbaglia.stocks.services.StocksService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/stocks")
class StocksController(val stocksService: StocksService) {
  @GetMapping
  fun stocks() = stocksService.stocks()

  @GetMapping("/{id}")
  fun stock(@PathVariable("id") id: String) = stocksService.stock(id)
}