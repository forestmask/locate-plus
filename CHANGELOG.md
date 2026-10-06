# Changelog

## 2.2.0

Now under the LGPL version 3 instead of all rights reserved. Earlier releases stay as they were.

### Removed

- /purgeentities is gone. It deleted entities, and nothing else in this mod changes the world, so
  it never fit. Vanilla /kill does the same job. If you have it in a command block or a datapack,
  that will stop working.

### Better

- Every result prints its coordinate next to the teleport button, so it can be read or copied
  without hovering over anything. On a grouped result it is the nearest one, which is the same
  place the button sends you.
- Distance is gone from results. With the coordinate on the line there was no reason to say 12m
  as well. /safetp still tells you how far it had to put you from the spot you asked for.
- The line offering an export is a button now. Click it and the command lands in your chat box
  instead of being typed out by hand.
- /lp analyze both printed the area, the export advice and the timing twice, once for each half,
  and the advice named only one of them. It reads as one report now: the area once above both
  halves, and one line of advice and one time below them.
- Export files are shorter. The header no longer repeats itself, the long rules across the page
  are gone, and the nearest coordinate sits beside its count instead of on a line of its own.
- Export headers dropped the Area line, and now say how many chunks were read out of how many
  were asked for, so a scan that missed unloaded chunks shows the shortfall.

### Fixed

- A block radius printed two chunk counts that disagreed: "Analyzing blocks in 64 blocks
  (4 chunks) (81 chunks)". The first was the reach, the second the area. It now prints only the
  area: "Analyzing blocks in 64 blocks (81 chunks)".
- Counts of one read as plurals in twelve places, among them "1 block types found", "glowing for
  1 seconds", "Fuel remaining: 1 ticks" and "1 positions are left out". All read as singular now.

## 2.1.0

Adds 1.21 and 1.21.1. Still works on 1.20.1 through 1.20.4.

### Fixed

- /lp clear only switched off glowing in the dimension you ran it in, and dropped its record of
  the rest, so anything lit in another world stayed lit with nothing able to turn it off. It now
  clears every dimension.
- Glowing on a boat, minecart, item frame or dropped item is a flag the game saves with the entity.
  The timer that switched it off only ran while the entity was loaded, so one that unloaded, or
  that was still lit when the server stopped, came back glowing for good. The flag now comes off
  when the entity unloads and when the server stops.
- /lp inspect could take the whole server down if a modded block misbehaved while being read. It
  now says it could not read that position and carries on.
- The mod loaded on 1.20.5 and 1.20.6, where it cannot work, then failed on use. It refuses to
  load there instead, with a message naming the versions it needs.
- Chest minecarts and other entity inventories counted as mobs, so a result read "on
  chest_minecart" and the split line put them under mobs. They count as containers now.
- Names printed with underscores in them. Containers, mobs, nested items, enchantments and biome
  type all read as words now, so "fire protection 4" rather than "fire_protection 4".
- The inspect and analyzechunks switches did nothing. Both commands were always registered, and
  /lp config would show them off while they still worked.
- /lp visualize had no switch at all. It has one now. /lp clear is always registered, so an
  outline can always be removed even with everything else switched off.
- A scan_min_y set above the ceiling flipped the height band, and every scan then read nothing
  without saying why.

## 2.0.0

Works on 1.20.1 through 1.20.4.

### Heads up

- /analyzechunks is now /lp analyze, /inspect is now /lp inspect
- Radius needs a unit: 64 blocks or 4 chunks, not just 64
- 4 chunks now means 4 in every direction (49 chunks), not the 4 nearest
- No radius at all scans everything loaded around you

### New

- /locate item finds items in chests, shulker boxes, on the floor, on mobs, in player inventories
- /lp visualize shows the scan area before you scan it
- /lp stop cancels a running scan
- /lp clear removes glowing and markers
- Config file with 43 settings, /lp reload to apply
- Export results to a text file
- Set a height range so scans skip depths you do not care about
- Every command can be switched off completely, including the ones under /lp
- Teleport buttons have their own switches, separate for block results and biome results
- Turn off the outline on /locate block, or change how many results chat lists

### Better

- Found blocks glow through walls
- Only you see your results
- Shorter chat, 60 results instead of 15
- Big scans no longer freeze the server
- /safetp lands you on chests, in composters, at the foot of pillars, and avoids lava, magma,
  powder snow, campfires and cactus
- Faster scans, and /safetp is much quicker when it has to search

### Fixed

Tons, mostly /safetp being dumb.

## 1.0.0

First release. /locate block, /locate entity, /inspect, /safetp, /glow, /analyzechunks and
/purgeentities.
