package burgos



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(BookSearchService)
class BookSearchServiceTests {

	def TEST_BOOK_NAME = 'The definitive guide to Grails'
    
  void testBookSearch() {
  	def completion_block = { success, response ->  
  		println response
  		assert success == true
  		assert response
    }
    service.searchBook( TEST_BOOK_NAME, completion_block )
    
  }

}
