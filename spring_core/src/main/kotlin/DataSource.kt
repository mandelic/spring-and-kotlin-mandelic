import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

data class DataSource(
    val dbName: String,
    val username: String,
    val password: String
)