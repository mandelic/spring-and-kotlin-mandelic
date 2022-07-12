import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableMBeanExport
import org.springframework.context.annotation.PropertySource
import org.springframework.scheduling.annotation.EnableAsync

fun main() {
    val applicationContext: ApplicationContext = AnnotationConfigApplicationContext(ApplicationConfig::class.java)
    val service = applicationContext.getBean(CarCheckUpSystem::class.java)
    //println(service.getCheckUps())
    println(service.addCheckUp("HFDLDSA7FSDJ"))
}

@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
class ApplicationConfig