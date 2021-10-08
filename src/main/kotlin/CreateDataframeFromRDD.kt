import org.apache.spark.rdd.RDD
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.Row
import org.apache.spark.sql.SparkSession
import org.jetbrains.kotlinx.spark.api.*

data class InternalClass(val content: String)
data class A(val internalClass: InternalClass)

fun main() {
    val spark = SparkSession
        .builder()
        .master("local[2]")
        .config("spark.driver.host","127.0.0.1")
        .appName("Simple Application").orCreate

    val rdd: RDD<A> = spark.toDS(listOf(A(InternalClass("some content"))))
        .rdd()

    val dataFrame: Dataset<Row> = spark.createDataFrame(rdd, A::class.java)

    dataFrame.show(false)
}