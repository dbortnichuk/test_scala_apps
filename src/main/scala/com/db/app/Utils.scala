package com.db.app

import com.db.app.Models.Param
import org.apache.commons.io.IOUtils.toByteArray

import java.io.{FileInputStream, InputStream}
import java.util.Properties
import scala.util.control.NonFatal

object Utils {

  def readResource(pathToResource: String): Array[Byte] = toByteArray(getClass.getResourceAsStream(pathToResource))

  def loadProperties(dataPath: Option[String]): Properties = {
    val dataProperties = new Properties()
    dataPath match {
      case None => {
        dataProperties.load(getClass.getResourceAsStream("/DefaultSimpleData.properties"))
      }
      case Some(path) => {
        var fis: InputStream = null
        try {
          fis = new FileInputStream(path)
          dataProperties.load(fis);
        } catch {
          case NonFatal(t) => throw t
        } finally {
          if ( fis != null) fis.close()
        }
      }
    }
    dataProperties
  }

  def buildURLQuery(baseUrl: String, path: Seq[String], params: Seq[Param] = Seq.empty[Param]): String = {
    val baseUrlStripped = if(baseUrl.endsWith("/")) baseUrl.substring(0, baseUrl.length - 1) else baseUrl
    val urlPath = if (path.isEmpty) baseUrlStripped else s"$baseUrlStripped/${path.mkString("/")}"
    val urlQuery = if (params.isEmpty) urlPath else s"$urlPath?${params.map(param => s"${param.k}=${param.v}").mkString("&")}"
    urlQuery
  }

  def main(args: Array[String]): Unit = {
    println(buildURLQuery("http://example.com", Seq("v1", "path"), Seq(Param("apiKey", "123"), Param("ds", "s3"))))
  }

}
