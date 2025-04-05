package vku.ddd.social_network_fe.data.utils

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

object FileUploadUtils {

    fun Context.uriToMultipartPart(uri: Uri, partName: String): MultipartBody.Part? {
        val contentResolver = contentResolver

        val cursor = contentResolver.query(uri, null, null, null, null)
        val nameIndex = cursor?.getColumnIndex(OpenableColumns.DISPLAY_NAME) ?: -1
        val fileName = if (cursor != null && cursor.moveToFirst() && nameIndex != -1) {
            cursor.getString(nameIndex)
        } else {
            "temp_file_${System.currentTimeMillis()}"
        }
        cursor?.close()

        val inputStream = contentResolver.openInputStream(uri) ?: return null
        val tempFile = File.createTempFile("upload_", fileName, cacheDir)
        tempFile.outputStream().use { outputStream ->
            inputStream.copyTo(outputStream)
        }

        val mediaType = contentResolver.getType(uri)?.toMediaTypeOrNull()
        val requestBody = tempFile.asRequestBody(mediaType)
        return MultipartBody.Part.createFormData(partName, fileName, requestBody)
    }

    fun Context.urisToMultipartParts(uris: List<Uri>, partName: String): List<MultipartBody.Part> {
        return uris.mapNotNull { uri ->
            uriToMultipartPart(uri, partName)
        }
    }
}
