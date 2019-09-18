package blog.guidobarbaglia.stocks.services

import blog.guidobarbaglia.stocks.models.Stock
import blog.guidobarbaglia.stocks.repositories.StocksRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@RunWith(SpringRunner::class)
@WebFluxTest(StocksService::class)
class StocksServiceTest {
  @Autowired
  lateinit var stocksService: StocksService

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
  fun `All the stocks`() {
    StepVerifier
      .withVirtualTime { stocksService.stocks() }
      .expectNext(stock1)
      .expectNext(stock2)
      .verifyComplete()
  }

  @Test
  fun `Specific stock`() {
    StepVerifier
      .withVirtualTime { stocksService.stock("ASX:OPT") }
      .expectNext(stock2)
      .verifyComplete()
  }
}