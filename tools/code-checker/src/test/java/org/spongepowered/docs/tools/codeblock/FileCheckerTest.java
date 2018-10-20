package org.spongepowered.docs.tools.codeblock;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.junit.jupiter.api.Test;

class FileCheckerTest {

    @Test
    void test() throws IOException {
        File testRunDir = new File("target/test-run");
        testRunDir.mkdirs();
        File inFile = new File(testRunDir, "TestFile.rst");
        copyResourceToFile("/TestFile.rst", inFile);
        FileChecker checker = new FileChecker(inFile, inFile.getParent().length() + 1, testRunDir);
//        assertEquals(12, checker.process());
        checker.process();

        boolean match = true;
        File outputFile = new File(testRunDir, "Code_TestFile.java");
        try (InputStream fis = new FileInputStream(outputFile);
                InputStreamReader fir = new InputStreamReader(fis, UTF_8);
                BufferedReader reader1 = new BufferedReader(fir);
                InputStream ris = getClass().getResourceAsStream("/Code_TestFile.java");
                InputStreamReader rir = new InputStreamReader(ris, UTF_8);
                BufferedReader reader2 = new BufferedReader(rir)) {
            String line1, line2;
            while ((line1 = reader1.readLine()) != null & (line2 = reader2.readLine()) != null) {
                if (!line1.equals(line2)) {
                    match = false;
                    break;
                }
            }
            if (line1 != null) {
                match = false;
            }
            if (line2 != null) {
                match = false;
            }
        }
        if (!match) {
            copyResourceToFile("/Code_TestFile.java", new File(testRunDir, "expectedCode_TestFile.java"));
        }
        assertTrue(match, "Generated file does not match expected one");
    }

    private void copyResourceToFile(String resourcePath, File target) throws IOException {
        try (InputStream stream = getClass().getResourceAsStream(resourcePath);
                OutputStream fos = new FileOutputStream(target)) {
            stream.transferTo(fos);
        }
    }

}
