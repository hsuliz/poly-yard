package dev.hsuliz.bookservice.review

import dev.hsuliz.bookservice.review.model.Review
import dev.hsuliz.bookservice.review.model.ReviewWithBookAndUserDto
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository : CoroutineCrudRepository<Review, Long> {

  fun findReviewsByUserId(userId: Long): Flow<Review>

  @Query(
      """
          SELECT 
          review.id AS review_id,
          review.rating AS review_rating,
          review.comment AS review_comment,
          review.created_at AS review_created_at,
          book.title AS book_title,
          user_.id AS user_id,
          user_.name AS user_name,
          book.id AS book_id,
          book.title AS book_title,
          book.author AS book_author,
          book.pages AS book_pages,
          book.image AS book_image,
          book.isbn AS book_isbn,
          book.published_date AS book_published_date
        FROM reviews AS review 
        NATURAL JOIN users AS user_
        NATURAL JOIN books AS book
    """)
  fun findAllReviewsWithBookAndUser(): Flow<ReviewWithBookAndUserDto>
}
