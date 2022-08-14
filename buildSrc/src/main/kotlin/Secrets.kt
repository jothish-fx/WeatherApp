/*
 * Created by Jothish on 18/11/21, 7:28 PM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 18/11/21, 7:28 PM
 */

import java.io.File
import java.io.FileInputStream
import java.util.*

object Secrets {
    private const val API_VERSION = "API_VERSION"
    private const val BASE_URL = "BASE_URL"
    private const val API_KEY = "API_KEY"

    //already given
    val apiKey: String = keysProperties().run { getProperty(API_KEY) } ?: ""
    val baseUrl: String = keysProperties().run { getProperty(BASE_URL) } ?: ""
    val apiVersion: String = keysProperties().run { getProperty(API_VERSION) } ?: ""


    private fun keysProperties(): Properties {
        val filename = "keys.properties"
        val file = File(
            filename
        )
        if (!file.exists()) {
            throw Error(
                "You need to prepare a file called $filename in the project root directory.\n" +
                        "and contain the BASE_URL key and API_VERSION key.\n" +
                        "the content of the file should look something like:\n\n" +
                        "(project root)$ cat $filename\n" +
                        "$BASE_URL=www.google.com\n" +
                        "$API_VERSION=v1\n" +
                        "$API_KEY=abcde123253\n"
            )

        }
        val properties = Properties()
        properties.load(FileInputStream(file))
        return properties
    }
}

