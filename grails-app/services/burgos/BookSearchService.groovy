package burgos

import groovyx.net.http.HTTPBuilder

import groovyx.net.http.Method

class BookSearchService {

  public void searchBook( String book_name, completion_block  ) {
		def http = new HTTPBuilder('http://www.google.com')
 
		def json_response
		//http.get( path : '/search', query : [q:bookName] )
		
		http.request( 'http://ajax.googleapis.com', groovyx.net.http.Method.GET, groovyx.net.http.ContentType.TEXT ) { req ->
  		uri.path = '/ajax/services/search/web'
  		uri.query = [ v:'1.0', q: book_name ]
  		headers.'User-Agent' = "Mozilla/5.0 Firefox/3.0.4"
  		headers.Accept = 'application/json'
 
  		response.success = { resp, reader ->
    		//assert resp.statusLine.statusCode == 200
    
    	 completion_block( true, reader.text )
  		}
 
  		response.'404' = {
    		println 'Not found'
    		completion_block( false, null )

    	}
		}

		
		
		
  }

}
