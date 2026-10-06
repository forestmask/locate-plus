plugins {
    id("dev.kikugie.stonecutter")
}

// The version the shared source tree is currently written against. Switching it rewrites the
// conditional comments in src/ for that version, which is why it belongs in version control.
stonecutter active "1.20.1"
