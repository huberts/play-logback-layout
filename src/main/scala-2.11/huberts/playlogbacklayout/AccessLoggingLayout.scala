package huberts.playlogbacklayout

import java.text.SimpleDateFormat
import java.util.Calendar
import java.lang.StringBuilder

import ch.qos.logback.classic.html.HTMLLayout
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.CoreConstants._
import ch.qos.logback.core.helpers.Transform
import ch.qos.logback.core.pattern.Converter

class AccessLoggingLayout extends HTMLLayout {
  title = "Dziennik zdarzeń dostępu do bazy danych"

  override def getFileHeader: String = {
    "" +
      "<!DOCTYPE html>" + LINE_SEPARATOR +
      "<html>" + LINE_SEPARATOR +
      "  <head>" + LINE_SEPARATOR +
      "    <meta charset=\"UTF-8\">" + LINE_SEPARATOR +
      "    <title>" + getTitle + "</title>" + LINE_SEPARATOR +
      "    <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css\" crossorigin=\"anonymous\">" + LINE_SEPARATOR +
      "    <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css\" crossorigin=\"anonymous\">" + LINE_SEPARATOR +
      "  </head>" + LINE_SEPARATOR +
      "  <body class=\"container\">" + LINE_SEPARATOR
  }

  override def getPresentationHeader: String = {
    val dateFormatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss")
    "" +
      "<table class=\"table table-striped\">" + LINE_SEPARATOR +
      "<caption>Start programu: " + dateFormatter.format(Calendar.getInstance.getTime) + "</caption>" + LINE_SEPARATOR +
      buildHeaderRowForTable +
      "<tbody>" + LINE_SEPARATOR
  }

  override def getPresentationFooter: String = {
    "" +
      "</tbody>" + LINE_SEPARATOR +
      "</table>" + LINE_SEPARATOR
  }

  override def doLayout(event: ILoggingEvent): String = {
    val buf = new StringBuilder
    startNewTableIfLimitReached(buf)
    var row = buf.toString + LINE_SEPARATOR
    row += "<tr>" + LINE_SEPARATOR
    var converter = head
    while (converter != null) {
      row += "<td>" + unescapeSafeTags(Transform.escapeTags(converter.convert(event))) + "</td>" + LINE_SEPARATOR
      converter = converter.getNext
    }
    row + "</tr>" + LINE_SEPARATOR
  }

  private def unescapeSafeTags(escaped: String): String = {
    escaped.replace("&lt;br&gt;", "<br>")
  }

  private def buildHeaderRowForTable: String = {
    var header = "" +
      "<thead>" + LINE_SEPARATOR +
      "<tr>" + LINE_SEPARATOR
    var converter = head
    while (converter != null) {
      val name = computeConverterName(converter)
      if (name != null) {
        header += "<th class=\"" + computeConverterName(converter) + "\">" + computeConverterName(converter) + "</th>" + LINE_SEPARATOR
      }
      converter = converter.getNext
    }
    header +
      "</tr>" + LINE_SEPARATOR +
      "</thead>" + LINE_SEPARATOR
  }


  override def computeConverterName(c: Converter[_]): String = {
    super.computeConverterName(c) match {
      case "Date" => "Data"
      case "Level" => "Poziom"
      case "Message" => "Treść"
      case default => default
    }
  }

  override protected def startNewTableIfLimitReached(sbuf: StringBuilder) {
    if (counter >= TABLE_ROW_LIMIT) {
      counter = 0
      sbuf.append("</tbody>")
      sbuf.append(LINE_SEPARATOR)
      sbuf.append("</table>")
      sbuf.append(LINE_SEPARATOR)
      sbuf.append("<table cellspacing=\"0\">")
      sbuf.append(LINE_SEPARATOR)
      sbuf.append(buildHeaderRowForTable)
    }
  }
}
