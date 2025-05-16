/*
 * Copyright © Schweizerische Bundesbahnen SBB, 2023.
 */

package ch.sbb.tms.capaopt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Version {

    private static final Pattern PATTERN = Pattern.compile("^(\\d+)\\.(\\d+)\\.(\\d+)");

    private final int major;
    private final int minor;
    private final int patch;

    private Version(final int major, final int minor, final int patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    public static Version fromString(final String versionString) {
        final Matcher matcher = PATTERN.matcher(versionString);
        if (matcher.matches()) {
            return new Version(
                    Integer.parseInt(matcher.group(1)),
                    Integer.parseInt(matcher.group(2)),
                    Integer.parseInt(matcher.group(3))
            );
        } else {
            throw new IllegalArgumentException("%s is no valid version".formatted(versionString));
        }
    }

    public boolean greaterThan(final Version other) {
        return (major > other.major) ||
                (major == other.major) && (minor > other.minor) ||
                (major == other.major) && (minor == other.minor) && (patch > other.patch);
    }

}