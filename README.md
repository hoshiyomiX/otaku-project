# OTAku Project

Android OTA payload toolkit — repack partition images (.img) into flashable ZIPs.

## Repository Structure

This is the **parent project container** that holds the [Stellar Frameworks](https://github.com/hoshiyomiX/stellar-frameworks) skill system and project management files.

The actual application source code lives in the submodule:

| Repository | Purpose |
|-----------|---------|
| [`hoshiyomiX/payload-toolkit-android`](https://github.com/hoshiyomiX/payload-toolkit-android) | Android app + Python payload_toolkit (submodule) |
| [`hoshiyomiX/otaku-project`](https://github.com/hoshiyomiX/otaku-project) | This repo — parent container |

## Quick Start

```bash
# Clone with submodule
git clone --recurse-submodules https://github.com/hoshiyomiX/otaku-project.git
cd otaku-project
cd payload-toolkit-android  # main development happens here
```

## Features

- DD-mode flashable ZIP generation for TWRP/OrangeFox recovery
- Compression: none, gzip, bzip2, xz, brotli (with configurable levels)
- Per-partition progress tracking with notification updates
- 3-mode theme: System (auto-follow), Light, Dark
- Foreground service for unkillable repack under memory pressure
- SHA-256 hash verification in flash script
- Auto device detection from `ro.product.name`
