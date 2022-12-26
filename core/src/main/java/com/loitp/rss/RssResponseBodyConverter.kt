package com.loitp.rss

import okhttp3.ResponseBody
import org.xml.sax.InputSource
import retrofit2.Converter
import javax.xml.parsers.SAXParserFactory

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
internal class RssResponseBodyConverter : Converter<ResponseBody, RssFeed> {

    override fun convert(value: ResponseBody): RssFeed {
        val rssFeed = RssFeed()
        try {
            val parser = XMLParser()
            val parserFactory = SAXParserFactory.newInstance()
            val saxParser = parserFactory.newSAXParser()
            val xmlReader = saxParser.xmlReader
            xmlReader.contentHandler = parser
            val inputSource = InputSource(value.charStream())
            xmlReader.parse(inputSource)
            val items = parser.items
            rssFeed.items = items
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return rssFeed
    }
}
