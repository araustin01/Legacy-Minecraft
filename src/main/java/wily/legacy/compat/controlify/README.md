# Controlify + Legacy4J Controller Integration

## Overview

This integration allows Legacy4J to take control of menu navigation while leveraging Controlify's superior controller detection and management capabilities. The integration provides multiple approaches depending on your needs.

## Integration Approaches

### Option 1: Disable Controlify's Screen Processing (Recommended)

This approach keeps Controlify for controller detection but lets Legacy4J handle all GUI navigation:

**Pros:**
- Best of both worlds: Controlify's excellent controller support + Legacy4J's menu system
- No input conflicts
- Maintains Legacy4J's unified interface feel
- Controlify's advanced features (haptics, LED control) still work

**Cons:**
- Loses Controlify's advanced screen navigation features
- May need manual input mapping

**How it works:**
- `ControlifyIntegration.java` overrides Controlify's screen processors
- Legacy4J continues to handle all menu navigation as usual
- Controlify only handles controller detection and hardware features

### Option 2: Bridge Controller Input Between Systems

This approach creates a bridge that translates Controlify controller events to Legacy4J:

**Pros:**
- More granular control over which features to use from each mod
- Can selectively use Controlify features where beneficial

**Cons:**
- More complex implementation
- Potential for input conflicts
- Requires careful mapping between input systems

### Option 3: Configuration-Based Approach

This allows users to choose which mod handles what:

**Pros:**
- User choice and flexibility
- Can be toggled at runtime
- Easy to debug issues

**Cons:**
- More configuration complexity
- May confuse users

## Implementation Details

### Files Created:

1. **`ControlifyIntegration.java`** - Main integration that disables Controlify's screen processing
2. **`ControlifyControllerBridge.java`** - Bridge for translating between controller systems
3. **`ControlifyCompatConfig.java`** - Configuration and conflict management

### How to Use:

1. **Automatic Detection**: The integration automatically detects if Controlify is present
2. **Priority System**: Legacy4J takes priority for menu navigation
3. **Fallback**: If Controlify isn't present, Legacy4J works normally

### Key Benefits:

- **Enhanced Controller Support**: Uses Controlify's superior controller detection
- **Consistent Interface**: Maintains Legacy4J's menu navigation system
- **Advanced Features**: Access to Controlify's haptics, LED control, and driver support
- **No Conflicts**: Clean separation of responsibilities

## Technical Details

### Controlify Features Still Available:
- ✅ Controller detection and hot-plugging
- ✅ Advanced controller drivers (Steam Deck, PS5 haptics, etc.)
- ✅ Controller LED control
- ✅ Rumble/haptic feedback
- ✅ Battery level monitoring
- ✅ Controller type detection

### Legacy4J Features Maintained:
- ✅ Menu navigation and cursor control
- ✅ Inventory management
- ✅ Button mapping system
- ✅ Console-style interface behavior
- ✅ Legacy cursor modes

### Integration Flow:

```
Controller Input → Controlify Detection → Legacy4J Processing → Game Actions
                              ↓
                    Hardware Features (Haptics, LED)
```

## Configuration

The integration can be configured in `ControlifyCompatConfig.java`:

```java
public static boolean USE_LEGACY4J_FOR_GUI = true;        // Let Legacy4J handle menus
public static boolean USE_CONTROLIFY_FOR_DETECTION = true; // Use Controlify for controller detection
```

## Troubleshooting

### If Both Mods Fight for Control:
- Check that `ControlifyIntegration.init()` is being called
- Verify that the `DisabledScreenProcessor` is being used
- Look for duplicate input handling in logs

### If Controllers Aren't Detected:
- Ensure Controlify is properly loaded
- Check that `USE_CONTROLIFY_FOR_DETECTION = true`
- Verify controller compatibility with Controlify

### If Menu Navigation Doesn't Work:
- Confirm Legacy4J's controller manager is receiving input
- Check that `USE_LEGACY4J_FOR_GUI = true`
- Verify Legacy4J controller bindings are set up

## Future Improvements

1. **Selective Feature Integration**: Allow using Controlify's screen processors for specific screens
2. **Input Mapping UI**: GUI for mapping between Controlify and Legacy4J input systems
3. **Per-Controller Settings**: Different integration modes per controller type
4. **Advanced Bridging**: More sophisticated input translation between systems

## Performance Impact

The integration has minimal performance impact:
- **Memory**: Small overhead for bridge objects and event listeners
- **CPU**: Minimal processing for event translation
- **Input Latency**: No additional latency introduced

This integration provides the best possible controller experience by combining Controlify's excellent hardware support with Legacy4J's console-style interface design.
