 [ ![Download](https://api.bintray.com/packages/mdashlw/maven/KDA/images/download.svg) ](https://bintray.com/mdashlw/maven/KDA/_latestVersion)
 [![Pipeline status](https://gitlab.com/mdashlw/kda/badges/master/pipeline.svg)](https://gitlab.com/mdashlw/kda/commits/master)

# Kotlin Discord API

Kotlin utilities for JDA library.

## Importing

Replace `VERSION` with the latest version above.

### Maven

```xml
<depedencies>
    <dependency>
        <artifactId>KDA</artifactId>
        <groupId>com.github.mdashl</groupId>
        <scope>compile</scope>
        <version>VERSION</version> 
  </dependency>
</depedencies>

<repositories>
    <repository>
      <id>jcenter</id>
      <url>https://jcenter.bintray.com/</url>
    </repository>
</repositories>
```

### Gradle

**Groovy DSL**:

```gradle
repositories {
    jcenter()
}

dependencies {
    implementation 'com.github.mdashl:KDA:VERSION'
}
```

**Kotlin DSL**:

```kotlin
repositories {
    jcenter()
}

dependencies {
    implementation("com.github.mdashl:KDA:VERSION")
}
```

### JAR artifact

You can download latest [artifact](https://bintray.com/mdashlw/maven/KDA/_latestVersion) of build and add it to classpath of your project.

## Getting Started

```kotlin
jda.setupKDA(KDA.Options(owner = "OWNER ID", staff = listOf("STAFF", "IDS"), locale = Locale.US))
``` 

## Features

### Modern Command Handler

This API provides new Command Handler.

#### Setup

```kotlin
CommandHandler.setup(CommandHandler.Options(prefix = "!"))
```

#### Register Command

```kotlin
command.register()
```

#### Simple Command

```kotlin
object TestCommand : Command() {
    override val aliases: List<String> = listOf("test")
    override val description: String = "just test"
    override val usage: String = "some usage"
    
    // Available variables here: guild: Guild, member: Member, channel: TextChannel, message: Message

    @GeneralCommand
    fun test() {
        reply(
            title("test embed")
        )
    }

    @GeneralCommand
    fun test(argument1: String, argument2: Boolean) {
        // ...
    }

    @SubCommand("subcmd")
    fun subcmd() {
        reply("empty subcmd")
    }

    @SubCommand("subcmd2", "anotheralias")
    fun subcmd2(argument: Int) {
        reply("The number is $argument")
    }
}
```

#### Simple Owner Command

Only owner has access to the owner commands.

```kotlin
object SimpleOwnerCommand : OwnerCommand() {
    // ...
}
```

#### Simple Staff Command

Only staff has access to the staff commands.

```kotlin
object SimpleStaffCommand : StaffCommand() {
    // ...
}
```

#### Command Contexts

Command Handler supports custom command contexts.

##### Register Command Context

```kotlin
context.register()
```

##### Simple Command Context

```kotlin
object SimpleContext : CommandContext<MyCustomType>(MyCustomType::class.java) {
    override fun handle(message: Message, arg: String): MyCustomType {
        // ...
    }
}
```

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