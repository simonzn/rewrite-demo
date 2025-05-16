/*
 * Copyright © Schweizerische Bundesbahnen SBB, 2023.
 */

package ch.sbb.tms.capaopt;

import org.openrewrite.ExecutionContext;
import org.openrewrite.FindSourceFiles;
import org.openrewrite.Preconditions;
import org.openrewrite.SourceFile;
import org.openrewrite.Tree;
import org.openrewrite.TreeVisitor;
import org.openrewrite.internal.lang.Nullable;
import org.openrewrite.text.PlainText;
import org.openrewrite.text.PlainTextParser;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Versions {

    public static TreeVisitor<?, ExecutionContext> updateVersionInLine(final String filePattern, final Pattern linePattern, final String newVersion) {
        return Preconditions.check(new FindSourceFiles(filePattern), new TreeVisitor<>() {

            @Override
            public Tree visit(@Nullable Tree tree, ExecutionContext ctx) {
                SourceFile sourceFile = (SourceFile) Objects.requireNonNull(tree);

                final PlainText plainText = PlainTextParser.convert(sourceFile);
                final Matcher matcher = linePattern.matcher(plainText.getText());

                if (!(matcher.find() && Version.fromString(newVersion).greaterThan(Version.fromString(matcher.group(1))))) {
                    return sourceFile;
                }

                final String newText = matcher.replaceAll(newVersion);
                return plainText.withText(newText);
            }

        });
    }

}