package burgos

import groovy.json.*

import groovyx.net.http.HTTPBuilder

import groovyx.net.http.Method

class BookSearchService {

  public void searchBook( String book_name, completion_block  ) {
		def http = new HTTPBuilder('http://www.google.com')
 					
		http.request( 'http://ajax.googleapis.com', groovyx.net.http.Method.GET, groovyx.net.http.ContentType.TEXT ) { req ->
  		uri.path = '/ajax/services/search/web'
  		uri.query = [ v:'1.0', q: book_name ]
  		headers.'User-Agent' = "Mozilla/5.0 Firefox/3.0.4"
  		headers.Accept = 'application/json'
 
  		response.success = { resp, reader ->
       def books_from_amazon = amazon_books_from_json( reader.text )
       if ( books_from_amazon ) {
        def book_candidate = filter_amazon_results( books_from_amazon, book_name )

        completion_block( true,  book_candidate )
       } else {
         completion_block( false, null )
       }

  		}
 
  		response.'404' = {
    		println 'Not found'
    		completion_block( false, [] )

    	}
		}
						
  }

  private List amazon_books_from_json( String json )
  {
    def result = new JsonSlurper().parseText(json)
    if (result.responseStatus == 200 ) {
      def amazon_books = result.responseData.results?.findAll {
        it['visibleUrl'] == 'www.amazon.com'
      }
      return amazon_books
    }
    return []
  }

  def filter_amazon_results( List results, String book_name) {
    results.find { it['title'].toUpperCase().indexOf(book_name.toUpperCase() ) != -1 }
  }
}
