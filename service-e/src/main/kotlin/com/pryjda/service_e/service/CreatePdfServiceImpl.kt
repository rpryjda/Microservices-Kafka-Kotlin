package com.pryjda.service_e.service

import org.apache.commons.io.IOUtils
import org.springframework.stereotype.Service
import java.io.*
import java.lang.RuntimeException
import java.util.*

@Service
class CreatePdfServiceImpl : CreatePdfService {

    val command: String = "wkhtmltopdf - -"

    override fun createPdf(): String {
        val sourceFile = File("src/main/resources/Test.html")
        val fis = FileInputStream(sourceFile)
        val fos = ByteArrayOutputStream()
        runProcess(fis, fos)
        val result = Base64.getEncoder().encodeToString(fos.toByteArray())
        return result
    }

    private fun runProcess(html: InputStream, outputFile: OutputStream) {
        val wkhtml: Process = Runtime.getRuntime().exec(command)
        val errors = ByteArrayOutputStream()
        val errorThread: Thread = createErrorThread(wkhtml, errors)
        val htmlReadThread: Thread = createHtmlReadThread(wkhtml, html)
        val pdfWriteThread: Thread = createPdfWriteThread(wkhtml, outputFile)
        errorThread.start()
        pdfWriteThread.start()
        htmlReadThread.start()
        wkhtml.waitFor()
    }

    private fun createErrorThread(wkhtml: Process, outputStream: OutputStream): Thread =
            Thread {
                try {
                    IOUtils.copy(wkhtml.errorStream, outputStream)
                } catch (e: IOException) {
                    throw RuntimeException()
                }
            }

    private fun createHtmlReadThread(wkhtml: Process, html: InputStream): Thread =
            Thread {
                try {
                    IOUtils.copy(html, wkhtml.outputStream)
                    wkhtml.outputStream.flush()
                    wkhtml.outputStream.close()
                } catch (e: IOException) {
                    throw RuntimeException()
                }
            }

    private fun createPdfWriteThread(wkhtml: Process, outputFile: OutputStream): Thread =
            Thread {
                try {
                    IOUtils.copy(wkhtml.inputStream, outputFile)
                } catch (e: IOException) {
                    throw RuntimeException()
                }
            }
}