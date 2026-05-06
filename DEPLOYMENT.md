# Software Deployment Documentation
## Game Cờ Vua 2.0 (Chess Game 2.0)

**Document ID:** GCV-DEPLOY-001  
**Version:** 1.0  
**Date:** 2026-05-06  
**Status:** Final  
**Classification:** Public  

---

## 1. INTRODUCTION

### 1.1 Purpose
This document provides comprehensive deployment and installation instructions for **Game Cờ Vua 2.0** (Chess Game 2.0), a Java-based chess application. It is intended for end-users, system administrators, and IT support personnel responsible for installing, configuring, and maintaining the software.

### 1.2 Scope
This document covers:
- System requirements and prerequisites
- Installation procedures
- Configuration and setup
- Operational guidelines
- Troubleshooting and support
- Maintenance and uninstallation

**Out of scope:**
- Development and source code compilation
- Architecture design details
- Performance optimization techniques

### 1.3 Document Organization
This document is organized as follows:
1. Introduction (this section)
2. Software Overview
3. System Requirements
4. Pre-Installation Tasks
5. Installation Procedures
6. Configuration and Setup
7. Operational Instructions
8. Troubleshooting Guide
9. Uninstallation Procedures
10. Technical Specifications
11. Support and Maintenance
12. Appendices

### 1.4 Intended Audience
- **Primary:** End-users with basic computer literacy
- **Secondary:** System administrators and IT support staff
- **Tertiary:** Developers and technical contributors

### 1.5 Conventions and Terminology
| Term | Definition |
|------|-----------|
| Application | Game Cờ Vua 2.0 software product |
| Installation | Process of setting up the application on a system |
| Deployment | Distribution and installation of the application |
| Runtime | Software required to execute the application |
| Executable | Binary file (.exe) that runs the application |
| User | Individual operating the application |

---

## 2. SOFTWARE OVERVIEW

### 2.1 Product Description
**Game Cờ Vua 2.0** is a cross-platform chess application developed in Java that provides an interactive interface for playing chess. The application supports multiple game modes and utilizes artificial intelligence for computer-based gameplay.

### 2.2 Product Version
- **Product Name:** Game Cờ Vua 2.0
- **Version Number:** 2.0.0
- **Release Date:** 2026-05-06
- **Build Number:** GCV-2.0-BUILD-001

### 2.3 Key Features
| Feature | Description | Status |
|---------|-----------|--------|
| Human vs Human Mode | Two-player local gameplay | ✓ Available |
| Human vs AI Mode | Single-player against AI opponent | ✓ Available |
| Minimax Algorithm | Advanced AI decision-making | ✓ Available |
| Graphical User Interface | Interactive chess board display | ✓ Available |
| Move History | Game move tracking and undo functionality | ✓ Available |
| Online Multiplayer | Network-based multiplayer gameplay | 🔄 In Development |

### 2.4 System Platform Support
- **Primary Platform:** Microsoft Windows 7 and later
- **Supported Versions:** Windows 7, Windows 8, Windows 10, Windows 11
- **Architecture:** 32-bit and 64-bit compatible
- **Secondary Platforms:** macOS, Linux (via Java Runtime)

---

## 3. SYSTEM REQUIREMENTS

### 3.1 Minimum System Requirements
The following table lists the minimum hardware and software requirements:

| Component | Minimum Specification |
|-----------|----------------------|
| **Operating System** | Windows 7 SP1 or later |
| **Processor** | Intel Pentium IV 2.0 GHz or equivalent |
| **Memory (RAM)** | 512 MB |
| **Hard Disk Space** | 300 MB free space |
| **Display Resolution** | 1024 × 768 pixels minimum |
| **Color Depth** | 256 colors (16-bit or higher recommended) |
| **Network Connection** | Optional (for future online features) |

### 3.2 Recommended System Requirements
For optimal performance and user experience:

| Component | Recommended Specification |
|-----------|--------------------------|
| **Operating System** | Windows 10 or Windows 11 (64-bit) |
| **Processor** | Intel Core i5 2.5 GHz or equivalent or higher |
| **Memory (RAM)** | 1-2 GB |
| **Hard Disk Space** | 500 MB SSD (Solid State Drive) |
| **Display Resolution** | 1920 × 1080 pixels or higher |
| **Color Depth** | 32-bit color |
| **Network Connection** | Broadband connection (for future online features) |

### 3.3 Software Prerequisites
The following software may be required depending on installation method:

#### 3.3.1 Java Runtime Environment (JRE)
- **Version:** Java 21 or later
- **For Installation Method 1 (App-Image):** Not required (bundled)
- **For Installation Method 2 (JAR):** Required - must be separately installed
- **Source:** https://www.oracle.com/java/technologies/downloads/

