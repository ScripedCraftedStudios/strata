# STRATA — Save Point
**As of: Sat 25 Jul 2026, end of session 1**
Deadline: **Sept 1, 2026 at 5:00 p.m. GMT+3** · Next milestone: **Aug 4**

---

## 1. Where things stand

**A working, playable mod exists.** Every item below was verified in-game, not assumed.

| Feature | Status |
|---|---|
| Dev environment (JDK 25.0.4, IntelliJ, NeoForge 26.2.0.32-beta, Gradle 9.2.1) | ✅ working |
| `STRATA 1.0.0` loads in Minecraft 26.2 | ✅ verified in mod list |
| **Stratum I** dimension (`strata:stratum_one`) | ✅ verified via F3 |
| Faded biome palette — dust fog, muted sky, olive grass, ash particles | ✅ verified visually |
| Archaic terrain — Beta 0–128 world, gravel shores, legacy RNG, no aquifers/ore veins | ✅ verified |
| **Rift Anchor** block + **Seed Shard** item | ✅ teleport works both ways in code |
| **Excavation Site** structure generating underground worldwide | ✅ `/locate` + block probe confirmed |
| Relics: **Ruby**, **Rose**, **Changelog Page** | ✅ render, named, RARE rarity |
| Archaeology loot table on suspicious gravel | ✅ wired, structure carries it |
| **Two-way rift** — outward costs a shard, return is free | ✅ both directions verified |
| **Advancement tree** (6 advancements, teaches the loop) | ✅ verified firing with correct frames |
| **Redrawn textures** — 4-tone shading, no stray holes | ✅ verified in-game |
| Hero screenshot — `screenshots/stratum-one-horizon.png` | ✅ 3440x1417, no HUD |
| Release jar builds and packages correctly | ✅ metadata resolved, all assets present |
| Public GitHub repo | ✅ github.com/ScripedCraftedStudios/strata |

**Last commit:** `c37195b` — pushed, working tree clean.

---

## 2. Known issues / debt

1. ~~**Rift is one-way in practice.**~~ **FIXED (session 2).** The rift now anchors both ends: travelling out plants a Rift Anchor where you land, and the return trip is free with an empty hand. Entering a stratum still costs a Seed Shard; leaving never does, so a player can't be stranded. Both directions verified in-game.

2. **Dead files to delete.** `rift_anchor_buried` (configured_feature, placed_feature, neoforge/biome_modifier) never generated anything and the structure replaced that approach entirely. `git rm` them.

3. **Terrain is archaic but not *recognisably yours*.** The original pitch was "your world in an older era." Currently Stratum I uses self-contained noise, so it shares the seed but not the landmass shape. Three attempts at reusing vanilla's `overworld/offset` + `factor` failed (plateaus or no relief). Next attempt should dump actual `offset`/`factor` values at known coordinates rather than tuning constants blind.

