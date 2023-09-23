import org.apache.spark.graphx._
import org.apache.spark.sql.SparkSession

object PageRank extends App{

  println("hello")
  val spark = SparkSession
    .builder()
    .master("local[1]")
    .appName("GraphX")
    .getOrCreate()

  import spark.implicits._
  val path = "Dataset/cit-HepTh.txt/Cit-HepTh.txt"

  val graph = GraphLoader.edgeListFile(spark.sparkContext, path)

  val ranks = graph.pageRank(0.00001)

  val pageRankDF = ranks.vertices.sortBy(-_._2).toDF()
    .withColumnRenamed("_1", "User")
    .withColumnRenamed("_2", "PageRank")



  pageRankDF.show(10)


}