#### 3.3.2 Additional Components
| Component | Version | Required | Purpose |
|-----------|---------|----------|---------|
| Windows Registry | System default | No | System database |
| DirectX | 9.0 or later | No | Graphics support |
| .NET Framework | 4.0 or later | No | System compatibility |

### 3.4 Storage Requirements Analysis

| Installation Type | Disk Space Required | With JRE Included |
|------------------|-------------------|-------------------|
| App-Image Distribution | ~300-350 MB | Yes (bundled) |
| JAR File Only | ~50-100 MB | No (JRE separate) |
| Full Installation | ~400-500 MB | Yes (bundled) |

---

## 4. PRE-INSTALLATION TASKS

### 4.1 System Verification Checklist
Before proceeding with installation, verify the following:

- [ ] Windows operating system version is 7 or later
- [ ] Sufficient free disk space available (minimum 300 MB)
- [ ] Display resolution is 1024×768 or higher
- [ ] System has at least 512 MB RAM available
- [ ] User has administrator privileges (for App-Image installation)
- [ ] Antivirus software is not blocking downloads
- [ ] Internet connection is stable (for downloading)

### 4.2 Pre-Installation System Backup
It is recommended to:
1. Back up important user data
2. Create a system restore point (Windows 7/8/10/11)
3. Document current system configuration

**Steps to Create System Restore Point (Windows 10/11):**
```
Settings → System → About → System Protection → 
Create... → Enter description → Create
```

### 4.3 Obtaining the Software
The application is available from:
- **Primary Source:** https://github.com/letuanphat21/GameCoVua2.0/releases
- **Available Formats:**
  - GameCoVua-2.0-App-Image.zip (Recommended)
  - GameCoVua-2.0.jar (Alternative)

### 4.4 File Integrity Verification
After downloading, verify file integrity:

#### MD5 Checksum Verification
```
Windows: certutil -hashfile GameCoVua-2.0-App-Image.zip MD5
Expected: [checksum value to be provided]
```

#### File Size Verification
| File | Expected Size | Tolerance |
|------|---------------|-----------|
| GameCoVua-2.0-App-Image.zip | ~320 MB | ±10 MB |
| GameCoVua-2.0.jar | ~80 MB | ±5 MB |

---

## 5. INSTALLATION PROCEDURES

### 5.1 Installation Method 1: App-Image Distribution (Recommended)

#### 5.1.1 Overview
The App-Image method provides a standalone, self-contained installation that requires no additional software dependencies.

**Advantages:**
- ✓ No Java installation required
- ✓ Complete and portable
- ✓ Suitable for all users
- ✓ Easy uninstallation

**Estimated Installation Time:** 2-3 minutes

#### 5.1.2 Step-by-Step Installation

**Step 1: Download the Application Package**
1. Visit https://github.com/letuanphat21/GameCoVua2.0/releases
2. Locate version 2.0.0 or latest
3. Download file: `GameCoVua-2.0-App-Image.zip`
4. Verify download completion (file size ~320 MB)

