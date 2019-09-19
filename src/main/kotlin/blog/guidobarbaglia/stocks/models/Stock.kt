package blog.guidobarbaglia.stocks.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Stock(@Id val id: String? = null, val code: String? = null, val description: String = "Not available.")