 [![Bintray](https://img.shields.io/bintray/v/mdashlw/maven/KDA.svg?label=KDA&style=flat-square)](https://bintray.com/mdashlw/maven/KDA/_latestVersion)
 [![GitHub](https://img.shields.io/github/license/mdashl/KDA.svg?style=flat-square)](https://choosealicense.com/licenses/mit/)
 [![Travis (.com)](https://img.shields.io/travis/com/mdashl/KDA.svg?style=flat-square)](http://travis-ci.com/mdashl/KDA)
 ![GitHub last commit](https://img.shields.io/github/last-commit/mdashl/KDA.svg?style=flat-square)
 ![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/mdashl/KDA.svg?style=flat-square)

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
        prefix = "/"

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

#### Example Command

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
    fun subcmd3(@Text text: String) {
        reply("Your text: `$text`")
    }

    // The usage is !test subcmd4 @member OR !test subcmd4 member's name
    // Note: name variable target, member is already declared in Command class
    @SubCommand("subcmd4")
    fun subcmd4(target: Member) {
        // ...
    }

    // The usage is !test subcmd5 somevalueidk
    // See Command Contexts for custom types deserialization
    @SubCommand("subcmd5")
    fun subcmd5(myCustomType: MyCustomType) {
        // ...
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

##### Example Command Context

```kotlin
object SimpleContext : CommandContext<MyCustomType>(MyCustomType::class.java) {

    override fun handle(message: Message, text: String, arg: String): MyCustomType {
        // ...
    }

}
```

### Modern Embed Builder

```kotlin
embed {
    title = "Hello!"
    description += "test" // Note: +=
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

### Event Waiter

Event Waiter will handle every instance of this event until predicate is complete and action is executed.

```kotlin
// When JDA receive a message from user with ID `1234567890123` in channel `0123456780123`,
// it will execute the action and stop listening to an event.
JDA#wait<GuildMessageReceivedEvent>({ author.id == "1234567890123" && channel.id == "0123456780123" }) {
    // ...
}
```

### Extensions

#### JDA

###### JDA#handlerOf

Registers a listener of an event.

**Note**: It's still recommended to implement `ListenerAdapter`, use this extension for very simple listeners.

```kotlin
JDA#handlerOf<GuildMessageReactionAddEvent> { event ->
    // ...
}
```

#### User

###### User#isOwner

Returns true if a user is **an owner** and false if they are not.

```kotlin
User#isOwner()
```

###### User#isStaff

Returns true if a user is **staff** and false if they are not.

```kotlin
User#isStaff()
```

#### Text Channel

###### TextChannel#send

Better sendMessage method.
Content is optional.

**Note**: Extension returns `MessageAction`, so `.queue()` is still required.

```kotlin
TextChannel#send("Content") {
    // Embed Builder

    title = "test"
    field {
        // ...
    }
}
```

## License

The project is licensed under the **[MIT license](https://choosealicense.com/licenses/mit/)**.
