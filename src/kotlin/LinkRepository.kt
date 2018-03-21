data class Link(val url: String, val description: String)

class LinkRepository {
    private val links: List<Link>

    init {
        links = ArrayList()
        links.add(Link("test url", "test desc"))
        links.add(Link("test url 2", "test desc 2"))
    }

    fun getAllLinks(linkFilter: LinkFilter?) : List<Link> {
        if (linkFilter == null) {
            return links
        }

        var currentList = links

        if (linkFilter.urlContains != null && linkFilter.urlContains.isNotEmpty()) {
            currentList = currentList.filter { it.url.contains(linkFilter.urlContains) }
        }

        if (linkFilter.linkDescription != null && linkFilter.linkDescription.isNotEmpty()) {
            currentList = currentList.filter { it.description.contains(linkFilter.linkDescription) }
        }

        return currentList
    }
}
