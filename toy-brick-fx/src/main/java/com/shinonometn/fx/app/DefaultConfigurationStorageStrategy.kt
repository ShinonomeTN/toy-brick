package com.shinonometn.fx.app

import com.shinonometn.fx.OperatingSystem
import java.io.File

enum class DefaultConfigurationStorageStrategy : ConfigurationStorageStrategy {
    CURRENT_DIRECTORY {
        override fun fileProvider(): File = File("./settings.json")
    },
    USER_HOME {
        override fun fileProvider(): File {
            return OperatingSystem.getWorkingDirectory(".toy_brick_fx").toFile()
        }

    };
}