package blog.guidobarbaglia.stocks.models

import java.time.Instant

data class StockPrice(val stockCode: String, val price: Float, val timestamp: Instant)