package ch.sbb.tms.capaopt;

import org.junit.jupiter.api.Test;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.test.SourceSpecs.text;

class UpgradeBaseImageTest implements RewriteTest {

    @Test
    void recipe_shouldUpdateBaseImage() {
        String recipe =
            """
            type: specs.openrewrite.org/v1beta/recipe
            name: ch.sbb.tms.capaopt.UpgradeToBaseImage3
            displayName: Update base image to v3
            description: 'Update base image to latest v3 build.'
            recipeList:
              - ch.sbb.tms.capaopt.UpgradeBaseImage:
                  newVersion: 3.0.0
            """;
        rewriteRun(
            spec -> spec.recipeFromYaml(recipe, "ch.sbb.tms.capaopt.UpgradeToBaseImage3"),
            text(
                """
                FROM tms-capaopt-release.docker.bin.sbb.ch/releng-jdk-base:2.0.0
                """,
                """
                FROM tms-capaopt-release.docker.bin.sbb.ch/releng-jdk-base:3.0.0
                """,
                spec -> spec.path("Dockerfile")
            )
        );
    }

}