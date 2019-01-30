# KDA [![Pipeline status](https://gitlab.com/mdashlw/kda/badges/master/pipeline.svg)](https://gitlab.com/mdashlw/kda/commits/master)

JDA Utilities for Kotlin.

## Importing

### Maven

```xml
<depedencies>
    <dependency>
        <artifactId>kda</artifactId>
        <groupId>com.github.mdashl</groupId>
        <scope>compile</scope>
        <version>1.0</version> 
  </dependency>
</depedencies>

<repositories>
    <repository>
        <id>gitlab-maven</id>
        <url>https://gitlab.com/api/v4/projects/10590152/packages/maven</url>
    </repository>
</repositories>
```

### Gradle

**Groovy DSL**:

```gradle
repositories {
    maven { url 'https://gitlab.com/api/v4/projects/10590152/packages/maven' }
}

dependencies {
    implementation 'com.github.mdashl:kda:1.0'
}
```

**Kotlin DSL**:

```kotlin
repositories {
    maven("https://gitlab.com/api/v4/projects/10590152/packages/maven")
}

dependencies {
    implementation("com.github.mdashl:kda:1.0")
}
```

### JAR artifact

You can download latest [artifact](https://gitlab.com/mdashlw/kda/pipelines) of build and add it to classpath of your project. 

## Features

### Modern JDA Builder

This API provides new JDA Builder.

```kotlin
import com.github.mdashl.kda.jda

val jda = jda("TOKEN_GOES_HERE") {
    setActivity(Activity.playing("with KDA"))
    // All methods from original JDABuilder
}
```

### Modern Embed Builder

This API provides new Embed Builder.

```kotlin
import com.github.mdashl.kda.embed
import java.time.OffsetDateTime

embed {
    title("Hello!")
    field {
        name("Hello, world")
        value("**Hello, world!**")
    }
    timestamp(OffsetDateTime.now())
    footer {
        text("That's a greeting message")
    }
}
```

## License

The project is under [MIT license](https://gitlab.com/mdashlw/kda/blob/master/LICENSE).