4. **"Experimental features" warning on world creation.** Not fixable cleanly — [NeoForge #846](https://github.com/neoforged/NeoForge/issues/846), mod-added dimensions are always flagged. Ignore it.

5. **IntelliJ Ultimate trial expires ~Aug 24**, before the deadline. Community Edition is already installed as fallback.

---

## 3. ⚠️ Official judging criteria — NOT what the blog said

From the binding [Terms & Conditions](https://mod.curseforge.com/minecraft/modjam2026/terms-and-conditions/):

| Weight | Criterion |
|---|---|
| **30%** | **Originality** — creativity, novelty, uniqueness |
| **30%** | **Fun Factor** — is it fun to play |
| **30%** | **Visuals** — visual presentation, aesthetic quality, overall appeal |
| **10%** | **Downloads** — number accumulated so far |

**Theme fit is not a scored category.** The blog's "Theme Fit / Quality & Polish / Originality & Fun" framing is marketing copy, not the rubric.

### What this means strategically

- **Visuals are 30% — a third of the score.** This is currently STRATA's weakest area. The Rift Anchor, Seed Shard and three relic textures are crude 16×16 pixel art I generated programmatically. **Replacing them with hand-drawn textures is likely the single highest-value remaining work.** The Stratum I atmosphere shot is genuinely strong; the item icons are not.
- **Downloads are 10%** — so publishing early compounds. Another reason to ship before Aug 4.
- **Originality is 30%** and STRATA's concept is its strongest asset. Lead with it in the description.

### Hard rules worth remembering

- **"AI-generated project avatars or gallery images are prohibited."** Stricter than CurseForge's general policy (which only requires disclosure). Your screenshots are real captures — fine. Do NOT use an AI-generated logo or gallery art. *(No AI disclosure is required for code.)*
- Project must be **new** — not published on CurseForge before Jul 21. ✅
- Must target **MC 26.1+** — you're on 26.2. ✅
- **GitHub link mandatory** for Java Mods. ✅
- **Must be 18+** to participate and claim prizes.
- Project must be **live and approved** on CurseForge before the deadline.
- One Grand Prize per author max, regardless of how many projects.

---

## 4. Next steps, in priority order

1. **`./gradlew build`** → produces `build/libs/strata-1.0.0.jar`
2. **Test that jar in a clean Minecraft instance** (not the dev run — dev runs hide packaging and missing-asset bugs). This is the most common way jam entries ship broken.
3. **Redraw the item textures by hand.** Highest score impact for the effort. 5 textures: rift_anchor, seed_shard, ruby, rose, changelog_page.
4. **Take good screenshots.** The Stratum I horizon (dust fog + ash + terraced hills) is the money shot. Also want: an Excavation Site interior, and the relics in an inventory.
5. **Make a 400×400 project avatar** (hard requirement; no solid colours, no .webp).
6. **Create the CurseForge project** and upload. Copy in `CURSEFORGE_LISTING.md`.
7. **Wait for moderation** (a few days).
8. **Only once live and approved** → fill the [submission Typeform](https://overwolfdevs.typeform.com/to/bbZkt4bN). Do not submit it before the project exists.

Everything after that — Stratum II, the Archive Terminal, the Giant, the Illusioner — is upside on a banked submission. See the design doc for that roadmap.

---

## 5. Key file map

```
src/main/java/com/tate/strata/
  STRATA.java                     registrations (items, block, creative tab)
  RiftAnchorBlock.java            the rift: useItemOn -> cross-dimension teleport
src/main/resources/
  data/strata/dimension/stratum_one.json          level stem
  data/strata/dimension_type/stratum_one.json     0-128 world, fog/sky palette
  data/strata/worldgen/noise_settings/beta_layer.json   archaic terrain
  data/strata/worldgen/biome/echo_plains.json     faded palette
  data/strata/worldgen/structure/excavation_site.json
  data/strata/worldgen/template_pool/excavation_site.json
  data/strata/worldgen/structure_set/excavation_sites.json
  data/strata/structure/excavation_site.nbt       the built chamber
  data/strata/loot_table/archaeology/excavation_site.json   relics
  assets/strata/...                               textures, models, lang
src/main/templates/META-INF/neoforge.mods.toml    mod metadata + description
```

### 26.2 API gotchas discovered the hard way

- **`ResourceLocation` is renamed `Identifier`** (`net.minecraft.resources.Identifier`), and `ResourceKey.location()` is now `.identifier()`. Breaks nearly every tutorial online.
- **`dimension_type` and `biome` use a namespaced `attributes` map** (`minecraft:visual/fog_color`, `minecraft:gameplay/bed_rule`, `timelines`) — not the old flat fields.
- **Biome `attributes` override `dimension_type`** for sky colour. Palette must live in a custom biome.
- **Item models moved** to `assets/<ns>/items/<name>.json` wrapping the old model.
- **`registerBlock` takes a `UnaryOperator<Properties>`**, not a Properties object.
- **`getHeightmapPos` on an ungenerated chunk returns the world bottom** — force `getChunk(x,z)` first or you drop players out of the world.
- Structure NBT path is `data/<ns>/structure/` (singular).

---

## 6. Verification commands

```
/locate structure strata:excavation_site
/execute in strata:stratum_one run tp @s <x> 100 <z>
/give @s strata:seed_shard 4
/give @s strata:rift_anchor
```
