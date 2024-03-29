package blog.guidobarbaglia.stocks.controllers

import blog.guidobarbaglia.stocks.models.Stock
import blog.guidobarbaglia.stocks.repositories.StocksRepository
import blog.guidobarbaglia.stocks.services.StocksService
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.returnResult
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@RunWith(SpringRunner::class)
@WebFluxTest(StocksService::class, StocksController::class)
class StocksControllerTests {
  @Autowired
  lateinit var stocksClient: WebTestClient

  @MockBean
  lateinit var stocksRepository: StocksRepository

  private val stock1 = Stock(code = "ASX:REA")
  private val stock2 = Stock(code = "ASX:OPT")

  @Before
  fun setUp() {
    Mockito.`when`(stocksRepository.findAll()).thenReturn(Flux.just(stock1, stock2))
    Mockito.`when`(stocksRepository.findByCode(stock1.code!!)).thenReturn(Mono.just(stock1))
    Mockito.`when`(stocksRepository.findByCode(stock2.code!!)).thenReturn(Mono.just(stock2))
  }

  @Test
  fun `Find all the stocks`() {
    StepVerifier
      .create(
        stocksClient
          .get().uri("/stocks")
          .exchange()
          .expectStatus().isOk
          .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
          .returnResult<Stock>().responseBody
      )
      .expectNext(stock1)
      .expectNext(stock2)
      .verifyComplete()
  }

  @Test
  fun `Find by stock's code`() {
    StepVerifier
      .create(
        stocksClient
          .get().uri("/stocks/ASX:OPT")
          .exchange()
          .expectStatus().isOk
          .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
          .returnResult<Stock>().responseBody
      )
      .expectNext(stock2)
      .verifyComplete()
  }
}
