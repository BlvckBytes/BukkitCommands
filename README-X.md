<!-- This file is rendered by https://github.com/BlvckBytes/readme_helper -->

# BukkitCommands

A simple bukkit command library which offers boilerplate code as well as configuration sections
for fully configurable commands and messages as well as error handling.

<!-- #toc -->

## Command Section

```yaml
my_command:
  # Main name of the command
  name: 'my-command'
  # Description printed in the vanilla help menu
  description: 'my fancy command'
  # "Secondary names" of the command
  aliases:
    - mycmd
    - myc
  # A map, assigning a usage to the count of currently provided arguments
  # This can be used to print usage messages with - for example - highlighted arguments
  argumentUsages:
    0$: '"&7/" & alias & " &c<player> &7<message>"'
    1$: '"&7/" & alias & " &7<player> &c<message>"'
  # Error messages used for various cases
  # All of the following properties have at least this environment:
  # value: String? - Current argument value, or null if there's no argument present
  # alias: String - Alias used to invoke this command, can be the main name
  # sender_name: String - Name of the command sender
  errorMessages:
    # An argument of type double has been malformed
    malformedDouble$: '"&7The value &c" & value & " &7is not a valid &cdouble"'
    # An argument of type float has been malformed
    malformedFloat$: '"&7The value &c" & value & " &7is not a valid &cfloat"'
    # An argument of type long has been malformed
    malformedLong$: '"&7The value &c" & value & " &7is not a valid &clong"'
    # An argument of type integer has been malformed
    malformedInteger$: '"&7The value &c" & value & " &7is not a valid &cinteger"'
    # An argument of type UUID-V4 has been malformed
    malformedUuid$: '"&7The value &c" & value & " &7is not a valid &cUUID"'
    # An argument which can only take on certain pre-defined values has been malformed
    # constant_names: List<String> - Available values to choose from
    malformedEnum$: |
      "&7The value &c" & value & " &7is not one of (" &
      iter_cat(constant_names, (constant_name) => "&c" & constant_name, "&7, ", "&cNo values available") &
      "&7)"
    # This command is only available for players, but has been invoked by the console
    notAPlayer: '&7This command can only be executed as a &cplayer'
    # An argument which had to be the name of a known player had an invalid value
    playerUnknown$: '"&7The player &c" & value & " &7has &cnot played &7on this server before"'
    # An argument which had to be the name of a online player had an invalid value
    playerNotOnline$: '"&7The player &c" & value & " &7is &cnot online"'
    # Any other, internal error occurred
    internalError: '&4An internal error occurred'
```