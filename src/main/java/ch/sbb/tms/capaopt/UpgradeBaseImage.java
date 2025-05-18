/*
 * Copyright © Schweizerische Bundesbahnen SBB, 2023.
 */

package ch.sbb.tms.capaopt;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.openrewrite.ExecutionContext;
import org.openrewrite.Option;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.internal.lang.NonNull;

import java.util.regex.Pattern;

@Value
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = false)
public class UpgradeBaseImage extends Recipe {

    private static final Pattern PATTERN =
            Pattern.compile("(?<=FROM tms-capaopt-release\\.docker\\.bin\\.sbb\\.ch/releng-jdk-base:)(\\d+\\.\\d+\\.\\d+)");
    private static final String FILE_PATTERN = "**/Dockerfile*";

    @Option(displayName = "New version",
            description = "The new releng-jdk-base version to be used",
            example = "2.0.0")
    @NonNull
    String newVersion;

    @Override
    public String getDisplayName() {
        return "Update releng-jdk-base parent in Dockerfile";
    }

    @Override
    public String getDescription() {
        return "Updates the releng-jdk-base parent version if necessary.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return Versions.updateVersionInLine(FILE_PATTERN, PATTERN, newVersion);
    }

}