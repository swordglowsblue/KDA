 [![](https://img.shields.io/bintray/v/mdashlw/maven/KDA.svg?label=KDA&style=flat-square)](https://bintray.com/mdashlw/maven/KDA/_latestVersion)
 [![](https://img.shields.io/badge/license-MIT-yellowgreen.svg?style=flat-square)](https://opensource.org/licenses/MIT)
 [![](https://gitlab.com/mdashlw/kda/badges/master/pipeline.svg)](https://gitlab.com/mdashlw/kda/commits/master)

# KDA (Kotlin Discord API)

KDA provides Kotlin-specific features for JDA (Java Discord API).

## Importing

Replace `VERSION` with the latest version above.

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

## Usage

### Client Builder

```kotlin
// Method returns JDA

client {
    // Required
    token = "TOKEN"
    owner = "OWNER_ID"

    // KDA Optional
    staff = listOf("STAFF_ID", "ANOTHER_STAFF_ID")
    locale = Locale.US

    // JDA Optional
    activity = Activity.watching("your home")
    status = OnlineStatus.IDLE
}
```

### Command Handler

```kotlin
client {
    // ...

    commandhandler {
        token = "/"

        // Optional
        defaultColor = Color(146, 75, 245) // Default - Discord's gray
        errorColor = Color(238, 40, 31) // Default - [204, 0, 0]
        displayStaffCommandsInHelp = false // Default - false
    }
}
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

    // Optional
    override val sendTyping: Boolean = true // Default - false
    override val displayInHelp: Boolean = false // Default - true

    /*
        Available variables here:
            [guild: Guild, member: Member, channel: TextChannel, message: Message]
    */

    // Optional
    override fun checkPermission(): Boolean {
        // ...
    }

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

To make commands staff/owner only, extend from **StaffCommand** / **OwnerCommand**.

```kotlin
object SimpleStaffCommand : StaffCommand() {
    // ...
}

```

```kotlin
object SimpleOwnerCommand : OwnerCommand() {
    // ...
}
```

#### Command Contexts

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

### Modern Embed Builder

```kotlin
embed {
    title = "Hello!"
    description += "test"
    field {
        name = "Hello, world"
        value = "**Hello, world!**"
    }
    timestamp = OffsetDateTime.now()
    footer {
        text = "That's a greeting message"
        icon = "url"
    }
}
```

### Extensions

#### JDA

```kotlin
JDA#handlerOf<GuildMessageReactionAddEvent> { event ->
    // ...
}
```

#### User

```kotlin
User#isOwner()
```

```kotlin
User#isStaff()
```

#### Text Channel

```kotlin
// Extension returns MessageAction, so `.queue()` is still required.

TextChannel#send("Content") {
    // Embed Builder

    title = "test"
    field {
        // ...
    }
}
```

## License

The project is under [MIT license](https://gitlab.com/mdashlw/kda/blob/master/LICENSE).
