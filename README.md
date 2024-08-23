# LogMaster

**LogMaster** is a versatile logging plugin for Paper/Spigot servers that allows server administrators to log various
player activities, such as chat, commands, and session events. The plugin supports configurable logging modes,
customizable log formats, and permission-based access control. It is compatible with both Paper and Spigot servers
running Minecraft 1.21+.

---

## Features

- **Configurable Logging Modes**: Choose between "combined" logs for all players, "player" logs for individual
  players, "both" modes, or disable specific logs.
- **Customizable Log Formats**: Define the format of log entries using placeholders like `{timestamp}`, `{player}`, and
  `{message}`.
- **Permission-Based Control**: Manage who is exempt from logging and who can use administrative commands.

## Commands

### `/logmaster reload`

Reloads the LogMaster configuration from the `config.yml` file.  
**Permission**: `logmaster.admin`  
**Usage**: `/logmaster reload`

## Permissions

- **`logmaster.admin`**: Allows the player to use LogMaster administrative commands (e.g., reload the configuration).
- **`logmaster.ignore`**: Exempts the player from all logging types.
- **`logmaster.ignore.commands`**: Exempts the player from command logging.
- **`logmaster.ignore.chat`**: Exempts the player from chat logging.
- **`logmaster.ignore.sessions`**: Exempts the player from session logging.

## Configuration

### `config.yml`

```yaml
log: # Options: "combined", "player", "both", "off"
  chat: "combined"
  commands: "combined"
  sessions: "combined"

files:
  chat: "{date}_chat.log"
  commands: "{date}_commands.log"
  sessions: "{date}_sessions.log"

format:
  chat: "{timestamp} - {player} » {message}"
  commands: "{timestamp} - {player} » {command}"
  sessions: "{timestamp} - {player} » {message}"
```

---

## License

Copyright © 2024 [Tyler Richards](https://github.com/tjrgg). [MIT License](LICENSE).
