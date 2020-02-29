package com.github.tokuramakura.deployment;

import com.hpe.kraal.MainKt;
import io.quarkus.deployment.annotations.*;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.nativeimage.NativeImageSystemPropertyBuildItem;
import io.quarkus.deployment.pkg.builditem.JarBuildItem;
import io.quarkus.deployment.pkg.builditem.OutputTargetBuildItem;
import io.quarkus.deployment.pkg.steps.NativeBuild;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class KraalProcessor {
    static {
        System.out.println("kraal processor loaded");
    }


    @BuildStep(onlyIf = NativeBuild.class)
    public FeatureBuildItem feature() {
        System.out.println("init kraal feature");
        return new FeatureBuildItem("kraal");
    }

    @BuildStep(onlyIf = NativeBuild.class)
    public NativeImageSystemPropertyBuildItem jarOutput(
            JarBuildItem jarBuildItem,
            OutputTargetBuildItem outputTargetBuildItem) {
        System.out.println("kraal jar output");

        Path dir = outputTargetBuildItem.getOutputDirectory().resolve(outputTargetBuildItem.getBaseName() + "-native-image-source-jar");

        System.out.println(dir);
        try {
            try (Stream<Path> s = Files.walk(dir)) {
                String[] targets = s.map(p -> p.toAbsolutePath().toString())
                        .filter(p -> p.endsWith(".jar") || p.endsWith(".class"))
                        .toArray(String[]::new);
                if (targets.length > 0) {
                    PrintStream out = System.out;
                    try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
                         PrintStream ps = new PrintStream(baos)
                    ) {
//                        System.setOut(ps);
                        MainKt.main(targets);
                    } finally {
                        System.setOut(out);
                    }
                }
            }
        } catch (IOException | UncheckedIOException | NullPointerException e) {

        }
        return new NativeImageSystemPropertyBuildItem("kraal.enabled", "true");
    }
}
