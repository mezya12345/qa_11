package ru.mail;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;
import ru.mail.model.Library;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;


public class HomeTest {
    ClassLoader cl = HomeTest.class.getClassLoader();

    @Test
    void zipParseTest() throws Exception {
        try (
                InputStream resources = cl.getResourceAsStream("home/home.zip");
                ZipInputStream zis = new ZipInputStream(resources)
        ) {
            ZipEntry entry;
            while((entry = zis.getNextEntry()) != null) {
                if (entry.getName().contains(".pdf")) {
                    PDF content = new PDF(zis);
                    assertThat(content.text).contains("Пример pdf-файла из архива");
                } else if (entry.getName().contains(".xlsx")) {
                    XLS content = new XLS(zis);
                    assertThat(content.excel.getSheetAt(0).getRow(1).getCell(2).getStringCellValue()).contains("Фантастика");
                } else if (entry.getName().contains(".csv")) {
                    CSVReader reader = new CSVReader(new InputStreamReader(zis));
                    List<String[]> content = reader.readAll();
                    assertThat(content.get(1)[2]).contains("Fantasy");
                }
            }
        }
    }

    @Test
    void jsonParseTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        try (
                InputStream resource = cl.getResourceAsStream("home/library_example.json");
                InputStreamReader reader = new InputStreamReader(resource)
        ) {
            Library library = objectMapper.readValue(reader, Library.class);
            assertThat(library.id).isEqualTo("3615");
            assertThat(library.generalInformation.title).isEqualTo("Red book");
            assertThat(library.generalInformation.authors).contains("Ivanov");
            assertThat(library.editions).contains("1965", "1981", "2003", "2023");
            assertThat(library.genre).isEqualTo("Fantasy");
        }
    }
}
