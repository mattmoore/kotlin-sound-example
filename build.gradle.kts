import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetPreset

plugins {
    kotlin("multiplatform") version "1.3.41"
}

repositories {
    mavenCentral()
}

kotlin {
    presets.withType<KotlinNativeTargetPreset>().filter { it.name == "macosX64" || it.name == "linuxX64" }.forEach {
        targetFromPreset(it) {
            compilations.getByName("main") {
                val interop by cinterops.creating {
                    defFile(project.file("src/nativeInterop/cinterop/libsdl.def"))
                }

                binaries {
                    executable() {
                        entryPoint = "io.mattmoore.sound.main"
                        baseName = "engine"
                    }
                }
            }
        }
    }

    sourceSets {
        val macosX64Main by getting {
            kotlin.srcDir("src/nativeMain/kotlin")
        }
        val linuxX64Main by getting {
            kotlin.srcDir("src/nativeMain/kotlin")
        }
    }
}
