/*
 * Copyright © Schweizerische Bundesbahnen SBB, 2023.
 */

package ch.sbb.tms.capaopt;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.openrewrite.ExecutionContext;
import org.openrewrite.Option;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.internal.lang.NonNull;

import java.util.regex.Pattern;

@Value
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

    @JsonCreator
    public UpgradeBaseImage(@NonNull @JsonProperty("newVersion") String newVersion) {
        this.newVersion = newVersion;
    }

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