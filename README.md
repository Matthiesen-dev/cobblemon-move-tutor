# Cobblemon Move Tutor

This mod adds a new NPC to your Cobblemon world that can teach your Pokemon new moves. The Move Tutor, set to spawn 
near Minecraft villages, offers a variety of moves that your Pokemon can learn, enhancing their abilities and making 
them more versatile in battles.

## Requirements
- [Cobblemon](https://modrinth.com/mod/cobblemon)
- [Matthiesen Lib](https://modrinth.com/mod/matthiesen-lib)

### Optional compatibility
- [CobbleDollars](https://modrinth.com/mod/cobbledollars)
- [Impactor](https://modrinth.com/mod/impactor)

## Configuration

The configuration directory is located at `<game_directory>/config/cobblemon_move_tutor/`. Inside this directory, you will find the following files:

- `common.json` - Contains the general configuration settings for the mod.
- `currency_providers.json` - Contains the configuration settings for currency providers, which are used to determine how the currency providers work for the Move Tutor.
- `permissions.json` - Contains the configuration settings for permissions related to the Move Tutor, which can be used to control access to available commands.

## Commands

- `/move-tutor` - Opens the Move Tutor's GUI, allowing you to browse and learn new moves for your Pokemon.
  - Requires the `cobblemon_move_tutor.command.move_tutor` permission node to use.
- `/move-tutor other <player>` - Opens the Move Tutor's GUI for another player, allowing you to browse and learn new moves for their Pokemon.
  - Requires the `cobblemon_move_tutor.command.move_tutor.other` permission node to use.

## NPC Extensions

### Molang functions

Two new Molang functions have been added to the Player molang functions:

> Note: Slot is the index of the Pokemon in the player's party, starting from 0. With a maximum of 5, as the player can have up to 6 Pokemon in their party.

- `q.player.open_village_tutor(<slot>)` - Opens the Move Tutor's GUI for the player, allowing them to browse and learn new moves for their Pokemon.
- `q.player.open_admin_tutor(<slot>)` - Opens the "Admin" version of the Move Tutor's GUI for the player, instead of the village tutor.

### Dialogues

Two new dialogues have been added for the Move Tutor NPCs:

- `cobblemon_move_tutor:tutor_dialogue` - The dialogue used by the Move Tutor NPC that spawns near villages.
- `cobblemon_move_tutor:admin_tutor_dialogue` - The dialogue used by the "Admin" Move Tutor NPC, which can be summoned using commands or Molang functions.

### Added NPCs

- `cobblemon_move_tutor:village_tutor` - The Move Tutor NPC that spawns near villages, offering a variety of moves for players to learn.
  - This NPC has a normal AI that allows it to act more like a regular villager, including wandering around and interacting with players.
- `cobblemon_move_tutor:tutor` - The "Admin" Move Tutor NPC, which can be summoned using commands or Molang functions.
  - This NPC has no AI and will simply stand in place, allowing players to interact with it to access the Move Tutor's GUI.

#### NPC Variants

Each of the Move Tutor NPCs have three variants that can be configured via Cobblemon's NPC Aspect system:

- `red` - The default variant, with a red color scheme.
- `blue` - A variant with a blue color scheme.
- `green` - A variant with a green color scheme.