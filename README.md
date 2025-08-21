# MediaPlayer VA09 - Advanced Audio Player

A sophisticated Java-based audio media player with advanced playlist management, search functionality, and sorting capabilities.

## New Features in VA09

- **Advanced Playlist Iterator**: Controllable playlist navigation with search and sort capabilities
- **Multiple Sort Criteria**: Sort by title, author, album, or duration
- **Search Functionality**: Filter audio files by metadata content
- **Exception Handling**: Custom `NotPlayableException` for better error management
- **Comparator System**: Dedicated comparators for different sorting criteria
- **Enhanced Package Structure**: Organized code in `studiplayer.audio` package

## Supported Audio Formats

- **WAV**: Uncompressed audio files with precise duration calculation
- **MP3**: Compressed audio with ID3 tag support
- **OGG**: Open-source compressed audio format with metadata

## Project Structure

```
MediaPlayer_VA09/
├── src/
│   ├── studiplayer/audio/          # Main package
│   │   ├── AudioFile.java          # Enhanced abstract base class
│   │   ├── AudioFileFactory.java   # Factory for audio file creation
│   │   ├── PlayList.java          # Advanced playlist with search/sort
│   │   ├── ControllablePlayListIterator.java  # Custom iterator
│   │   ├── SortCriterion.java     # Enum for sort options
│   │   ├── NotPlayableException.java  # Custom exception
│   │   ├── *Comparator.java       # Sorting comparators
│   │   ├── SampledFile.java       # Base for sampled audio
│   │   ├── TaggedFile.java        # MP3/OGG with metadata
│   │   └── WavFile.java           # WAV file implementation
│   └── (Legacy classes in root)   # Backward compatibility
├── cert/                          # Certification tests
├── test/                          # Unit tests
├── audiofiles/                    # Sample audio collection
├── audiofiles2/                   # Additional audio samples
├── examples/                      # Usage examples
├── lib/                          # External libraries
└── *.m3u                         # Sample playlists
```

## Key Enhancements

### Advanced Playlist Management

```java
// Create playlist with search and sort capabilities
PlayList playlist = new PlayList("test.m3u");

// Set search filter
playlist.setSearch("beethoven");

// Sort by different criteria
playlist.setSortCriterion(SortCriterion.AUTHOR);
playlist.setSortCriterion(SortCriterion.TITLE);
playlist.setSortCriterion(SortCriterion.ALBUM);
playlist.setSortCriterion(SortCriterion.DURATION);

// Navigate with enhanced iterator
AudioFile current = playlist.currentAudioFile();
playlist.nextSong();
```

### Search and Filter

The playlist supports searching across multiple metadata fields:
- Artist/Author names
- Song titles  
- Album names (for tagged files)

```java
// Filter playlist by search term
playlist.setSearch("Mozart");  // Shows only Mozart tracks
playlist.setSearch("");        // Clear filter
```

### Sort Criteria

Available sorting options via `SortCriterion` enum:
- `DEFAULT`: Original playlist order
- `AUTHOR`: Sort by artist/author name
- `TITLE`: Sort by song title
- `ALBUM`: Sort by album name (tagged files first)
- `DURATION`: Sort by track duration

### Exception Handling

Enhanced error handling with `NotPlayableException`:

```java
try {
    AudioFile file = AudioFileFactory.createAudioFile("path/to/file.mp3");
    file.play();
} catch (NotPlayableException e) {
    System.out.println("Cannot play: " + e.getPathname());
    System.out.println("Reason: " + e.getMessage());
}
```

### Iterator Pattern

The playlist implements `Iterable<AudioFile>` with a custom iterator:

```java
// Use enhanced for-each loop
for (AudioFile file : playlist) {
    System.out.println(file.toString());
}

// Or get the iterator directly
Iterator<AudioFile> iter = playlist.iterator();
while (iter.hasNext()) {
    AudioFile file = iter.next();
    // Process file
}
```

## Comparator Classes

Dedicated comparator implementations for flexible sorting:

- `TitleComparator`: Alphabetical by title
- `AuthorComparator`: Alphabetical by author
- `AlbumComparator`: Alphabetical by album (tagged files prioritized)
- `DurationComparator`: Numerical by duration (sampled files only)

## Dependencies

Located in `lib/` directory:
- `studiplayer.jar`: Core audio functionality
- `jl1.0.jar`: MP3 decoding
- `jogg-0.0.7.jar` & `jorbis-0.0.15.jar`: OGG support
- `mp3spi1.9.5.jar`: MP3 Service Provider Interface
- `tritonus_share.jar` & `vorbisspi1.0.2.jar`: Audio format support

## Testing

Comprehensive test suite in `cert/` directory:
- `AudioFileTest.java`: Basic audio file functionality
- `PlayListTest.java`: Playlist operations
- `AudioFileFactoryTest.java`: Factory pattern tests
- Individual format tests for WAV, Tagged files

## Usage Examples

### Basic Playlist Operations

```java
// Create and populate playlist
PlayList playlist = new PlayList();
playlist.add(AudioFileFactory.createAudioFile("audiofiles/Rock 812.mp3"));

// Load from M3U file
PlayList fromFile = new PlayList("test.m3u");

// Save current playlist
playlist.saveAsM3U("my_playlist.m3u");
```

### Advanced Filtering and Sorting

```java
// Create playlist with classical music
PlayList classical = new PlayList("classical.m3u");

// Find all Beethoven pieces
classical.setSearch("Beethoven");
classical.setSortCriterion(SortCriterion.TITLE);

// Jump to specific track
AudioFile symphony = /* find specific file */;
classical.jumpToAudioFile(symphony);
```

## File Naming Conventions

Automatic metadata extraction from filenames:
- `Artist - Title.ext` → Artist: "Artist", Title: "Title"
- `Title.ext` → Title: "Title", Artist: ""

## Getting Started

1. Import project into Eclipse IDE
2. Ensure all JAR files in `lib/` are in classpath
3. Run tests in `cert/` directory to verify setup
4. Create playlists using sample audio files in `audiofiles/`

## Migration from Previous Versions

VA09 maintains backward compatibility with legacy classes in the root `src/` directory while providing enhanced functionality through the `studiplayer.audio` package.

## License

Educational project for advanced Java programming concepts including design patterns, exception handling, and collection frameworks.
