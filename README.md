# Locate Plus

Find any block, entity or item, not just structures. Scans, surveys, inspects and teleports you
there safely. Server-side only, no client install needed.

## Commands

| Command | What it does |
|---|---|
| `/lp` | In-game command list |
| `/locate block <id\|#tag> [<n> chunks\|blocks]` | Nearest matching block |
| `/locate entity <id\|#tag> [<n> chunks\|blocks]` | Nearest matching entity |
| `/locate item <id\|#tag> [<n> chunks\|blocks] [export]` | Find an item in chests, on the ground, on mobs and on players |
| `/locate biome\|structure\|poi <id\|#tag>` | Vanilla searches, with a safe-teleport button |
| `/lp inspect <x> <y> <z>` | Redstone, light, mob spawning, crops, containers, entity data |
| `/safetp [targets] <destination>` | Teleport somewhere you can actually stand |
| `/glow <target> <n> chunks\|blocks` | Outline entities through terrain |
| `/lp analyze blocks\|entities\|both [<n> chunks\|blocks] [export]` | Survey an area |
| `/lp visualize <n> chunks\|blocks [seconds]` | Outline the area a scan would cover |
| `/lp stop` | Stop any scan that is running |
| `/lp clear [all\|both\|glow\|markers]` | Clear what this mod is showing |
| `/lp config` / `/lp reload` | Show settings, or re-read the config file |

Permission level 2 by default. Add forceload to most commands to include unloaded chunks.

## Radius

- Leave it off and the command covers every loaded chunk, usually a few hundred around you.
- The unit is required and plural. 64 blocks or 4 chunks, never a bare 64.
- A chunk radius is a reach, not a total. 4 chunks is 49 chunks, 20 chunks is 1,257.
  Use 0 chunks for just the one you are standing in.
- It covers a circle, so the corners get trimmed and nothing reported is further than you asked.
- Horizontal only. The full height of the world is always included.
- Caps are 1,000 chunks and 16,000 blocks, both configurable. They exist because the area grows
  with the square of the radius.

Targets take ids, tags or selectors: minecraft:zombie, #minecraft:skeletons, @e,
@e[type=minecraft:creeper]. Modded ids work in any namespace. Searching a tag lists each matching
type with its own count and teleport button.

Nothing is force-loaded unless you ask. Without forceload a scan reads only what is already in
memory and never generates terrain. Skipped chunks get counted and reported.

Large scans warn you first, then run sliced across ticks so they never freeze the server.

## Markers

/locate block outlines its nearest hit, and you can see it through terrain, so a buried target is
visible from wherever you land.

The outline is a glass copy of the block at its real size, slightly oversized so it sits just
outside the real faces instead of flickering. It is a display entity, so no hitbox and nothing to
walk into. Anyone nearby can see it, same as /glow, because glowing is entity state.

It clears when you mine or replace the block, after a minute, or on /lp clear. Any marker left
behind by a server that stopped badly is deleted the moment the world loads again.

| Setting | Does |
|---|---|
| `mark_located_blocks` | turn outlining off entirely |
| `block_marker_seconds` | 0 for no time limit |
| `block_marker_fill` | what it is made of, must be a block that renders |
| `block_marker_colour` | hex code or a dye name |

Barrier, light and structure void draw nothing, so they are refused with a note in the log.

## Teleporting

/safetp puts you at the nearest spot you can stand and turns you to face the target. Slabs,
stairs, paths and shallow water all count. The [Teleport] buttons in chat use it too.

If there is genuinely nowhere to stand, like a spot sealed in solid rock, you get sent to the exact
coordinate anyway and told it was not checked. fallback_to_vanilla_tp turns that off.

The buttons have two switches because the results are different. Block, entity and item results
point at something real, so safe_teleport_from_results covers those. Biome, structure and poi
results point at a place rather than a block, so safe_teleport_from_biome_results covers those.
Set either to false for a plain /tp.

## Exports

Written to config/locate-plus/exports/ as a text file, on a background thread. Chat shows the top
60 types and says how many were left out, which chat_results_shown changes. Add export for the
full list with every coordinate.

Four switches decide which blocks get coordinates listed. Every type is counted either way.

| Switch | Lists | Default |
|---|---|---|
| `export_modded_blocks` | anything from another mod | on |
| `export_placed_blocks` | vanilla blocks a player could place | on |
| `export_notable_blocks` | ores, spawners, amethyst and other rare finds | off |
| `export_natural_blocks` | ordinary terrain: stone, dirt, water | off |

Any combination works. Modded blocks get sorted by the tags mods share, so a modded ore counts as
an ore and a modded stone counts as ground. One with no tags is treated as placed, which is right
for machines and pipes.

## /locate item

Looks in containers including modded ones, shulker boxes inside other containers, items on the
ground, mob equipment, item frames, player inventories and ender chests. Results are grouped by
location, biggest pile first, and each line says what is holding them.

Any of those can be switched off in the config. Worth doing for player inventories if your server
treats those as private.

## Other things

- Glow and block markers last one minute.
- Command output is private. Results go only to whoever ran the command, so coordinates are never
  announced. The exception is glowing, which is entity state and cannot be shown to one player, so
  anyone nearby sees it. glow_located_entities turns that off.

## Installing it on a server you do not own

You cannot. Every command is registered into the server's command tree and every scan reads the
server's copy of the world. Put the jar in your own mods folder, join someone else's server, and
you get nothing: your client sends the command as plain text and a server without the mod answers
"Unknown or incomplete command". Nothing is x-rayed, because the client is never doing the looking.

That is also why no client install is needed.

## Configuration

config/locate-plus/config.json, written on first start. JSON with comments allowed, and every
setting is explained in the file. Edit it and run /lp reload, or restart.

Permission level, which commands exist at all, default radii, what /locate item searches, how
much of each tick scans may use, and every limit the mod applies. /lp config prints what is
currently loaded.

A file with a syntax error is left alone and reported in the log, so a typo never costs you your
settings. Out-of-range values get clamped rather than rejected.

## Defaults

| | |
|---|---|
| `/locate` radius | 64 blocks |
| `/lp analyze` radius | 4 chunks |
| Permission level | 2 (OP, or cheats in singleplayer) |
| Scan budget | 8 ms per tick |
