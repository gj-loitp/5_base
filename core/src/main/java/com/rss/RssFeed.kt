package com.rss

import androidx.annotation.Keep

/**
 * RSS Feed response model
 */

@Keep
data class RssFeed(var items: List<RssItem>? = null)
