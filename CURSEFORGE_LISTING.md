# CurseForge Listing — STRATA

Copy-paste material for the CurseForge project page. **Not part of the mod build.**

---

## Project setup fields

| Field | Value |
|---|---|
| **Project Name** | `STRATA` |
| **Slug / URL** | `strata` |
| **Summary** (must be ~1 sentence, must NOT reuse description text) | Excavate buried sites to open a rift into an older, archaic version of your own world. |
| **Project Avatar** | **Must be exactly 400x400px.** No solid colours, no copyrighted imagery. **Do not use .webp** — known bug. |
| **Category** | Mods → *World Gen* (add *Adventure and RPG* as secondary) |
| **Game Version** | Minecraft **26.2** |
| **Mod Loader** | **NeoForge** |
| **License** | All Rights Reserved *(current setting in `gradle.properties`)* |
| **Source** | `https://github.com/ScripedCraftedStudios/strata` |
| **Issues** | `https://github.com/ScripedCraftedStudios/strata/issues` |

---

## Description (paste into the project page body)

### Your world remembers what it used to be.

Minecraft's terrain generator has been rewritten many times. Every world you have
ever built has a ghost — the landscape that *would* have existed, from the same
seed, under older rules.

**STRATA** makes that ghost a place you can walk into.

---

### Find the site

Deep underground, buried in stone, sit **Excavation Sites** — collapsed mossy
chambers that were sealed a long time ago. Brush the suspicious gravel inside
them and you will turn up **Seed Shards**, along with relics of Minecraft's own
discarded history:

- **Ruby** — the gem that was cut before Emerald shipped in 1.3
- **Rose** — the flower the Poppy replaced in 1.7
- **Changelog Page** — still reading *"Removed Herobrine"*

---

### Open the rift

At the centre of every site is a dormant **Rift Anchor**. Strike it with a Seed
Shard and it opens.

You arrive in **Stratum I** at *your own coordinates* — but this is your world
generated under archaic rules. A 128-block sky. Terraced hills and gravel
shorelines. Clouds sitting exactly where they used to. Warm dust on the horizon,
a washed-out sky, and ash drifting through air that has not moved in fifteen years.

Nothing here rains. Nothing here lets you sleep.

> *You cannot sleep here. This world has already ended.*

---

### What's in it

- A full custom dimension with its own terrain generator, biome palette and sky
- Archaic worldgen: Beta-style world height, no modern biome height blending, no
  aquifers, no ore veins, and the pre-1.18 random source
- Excavation Site structures generating worldwide underground
- Archaeology loot with relics drawn from real cut Minecraft content
- Rift Anchor and Seed Shard, both craftable and both findable

---

### Notes

Minecraft flags any mod-added dimension as "experimental" when you create a world.
This is a NeoForge-wide limitation ([issue #846](https://github.com/neoforged/NeoForge/issues/846)),
not a problem with the mod — click through it.

Requires **NeoForge for Minecraft 26.2**.

---

*Built for the CurseForge Minecraft ModJam 2026 — Echoes of the Past.*

---

## Upload checklist

- [ ] `./gradlew build` run, `build/libs/strata-1.0.0.jar` exists
- [ ] Jar tested once in a clean instance (not just the dev run)
- [ ] Project created on CurseForge, category **Mods**
- [ ] Game version **26.2**, loader **NeoForge**
- [ ] Description pasted, source + issue links filled in
- [ ] **Screenshots uploaded** — the Stratum I horizon shot is the strongest one
- [ ] Logo / thumbnail set, **400x400px, hand-made — AI-generated avatars and gallery images are PROHIBITED by the contest rules**
- [ ] File uploaded, release type **Release**
- [ ] Wait for CurseForge moderation approval
- [ ] **Only after it is live and approved:** submit the ModJam entry form
- [ ] GitHub repo link included on the form (mandatory for Java Mods)

**Deadline reminders**
- **Aug 4** — submit by this date to qualify for the $150 mid-contest reward
- **Sep 1** — final submission deadline
- **Sep 8–14** — community voting
