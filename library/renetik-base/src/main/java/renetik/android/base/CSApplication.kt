package renetik.android.base

import android.app.Application
import android.os.Environment.getExternalStorageDirectory
import renetik.android.base.CSApplicationObject.application
import renetik.android.extensions.applicationLabel
import renetik.android.java.extensions.exception
import renetik.android.logging.AndroidLogger
import renetik.android.logging.CSLog.logInfo
import renetik.android.logging.CSLog.logWarn
import renetik.android.logging.CSLogger
import renetik.android.task.CSDoLaterObject.initializeDoLater
import java.io.File

object CSApplicationObject {
    lateinit var application: CSApplication
}

open class CSApplication : Application() {

    open val name: String by lazy { applicationLabel }
    open val logger: CSLogger by lazy { AndroidLogger() }
    open val store: CSValueStore by lazy { CSValueStore("ApplicationSettings") }
    open val externalFilesDir: File
        get() = getExternalFilesDir(null) ?: getExternalStorageDirectory()
    open val isDebugBuild: Boolean
        get() {
            throw exception("You need to override this if like to use it in your implementation of CSApplication," +
                    " because BuildConfig.DEBUG returns true only in debugged module")
        }

    override fun onCreate() {
        super.onCreate()
        initializeDoLater()
        application = this
    }

    override fun onLowMemory() {
        super.onLowMemory()
        logWarn("onLowMemory")
        logger.onLowMemory()
    }

    override fun onTerminate() {
        super.onTerminate()
        logInfo("onTerminate")
    }
}
