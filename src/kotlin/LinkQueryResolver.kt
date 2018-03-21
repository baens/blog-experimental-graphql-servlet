import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.fasterxml.jackson.annotation.JsonProperty

class LinkQueryResolver : GraphQLQueryResolver {
    private val linksRepository : LinkRepository = LinkRepository()

    fun allLinks(filter: LinkFilter?) = linksRepository.getAllLinks(filter)
}

data class LinkFilter(@JsonProperty("description_contains") val linkDescription: String?, @JsonProperty("url_contains") val urlContains: String?)
