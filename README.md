<!-- This file is rendered by https://github.com/BlvckBytes/readme_helper -->

# BukkitCommands

A simple bukkit command library which offers boilerplate code as well as configuration sections
for fully configurable commands and messages as well as error handling.

## Table of Contents
- [Command Section](#command-section)
  - [name](#name)
  - [description](#description)
  - [usage](#usage)
  - [aliases](#aliases)
  - [argumentUsages](#argumentusages)
  - [errorMessages](#errormessages)
    - [malformedDouble](#malformeddouble)
    - [malformedFloat](#malformedfloat)
    - [malformedLong](#malformedlong)
    - [malformedInteger](#malformedinteger)
    - [malformedUuid](#malformeduuid)
    - [malformedEnum](#malformedenum)
    - [notAPlayer](#notaplayer)
    - [playerUnknown](#playerunknown)
    - [playerNotOnline](#playernotonline)
    - [internalError](#internalerror)

## Command Section

Neither command names, aliases nor messages should be hard coded in my personal opinion, which is why
the command section offers keys for all of these values.

### name

The main name of the command, of type **String**.

```
name: 'example'
```

### description

The description printed in the vanilla help menu, of type **String**.

```
description: 'my example command'
```

### usage

The usage printed in the vanilla help menu, of type **String**.

```
usage: 'my example usage'
```

### aliases

"Secondary names" of the command, of type **List\<String\>**.

```
aliases:
  - ex
  - e
```

### argumentUsages

A map, assigning a usage **String** to the **Integer** of currently provided arguments, of type **Map<Integer, String>**.
This map can be used to print usage messages with - for example - highlighted arguments.

```
argumentUsages:
  0$: '"&7/" & alias & " &c<player> &7<message>"'
  1$: '"&7/" & alias & " &7<player> &c<message>"'
```

### errorMessages

Error messages used for various cases, a section containing the following properties:

All properties have at least the following environment:

| Variable Name | Description                                                    | Type    |
|---------------|----------------------------------------------------------------|---------|
| value         | Current argument value, or null if there's no argument present | String? |
| alias         | Alias used to invoke this command, can be the main name        | String  |
| sender_name   | Name of the command sender                                     | String  |

#### malformedDouble

An argument of type double has been malformed.

```
malformedDouble$: '"&7The value &c" & value & " &7is not a valid &cdouble"'
```

#### malformedFloat

An argument of type float has been malformed.

```
malformedFloat$: '"&7The value &c" & value & " &7is not a valid &cfloat"'
```

#### malformedLong

An argument of type long has been malformed.

```
malformedLong$: '"&7The value &c" & value & " &7is not a valid &clong"'
```

#### malformedInteger

An argument of type integer has been malformed.

```
malformedInteger$: '"&7The value &c" & value & " &7is not a valid &cinteger"'
```

#### malformedUuid

An argument of type UUID-V4 has been malformed.

```
malformedUuid$: '"&7The value &c" & value & " &7is not a valid &cUUID"'
```

#### malformedEnum

An argument which can only take on certain pre-defined values has been malformed. These values are available within the
property's environment by the value `constant_names` of type **List\<String\>**.

```
malformedEnum$: |
  "&7The value &c" & value & " &7is not one of (" &
  iter_cat(constant_names, (constant_name) => "&c" & constant_name, "&7, ", "&cNo values available") &
  "&7)"
```

#### notAPlayer

This command is only available for players, but has been invoked by the console.

```
notAPlayer: '&7This command can only be executed as a &cplayer'
```

#### playerUnknown

An argument which had to be the name of a known player had an invalid value.

```
playerUnknown$: '"&7The player &c" & value & " &7has &cnot played &7on this server before"'
```

#### playerNotOnline

An argument which had to be the name of a online player had an invalid value.

```
playerNotOnline$: '"&7The player &c" & value & " &7is &cnot online"'
```

#### internalError

Any other, internal error occurred.

```
internalError: '&4An internal error occurred'
```