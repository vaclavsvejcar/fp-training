package common.logic

import cats.effect.IO
import common.domain.ImdbMovie
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.browser.JsoupBrowser.JsoupDocument
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._

/**
  * IMDB Scraper - uses `scalascraper` library to fetch data from IMDB.
  */
object ImdbScraper {

  private val SiteURL = "http://akas.imdb.com/chart/top"
  private val browser = new JsoupBrowser()

  /**
    * Fetch TOP N rated movies from ''IMDB''.
    *
    * @param noOfMovies number of movies to fetch
    * @return fetched movies
    */
  def fetchTopN(noOfMovies: Int): IO[Seq[ImdbMovie]] = fetchDocument(SiteURL).map(parseTopN(noOfMovies))

  private def fetchDocument(url: String) = IO { browser.get(SiteURL) }

  private def parseTopN(noOfMovies: Int)(doc: JsoupDocument): Seq[ImdbMovie] = {
    (doc >> elementList(".lister-list tr")).take(10).map { movie =>
      val title  = (movie >> element(".titleColumn a")).innerHtml
      val rating = (movie >> element(".ratingColumn strong")).innerHtml.replace(",", ".").toDouble
      ImdbMovie(title, rating)
    }
  }
}
