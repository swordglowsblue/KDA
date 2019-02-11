 [![](https://img.shields.io/bintray/v/mdashlw/maven/KDA.svg?label=KDA&style=flat-square)](https://bintray.com/mdashlw/maven/KDA/_latestVersion)
 [![](https://img.shields.io/badge/license-MIT-yellowgreen.svg?style=flat-square)](https://opensource.org/licenses/MIT)
 [![](https://gitlab.com/mdashlw/kda/badges/master/pipeline.svg)](https://gitlab.com/mdashlw/kda/commits/master)

# KDA (Kotlin Discord API)

KDA provides Kotlin-specific features for JDA (Java Discord API).

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

##### Groovy DSL

```gradle
repositories {
    jcenter()
}

dependencies {
    implementation 'com.github.mdashl:KDA:VERSION'
}
```

##### Kotlin DSL

```kotlin
repositories {
    jcenter()
}

dependencies {
    implementation("com.github.mdashl:KDA:VERSION")
}
```

## Usage

### Getting Started

To start using KDA, you need to setup it on the JDA object.

```kotlin
jda.setupKDA(
    KDA.Options(
        owner = "OWNER ID",
        staff = listOf("STAFF", "IDS"), // Optional, default is empty
        locale = Locale.US // Optional, default is Locale.US
    )
)
``` 

### Command Handler

KDA provides new simple Command Handler.

#### Setup

```kotlin
CommandHandler.setup(
    CommandHandler.Options(
        prefix = "-",
        defaultColor = Color(146, 75, 245), // Optional, default is Discord's one
        errorColor = Color(238, 40, 31), // Optional, default is [204, 0, 0]
        displayStaffCommandsInHelp = false // Optional, default is false
    )
)
```

#### Register a Command

```kotlin
Command#register()
```

#### Simple Command

```kotlin
object TestCommand : Command() {
    override val aliases: List<String> = listOf("test")
    override val description: String = "just test"
    override val usage: String = "some usage"
    override val sendTyping: Boolean = true // Optional, default is false
    override val displayInHelp: Boolean = false // Optional, default is true

    // Available variables here: [guild: Guild, member: Member, channel: TextChannel, message: Message]

    // The usage is !test
    @GeneralCommand
    fun test() {
        reply(
            title("test embed")
        )
    }

    // The usage is !test blah true
    @GeneralCommand
    fun test(argument1: String, argument2: Boolean) {
        // ...
    }

    // The usage is !test subcmd
    @SubCommand("subcmd")
    fun subcmd() {
        reply("empty subcmd")
    }

    // The usage is !test subcmd2 37
    @SubCommand("subcmd2", "anotheralias")
    fun subcmd2(argument: Int) {
        reply("The number is $argument")
    }

    // The usage is !test subcmd3 any text here
    @SubCommand("subcmd3")
    fun subcmd3(text: Text) {
        reply("Your text: `$text`")
    }
}
```

#### Staff Commands

To make commands staff/owner only, extend from **StaffCommand** or **OwnerCommand** either.

```kotlin
object SimpleStaffCommand : StaffCommand() {
    // ...
}

object SimpleOwnerCommand : OwnerCommand() {
    // ...
}
```

#### Command Contexts

Command Handler supports custom command contexts.

##### Register a Command Context

```kotlin
CommandContext#register()
```

##### Simple Command Context

```kotlin
object SimpleContext : CommandContext<MyCustomType>(MyCustomType::class.java) {
    override fun handle(message: Message, text: Text, arg: String): MyCustomType {
        // ...
    }
}
```

### Modern JDA Builder

KDA provides new modern JDA Builder.

```kotlin
val jda = jda("TOKEN_GOES_HERE") {
    setActivity(Activity.playing("with KDA"))
    // All methods from original JDABuilder
}
```

### Modern Embed Builder

KDA provides new modern Embed Builder.

```kotlin
embed {
    title("Hello!")
    field {
        name("Hello, world")
        value("**Hello, world!**")
    }
    timestamp(OffsetDateTime.now())
    footer {
        text("That's a greeting message")
        icon("url")
    }
}
```

## License

The project is under [MIT license](https://gitlab.com/mdashlw/kda/blob/master/LICENSE).
