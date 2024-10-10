package cl.devopcitos.alertasismos.models;

import static org.testng.Assert.assertEquals;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SismoTest {
    WebDriver driver;

    @BeforeTest
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        //driver = RemoteWebDriver(new URL("https://selenium.flexsolution.xyz"));
    }

    @Test
    public void searchTest() {
        // Abre Google
        driver.get("https://www.google.com");

        // Verifica que el título de la página sea "Google"
        String title = driver.getTitle();
        System.out.println("Título de la página: " + title);
        assertEquals(title, "Google", "El título de la página no es correcto");

        // Encuentra el campo de búsqueda usando su nombre
        driver.findElement(By.name("q")).sendKeys("Selenium WebDriver");

        // Envía la búsqueda
        driver.findElement(By.name("q")).submit();

        // Espera hasta que el título de la página cambie para reflejar los resultados de búsqueda 
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        assertEquals(driver.getTitle().contains("Selenium WebDriver"), true, "El título de la página no contiene 'Selenium WebDriver'");

        // Verifica que "Selenium WebDriver" aparezca en los resultados
        String pageSource = driver.getPageSource();
        if (pageSource.contains("Selenium WebDriver")) {
            System.out.println("La prueba pasó exitosamente.");
        } else {
            System.out.println("La prueba falló.");
            assertEquals(pageSource.contains("Selenium WebDriver"), true, "El término 'Selenium WebDriver' no se encontró en la página");
        }
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}