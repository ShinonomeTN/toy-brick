/*
 * Hello Minecraft! Launcher.
 * Copyright (C) 2018  huangyuhui <huanghongxun2008@126.com>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see {http://www.gnu.org/licenses/}.
 */
package com.shinonometn.fx

import java.io.File
import java.lang.management.ManagementFactory
import java.nio.charset.Charset
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import javax.management.JMException
import javax.management.ObjectName
import kotlin.math.roundToInt

/**
 * Represents the operating system.
 *
 * @author huangyuhui
 */
enum class OperatingSystem(val checkedName: String) {
    /**
     * Microsoft Windows.
     */
    WINDOWS("windows"),

    /**
     * Linux and Unix like OS, including Solaris.
     */
    LINUX("linux"),

    /**
     * Mac OS X.
     */
    OSX("osx"),

    /**
     * Unknown operating system.
     */
    UNKNOWN("universal");


    companion object {

        /**
         * The current operating system.
         */
        val currentOs: OperatingSystem

        /**
         * The total memory/MB this computer have.
         */
        val totalMemorySize: Int

        /**
         * The suggested memory size/MB for Minecraft to allocate.
         */
        val suggestedMemorySize: Int

        val PATH_SEPARATOR = File.pathSeparator
        val FILE_SEPARATOR = File.separator
        val LINE_SEPARATOR = System.lineSeparator()

        /**
         * The system default encoding.
         */
        val ENCODING = System.getProperty("sun.jnu.encoding", Charset.defaultCharset().name())

        /**
         * The version of current operating system.
         */
        val SYSTEM_VERSION = System.getProperty("os.version")

        /**
         * The architecture of current operating system.
         */
        val SYSTEM_ARCHITECTURE: String

        init {
            val name = System.getProperty("os.name").toLowerCase(Locale.US)
            currentOs = when {
                name.contains("win") -> WINDOWS
                name.contains("mac") -> OSX
                name.contains("solaris") || name.contains("linux") || name.contains("unix") || name.contains("sunos") -> LINUX
                else -> UNKNOWN
            }

            totalMemorySize = totalPhysicalMemorySize
                    .map { bytes -> (bytes!! / 1024 / 1024).toInt() }
                    .orElse(1024)

            suggestedMemorySize = ((1.0 * totalMemorySize / 4.0 / 128.0).roundToInt() * 128)

            var arch = System.getProperty("sun.arch.data.model")
            if (arch == null)
                arch = System.getProperty("os.arch")

            SYSTEM_ARCHITECTURE = arch
        }

        private val totalPhysicalMemorySize: Optional<Long>
            get() {
                val mBeanServer = ManagementFactory.getPlatformMBeanServer()
                try {
                    val attribute = mBeanServer.getAttribute(ObjectName("java.lang", "type", "OperatingSystem"), "TotalPhysicalMemorySize")
                    if (attribute is Long) {
                        return Optional.of(attribute)
                    }
                } catch (e: JMException) {
                    return Optional.empty()
                }

                return Optional.empty()
            }

        fun forceGC() {
            System.gc()
            System.runFinalization()
            System.gc()
        }

        fun getWorkingDirectory(folder: String): Path {
            val home = System.getProperty("user.home", ".")
            return when (OperatingSystem.currentOs) {
                LINUX -> Paths.get(home, ".$folder")
                WINDOWS -> {
                    val appdata = System.getenv("APPDATA")
                    Paths.get(listOf(appdata, home).first { it != null }, folder)
                }
                OSX -> Paths.get(home, "Library", "Application Support", folder)
                else -> Paths.get(home, folder)
            }
        }
    }
}
