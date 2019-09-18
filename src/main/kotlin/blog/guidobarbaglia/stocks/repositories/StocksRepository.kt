package blog.guidobarbaglia.stocks.repositories

import blog.guidobarbaglia.stocks.models.Stock
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface StocksRepository : ReactiveCrudRepository<Stock, String>