**Step 2: Extract the Archive**
1. Navigate to download location (typically `C:\Users\[Username]\Downloads\`)
2. Right-click on `GameCoVua-2.0-App-Image.zip`
3. Select "Extract All..."
4. Choose extraction location:
   - **Option A (Recommended):** `C:\Program Files\GameCoVua\`
   - **Option B (User Directory):** `C:\Users\[Username]\AppData\Local\GameCoVua\`
5. Wait for extraction to complete (~1-2 minutes)

**Expected Directory Structure After Extraction:**
```
C:\Program Files\GameCoVua\
├── bin\
│   ├── GameCoVua.exe          [Main executable]
│   ├── GameCoVua.dll          [Runtime libraries]
│   └── runtime\               [Java Runtime bundled]
├── lib\                        [Application libraries]
├── conf\                       [Configuration files]
└── data\                       [Data files]
```

**Step 3: Verify Installation**
1. Open File Explorer
2. Navigate to installation directory
3. Verify presence of `bin\GameCoVua.exe`
4. Check folder size (~300-350 MB)

**Step 4: Create Desktop Shortcut (Optional)**
1. Open installation directory: `bin` folder
2. Right-click on `GameCoVua.exe`
3. Select "Send to" → "Desktop (create shortcut)"
4. Shortcut appears on Desktop
5. (Optional) Right-click shortcut → Properties → Change Icon

#### 5.1.3 Verification Checklist
- [ ] Directory created at specified location
- [ ] GameCoVua.exe file present and executable
- [ ] File permissions appropriate (Read & Execute)
- [ ] No error messages during extraction
- [ ] Total installation size ~300-350 MB

### 5.2 Installation Method 2: JAR File Distribution

#### 5.2.1 Overview
The JAR method requires Java 21 to be pre-installed on the system.

**Prerequisites:**
- Java 21 JRE or JDK installed and configured
- Java PATH environment variable configured
- Minimum 80 MB free disk space

**Advantages:**
- ✓ Smaller download size (~80 MB)
- ✓ Flexible deployment
- ✓ Cross-platform compatible

**Estimated Installation Time:** 5-10 minutes (includes Java installation if needed)

#### 5.2.2 Java Runtime Installation

**Step 1: Check Existing Java Installation**
1. Open Command Prompt (Win + R, type `cmd`)
2. Execute: `java -version`
3. If Java 21+ is installed, proceed to Step 5.2.3
4. If Java not found or older version, continue to Step 2

**Step 2: Download Java 21 JRE**
1. Visit https://www.oracle.com/java/technologies/downloads/#java21
2. Select "Windows" platform
3. Choose "x64 Installer" (for 64-bit systems)
4. Accept license agreement
5. Download file (~100 MB, estimated 2-5 minutes on 10 Mbps connection)

**Step 3: Install Java 21**
1. Run downloaded installer: `jre-21_windows-x64_bin.exe`
2. Click "Install"
3. Accept license terms
4. Choose installation path (default: `C:\Program Files\Java\jre-21\`)
5. Complete installation (1-2 minutes)
6. **Restart computer**

**Step 4: Verify Java Installation**
1. Open Command Prompt
2. Execute: `java -version`
3. Expected output:
```
java version "21.x.x"
Java(TM) SE Runtime Environment (build 21.x.x)
Java HotSpot(TM) 64-Bit Server VM (build 21.x.x)
```

#### 5.2.3 JAR File Installation

**Step 1: Download JAR File**
1. Visit https://github.com/letuanphat21/GameCoVua2.0/releases
2. Download: `GameCoVua-2.0.jar` (~80 MB)
3. Save to preferred location (e.g., `C:\Games\GameCoVua\`)

**Step 2: Create Installation Directory**
1. Open File Explorer
2. Create folder: `C:\Games\GameCoVua\`
3. Move downloaded `GameCoVua-2.0.jar` to this folder
4. Rename to: `GameCoVua.jar` (optional, for convenience)

**Step 3: Verify Installation**
1. Open Command Prompt
2. Execute:
```
cd C:\Games\GameCoVua
dir GameCoVua.jar
```
3. File should be listed with size ~80 MB

#### 5.2.4 Verification Checklist
- [ ] Java 21+ successfully installed
- [ ] Java PATH correctly configured
- [ ] GameCoVua.jar file present in installation directory
- [ ] File permissions allow execution
- [ ] No error messages during installation

---

## 6. CONFIGURATION AND SETUP

### 6.1 Initial Configuration

#### 6.1.1 Application First Run
**For App-Image Installation:**
1. Double-click `GameCoVua.exe` from installation directory
2. Wait for application to load (5-10 seconds on first run)
3. Application window opens with main menu

**For JAR Installation:**
1. Method A - Direct execution:
   - Double-click `GameCoVua.jar`
   - Application launches automatically

2. Method B - Command-line execution:
   - Open Command Prompt
   - Execute: `java -jar GameCoVua.jar`
   - Application launches

#### 6.1.2 Application Window Initialization
Upon first launch, the application:
1. Creates local configuration directory
2. Initializes game settings to defaults
3. Displays main menu interface
4. Allocates system resources

**Typical startup sequence (first run):**
```
Initializing Game Engine...           [2 seconds]
Loading Graphics Resources...          [3 seconds]
Configuring Game Board...             [1 second]
Application Ready                      [Signal]
```

### 6.2 User Configuration

#### 6.2.1 Game Preferences
Users can configure:
- Difficulty level (AI mode)
- Display preferences
- Sound settings
- Game speed

#### 6.2.2 Configuration File Location
**For App-Image:**
```
C:\Users\[Username]\AppData\Local\GameCoVua\config\settings.ini
```

**For JAR:**
```
C:\Users\[Username]\.gamecovua\settings.ini
```

#### 6.2.3 Configuration Parameters
| Parameter | Values | Default |
|-----------|--------|---------|
| difficulty | Easy, Normal, Hard | Normal |
| language | English, Vietnamese | Vietnamese |
| sound_enabled | true, false | true |
| board_style | Classic, Modern | Classic |
| animation_speed | 1-10 | 5 |

### 6.3 System Integration

#### 6.3.1 File Association (Optional)
To associate chess files with the application:
1. Right-click any `.chess` or `.pgn` file
2. Select "Open with" → "Choose another app"
3. Select GameCoVua application
4. Check "Always use this app"

#### 6.3.2 Environment Variables
The application automatically sets:
```
GAMECOVUA_HOME = [Installation Directory]
GAMECOVUA_DATA = [User Data Directory]
```

---

## 7. OPERATIONAL INSTRUCTIONS

### 7.1 Application Startup

#### 7.1.1 Normal Startup Procedure
1. Double-click `GameCoVua.exe` (App-Image) or `GameCoVua.jar` (JAR)
2. Wait 5-10 seconds for initialization
3. Main menu appears
4. Select desired game mode

#### 7.1.2 Command-Line Startup Options
```bash
# Standard startup
java -jar GameCoVua.jar

# With increased memory allocation
java -Xmx1G -jar GameCoVua.jar

# With verbose output
java -verbose:gc -jar GameCoVua.jar
```

### 7.2 Main User Interface

#### 7.2.1 Home Screen
```
╔════════════════════════════════╗
║    GAME CỜ VUA 2.0             ║
╠════════════════════════════════╣
║                                ║
║  [1] Người vs Người            ║
║      Two-player local game     ║
║                                ║
║  [2] Người vs AI               ║
║      Single-player vs computer ║
║                                ║
║  [3] Cài đặt                   ║
║      Settings & preferences    ║
║                                ║
║  [4] Hướng dẫn                 ║
║      Help & instructions       ║
║                                ║
╚════════════════════════════════╝
```

#### 7.2.2 Game Modes

**Mode 1: Human vs Human**
- Two players on same computer
- Turn-based gameplay
- Alternating moves
- Undo functionality available

**Mode 2: Human vs AI**
- Single player vs computer opponent
- Difficulty levels: Easy, Normal, Hard
- AI uses Minimax algorithm
- Real-time move calculation

**Mode 3: Settings**
- Display preferences
- Sound configuration
- Difficulty adjustment
- Game speed control

### 7.3 Basic Operations

#### 7.3.1 Making a Move
1. Click on piece to select (piece highlights)
2. Valid destination squares highlight in green
3. Click destination to move
4. Move executes
5. Turn passes to opponent

#### 7.3.2 Game Controls
| Control | Action |
|---------|--------|
| Left-click | Select piece / Make move |
| Right-click | Deselect piece |
| Spacebar | Hint (if available) |
| Ctrl+Z | Undo move |
| Escape | Return to menu |
| F1 | Help/Instructions |

#### 7.3.3 Game Status Indicators
| Indicator | Meaning |
|-----------|---------|
| Check (⚠️) | King under attack |
| Checkmate (X) | Game over - loss |
| Stalemate (=) | Game over - draw |
| Your Turn (→) | Awaiting your move |

### 7.4 Performance Optimization

#### 7.4.1 Resource Management
- Recommended available RAM: 1 GB minimum
- Close unnecessary applications
- Disable visual effects for better performance
- Use SSD storage for faster loading

#### 7.4.2 Graphics Optimization
For systems with limited graphics capability:
1. Settings → Display
2. Disable animations
3. Reduce board size
4. Use classic board style

---

## 8. TROUBLESHOOTING GUIDE

### 8.1 Problem: Application Will Not Start

#### 8.1.1 Symptom Description
- Executable clicked but nothing happens
- No error message displayed
- Application does not appear in task manager

#### 8.1.2 Diagnostic Checklist
- [ ] Sufficient free disk space (minimum 300 MB)
- [ ] Installation directory accessible and readable
- [ ] User has execute permissions
- [ ] System resources available (RAM, CPU)
- [ ] Antivirus not blocking execution

#### 8.1.3 Resolution Steps
**Step 1: Check File Permissions**
1. Right-click `GameCoVua.exe`
2. Select "Properties"
3. Check "Security" tab
4. Ensure "Read & Execute" permission is enabled

**Step 2: Run in Compatibility Mode**
1. Right-click `GameCoVua.exe`
2. Select "Properties"
3. Go to "Compatibility" tab
4. Check "Run this program in compatibility mode for:"
5. Select "Windows 10" or "Windows 7"
6. Click "Apply" → "OK"

**Step 3: Run as Administrator**
1. Right-click `GameCoVua.exe`
2. Select "Run as administrator"
3. Accept User Account Control prompt

**Step 4: Verify Installation**
1. Open Command Prompt (Admin)
2. Navigate to installation directory
3. Execute: `GameCoVua.exe -verbose`
4. Observe error messages

**Step 5: Reinstall Application**
If above steps fail:
1. Uninstall application (see Section 9)
2. Restart computer
3. Re-download installation files
4. Verify file integrity (checksums)
5. Reinstall following Section 5 procedures

### 8.2 Problem: Java Runtime Error

#### 8.2.1 Error Message Examples
```
"Java not found"
"Exception in thread main"
"Unable to locate Java Runtime"
"UnsupportedClassVersionError"
```

#### 8.2.2 Causes and Solutions
| Error | Cause | Solution |
|-------|-------|----------|
| Java not found | JRE not installed | Install Java 21 (see 5.2.2) |
| Wrong version | Java < 21 installed | Upgrade to Java 21+ |
| PATH not set | Environment variables incorrect | Restart computer after Java install |
| Permission denied | File permissions | Run as administrator |

#### 8.2.3 Resolution Steps
**Step 1: Verify Java Installation**
```
java -version
```

**Step 2: If Java Not Found**
- Download Java 21 from https://www.oracle.com/java/technologies/downloads/
- Install following Section 5.2.2
- Restart computer

**Step 3: If Wrong Version**
```
# Check installed Java version
java -version

# If version < 21, uninstall and install Java 21
```

**Step 4: Verify JAVA_HOME Variable**
```
# Command Prompt
echo %JAVA_HOME%

# Should output: C:\Program Files\Java\jre-21
```

### 8.3 Problem: Poor Performance / Slow Execution

#### 8.3.1 Symptoms
- Application responds slowly to user input
- Moves take several seconds
- Frequent freezing during AI calculations
- High CPU usage (>80%)

#### 8.3.2 Diagnostic Steps
1. Check available RAM: Ctrl+Shift+Esc (Task Manager)
2. Monitor CPU usage
3. Check disk activity
4. Verify graphics driver version

#### 8.3.3 Resolution Procedures
**Step 1: Close Background Applications**
1. Open Task Manager (Ctrl+Shift+Esc)
2. Identify resource-heavy applications
3. End unnecessary tasks
4. Retry application

**Step 2: Increase Available Memory**
```
# Launch with increased heap size
java -Xmx2G -jar GameCoVua.jar
```

**Step 3: Reduce Graphics Quality**
1. Settings → Display
2. Disable animations
3. Select "Classic" board style
4. Reduce animation speed

**Step 4: Update Graphics Drivers**
1. Identify graphics card: Device Manager
2. Visit manufacturer website (NVIDIA, AMD, Intel)
3. Download latest driver
4. Install and restart

**Step 5: Move Installation to SSD**
If installed on HDD:
1. Uninstall application
2. Reinstall on SSD drive
3. SSD provides faster loading

### 8.4 Problem: Graphics/Display Issues

#### 8.4.1 Symptoms
- Board squares misaligned
- Pieces not rendering correctly
- Colors inverted or washed out
- Window not resizable
- Menu text garbled

#### 8.4.2 Common Causes
| Issue | Cause |
|-------|-------|
| Resolution incompatible | Screen < 1024×768 |
| Color depth insufficient | 256-color mode |
| Driver outdated | Graphics driver old |
| DPI scaling conflict | Windows DPI settings |

#### 8.4.3 Resolution Steps
**Step 1: Check Display Resolution**
1. Settings → System → Display
2. Ensure resolution ≥ 1024×768
3. Set to 1920×1080 if available
4. Apply and restart application

**Step 2: Verify Color Depth**
1. Right-click Desktop
2. Select "Display settings"
3. Advanced display settings
4. Ensure 32-bit color is selected
5. Apply and restart

**Step 3: Disable DPI Scaling**
1. Right-click `GameCoVua.exe`
2. Properties → Compatibility
3. Check "Disable full-screen optimizations"
4. Change High DPI settings → override
5. Apply and restart

**Step 4: Update Graphics Driver**
- See Section 8.3.3 Step 4

### 8.5 Problem: Application Crashes

#### 8.5.1 Symptoms
- Application unexpectedly closes
- Windows crash dialog appears
- No error message
- Application stops responding

#### 8.5.2 Diagnostic Information Collection
When crash occurs:
1. Note exact time
2. Record what action caused crash
3. Note any error messages
4. Check Windows Event Viewer:
   - Ctrl+Shift+Esc → Event Viewer
   - Windows Logs → System/Application

#### 8.5.3 Resolution Steps
**Step 1: Clear Application Cache**
```
C:\Users\[Username]\AppData\Local\GameCoVua\
Delete: cache, temp, log folders
```

**Step 2: Verify File Integrity**
1. Uninstall application (see Section 9)
2. Delete installation directory
3. Delete user data directory (backup first if needed)
4. Reinstall clean

**Step 3: Check for Updates**
1. Visit https://github.com/letuanphat21/GameCoVua2.0
2. Check for newer version
3. Download and install latest version

**Step 4: Report Issue**
If problem persists:
1. Collect crash logs:
   - `C:\Users\[Username]\AppData\Local\GameCoVua\logs\`
2. Note system configuration
3. Submit issue to GitHub:
   - https://github.com/letuanphat21/GameCoVua2.0/issues

### 8.6 Problem: Network Connectivity Issues

#### 8.6.1 Applicable When
- Using online multiplayer features (future version)
- Attempting to download updates
- Checking for updates fails

#### 8.6.2 Resolution Steps
**Step 1: Verify Internet Connection**
```
ping google.com
```

**Step 2: Check Firewall Settings**
1. Settings → Privacy & Security → Windows Defender Firewall
2. Allow app through firewall → GameCoVua
3. Check both "Private" and "Public" networks
4. Restart application

**Step 3: Configure Proxy (if applicable)**
1. Settings → Network & Internet → Proxy
2. Set proxy server if required by organization
3. Restart application

---

## 9. UNINSTALLATION PROCEDURES

### 9.1 Uninstall Method 1: App-Image Installation

#### 9.1.1 Simple Deletion
1. Open File Explorer
2. Navigate to installation directory (e.g., `C:\Program Files\GameCoVua\`)
3. Select entire GameCoVua folder
4. Press Delete
5. Confirm deletion

#### 9.1.2 Remove from Start Menu
1. Right-click Start button
2. Select "Settings"
3. Apps → Installed apps
4. Search for "GameCoVua"
5. Click "Uninstall" (if listed)

#### 9.1.3 Remove Desktop Shortcut
1. Right-click shortcut on Desktop
2. Select "Delete"
3. Confirm deletion

### 9.2 Uninstall Method 2: JAR Installation

#### 9.2.1 Delete JAR File
1. Open File Explorer
2. Navigate to installation directory (e.g., `C:\Games\GameCoVua\`)
3. Delete `GameCoVua.jar` file
4. Delete installation directory if empty

#### 9.2.2 Optional: Remove Java Runtime
Java remains on system if other applications depend on it.

**To safely remove Java 21 (if unused by other applications):**
1. Control Panel → Programs → Uninstall a program
2. Find "Java 21 Runtime Environment"
3. Click "Uninstall"
4. Follow Java uninstaller prompts

### 9.3 Delete User Data (Optional)

#### 9.3.1 Remove Saved Games and Settings
**For App-Image:**
```
Delete: C:\Users\[Username]\AppData\Local\GameCoVua\
```

**For JAR:**
```
Delete: C:\Users\[Username]\.gamecovua\
```

#### 9.3.2 Verification After Deletion
- Confirm directories removed
- Check disk space freed (~300-350 MB for App-Image)
- Verify no orphaned shortcuts remain

---

## 10. TECHNICAL SPECIFICATIONS

### 10.1 Application Architecture

#### 10.1.1 Component Overview
```
Game Cờ Vua 2.0
│
├── Presentation Layer (GUI)
│   ├── MainFrame.java          - Main application window
│   ├── ChessGameGUI.java       - Chess board display
│   ├── Home.java               - Main menu
│   └── ChessSquareComponent.java - Board squares
│
├── Game Logic Layer
│   ├── ChessGame.java          - Game rules & state management
│   ├── Board.java              - Board representation (8×8)
│   ├── Position.java           - Coordinate system
│   └── PieceColor.java         - Piece color enumeration
│
├── Piece Model Layer
│   ├── Piece.java              - Base piece class
│   ├── Pawn.java               - Pawn logic
│   ├── Rook.java               - Rook logic
│   ├── Knight.java             - Knight logic
│   ├── Bishop.java             - Bishop logic
│   ├── Queen.java              - Queen logic
│   └── King.java               - King logic
│
└── AI Engine Layer
    ├── Minimax.java            - Minimax algorithm implementation
    └── Node.java               - Game tree node representation
```

#### 10.1.2 Design Patterns Used
- Model-View-Controller (MVC)
- Strategy Pattern (AI algorithms)
- Observer Pattern (event handling)

### 10.2 Technical Stack

| Component | Technology | Version |
|-----------|-----------|---------|
| Programming Language | Java | 21 LTS |
| GUI Framework | Java Swing | Built-in |
| Build System | Eclipse IDE | 2024.06+ |
| Target Runtime | Java Runtime Environment | 21+ |
| OS API | Windows API | Via JNI (optional) |

### 10.3 File Structure on Disk

#### 10.3.1 App-Image Installation Structure
```
GameCoVua/
├── bin/
│   ├── GameCoVua.exe           [Main executable]
│   ├── GameCoVua.cfg           [Configuration]
│   └── runtime/                [Bundled JRE]
├── lib/
│   ├── app/                    [Application JAR files]
│   ├── mod/                    [Java modules]
│   └── server/                 [Server runtime]
├── conf/
│   ├── settings.ini            [Default settings]
│   └── logging.properties      [Log configuration]
├── data/
│   └── resources/              [Game resources]
├── LICENSE                     [License file]
└── README.txt                  [README]
```

#### 10.3.2 User Data Directory Structure
**Location:** `C:\Users\[Username]\AppData\Local\GameCoVua\`

```
GameCoVua/
├── config/
│   └── settings.ini            [User preferences]
├── data/
│   ├── games/                  [Saved games]
│   ├── cache/                  [Cache files]
│   └── logs/                   [Application logs]
└── temp/                       [Temporary files]
```

### 10.4 System Resource Utilization

#### 10.4.1 Memory Usage Profile
| Component | Memory Usage | Notes |
|-----------|-------------|-------|
| JVM Startup | ~50 MB | Heap allocation |
| Base Application | ~100-150 MB | GUI framework |
| Game Engine | ~50-100 MB | Board & piece state |
| AI Calculations | ~50-200 MB | Variable based on depth |
| **Total (Typical)** | **250-450 MB** | Under normal conditions |

#### 10.4.2 CPU Usage Profile
| Operation | CPU Impact |
|-----------|-----------|
| Menu navigation | < 5% |
| User move input | < 10% |
| Board rendering | < 15% |
| AI calculation (Normal) | 50-70% |
| AI calculation (Hard) | 80-95% |

#### 10.4.3 Disk I/O
| Operation | I/O Time |
|-----------|----------|
| Application startup | 2-5 seconds |
| Game loading | <1 second |
| Save game | <1 second |
| Load game | <1 second |

---

## 11. SUPPORT AND MAINTENANCE

### 11.1 Support Channels

#### 11.1.1 Primary Support
**GitHub Issues Repository**
- URL: https://github.com/letuanphat21/GameCoVua2.0/issues
- Response time: 24-48 hours (estimated)
- Best for: Bug reports, feature requests

#### 11.1.2 Documentation
**Primary Documentation**
- URL: https://github.com/letuanphat21/GameCoVua2.0/wiki
- Contains: FAQs, tutorials, technical details

### 11.2 Reporting Issues

#### 11.2.1 Bug Report Format
When reporting a bug, include:
1. **Title:** Brief description of issue
2. **Environment:**
   - Windows version
   - Java version (if applicable)
   - Installation method (App-Image / JAR)
   - System specifications (RAM, CPU)

3. **Steps to Reproduce:**
   - Detailed sequence of actions
   - Expected vs. actual behavior

4. **Screenshots/Logs:**
   - Visual evidence of issue
   - Log file excerpts (if applicable)
   - Error messages (exact text)

5. **Additional Information:**
   - When issue first occurred
   - Previous version status
   - Workarounds attempted

#### 11.2.2 Example Bug Report
```
Title: Application crashes when AI calculates move in Hard difficulty

Environment:
- Windows 11 (Build 22621)
- Java 21.0.1
- App-Image installation
- 8 GB RAM, Intel i7

Steps to Reproduce:
1. Start new game (Human vs AI)
2. Set difficulty to Hard
3. Make two moves
4. AI calculates next move
5. Application crashes after ~30 seconds

Expected: AI makes move and returns control to player
Actual: Application crashes with no error dialog

Screenshots: [attached crash dialog]
Log file: [attached gamecovua.log]

Workaround: Use Normal difficulty instead
```

### 11.3 Maintenance and Updates

#### 11.3.1 Update Checking
The application checks for updates:
- Frequency: Upon application startup
- Notification: Dialog box with "Update Available" message
- Action: User can download and install update

#### 11.3.2 Updating the Application

**Method 1: Automatic Update (Recommended)**
1. Application notifies of available update
2. Click "Update" button
3. New version downloads and installs
4. Application restarts

**Method 2: Manual Update**
1. Visit https://github.com/letuanphat21/GameCoVua2.0/releases
2. Download latest version
3. Follow installation procedures (Section 5)
4. Backup previous version if needed

#### 11.3.3 Version Compatibility
| Version | Release Date | Status | Support |
|---------|-------------|--------|---------|
| 2.0.0 | 2026-05-06 | Current | Full |
| 1.9.x | Earlier | Legacy | Limited |
| 1.0.x | Earlier | EOL | None |

### 11.4 Maintenance Tasks

#### 11.4.1 Regular Maintenance Schedule
| Task | Frequency | Duration |
|------|-----------|----------|
| Clear cache | Monthly | <1 minute |
| Review logs | Quarterly | <5 minutes |
| Update Java | Every 3 months | 5-10 minutes |
| Backup game data | As needed | <1 minute |

#### 11.4.2 Cache Clearing
**Location:** `C:\Users\[Username]\AppData\Local\GameCoVua\cache\`

To manually clear:
1. Close application
2. Delete all files in cache directory
3. Restart application (cache rebuilds automatically)

#### 11.4.3 Log File Management
**Location:** `C:\Users\[Username]\AppData\Local\GameCoVua\logs\`

**To access logs:**
- Open `gamecovua.log` with text editor
- Contains debug information and error records

---

## 12. APPENDICES

### Appendix A: Glossary of Terms

| Term | Definition |
|------|-----------|
| **AI** | Artificial Intelligence - computer opponent |
| **App-Image** | Self-contained application package |
| **Checkmate** | Board position where king cannot escape capture |
| **JRE** | Java Runtime Environment |
| **Minimax** | Game theory algorithm for decision-making |
| **PGN** | Portable Game Notation - chess game format |
| **Runtime** | Software required to execute a program |
| **Stalemate** | Position where player cannot move but king not in check |

### Appendix B: Command Reference

#### Command Line Options
```bash
# Standard launch
java -jar GameCoVua.jar

# With verbose output
java -verbose:gc -jar GameCoVua.jar

# Increased memory (2GB)
java -Xmx2G -jar GameCoVua.jar

# Disable GUI rendering optimization
java -Dsun.java2d.noddraw=true -jar GameCoVua.jar

# Set language
java -Duser.language=vi -jar GameCoVua.jar
```

### Appendix C: Frequently Asked Questions (FAQ)

**Q: Do I need to install Java separately?**  
A: No, if using App-Image version. Java is bundled. If using JAR version, Java 21 is required.

**Q: Can I play online?**  
A: Online multiplayer is under development and will be available in future versions.

**Q: Where are game saves stored?**  
A: `C:\Users\[Username]\AppData\Local\GameCoVua\data\games\`

**Q: Can I run the game on macOS or Linux?**  
A: JAR version can run on any system with Java 21. App-Image version is Windows-only.

**Q: How do I report a bug?**  
A: Visit https://github.com/letuanphat21/GameCoVua2.0/issues and create new issue.

**Q: What is the minimum display resolution?**  
A: 1024×768 minimum, 1920×1080 recommended.

**Q: Can I uninstall and reinstall the game?**  
A: Yes, follow procedures in Section 9 for uninstall and Section 5 for reinstall.

### Appendix D: System Compatibility Matrix

| OS | Version | Support | Status |
|----|---------|---------|--------|
| Windows | 7 SP1 | Tested | ✓ Supported |
| Windows | 8 / 8.1 | Tested | ✓ Supported |
| Windows | 10 | Tested | ✓ Supported |
| Windows | 11 | Tested | ✓ Supported |
| macOS | 10.14+ | JAR only | ⚠ Limited |
| Linux | Ubuntu 18.04+ | JAR only | ⚠ Limited |

### Appendix E: Performance Benchmarks

#### Configuration A: Minimum Requirements
- CPU: Pentium IV 2.0 GHz
- RAM: 512 MB
- Storage: HDD
- Expected AI response time (Normal): 2-3 seconds

#### Configuration B: Recommended
- CPU: Core i5 2.5 GHz
- RAM: 1 GB
- Storage: SSD
- Expected AI response time (Normal): <1 second
- Expected AI response time (Hard): 2-4 seconds

#### Configuration C: High-End
- CPU: Core i7 3.0+ GHz
- RAM: 2+ GB
- Storage: SSD
- Expected AI response time (Hard): <1 second

---

## Document Control

| Revision | Date | Author | Changes |
|----------|------|--------|---------|
| 1.0 | 2026-05-06 | Documentation Team | Initial release |

---

## Approval and Sign-off

| Role | Name | Signature | Date |
|------|------|-----------|------|
| Project Manager | [Lê Tuấn Phát] | ________________ | ________ |
| Quality Assurance | [QA Lead] | ________________ | ________ |
| Documentation Manager | [Doc Lead] | ________________ | ________ |

---

**End of Document**

**Document ID:** GCV-DEPLOY-001  
**Version:** 1.0  
**Last Updated:** 2026-05-06  
**Classification:** Public  
**Distribution:** Unrestricted
