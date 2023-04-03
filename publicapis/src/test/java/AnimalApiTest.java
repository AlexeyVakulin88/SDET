import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;



public class AnimalApiTest {

    private List<ApiEntry> entries;

    @BeforeClass
    public void setup() {
        entries = AnimalApiHelper.getApiEntries();
    }

    @Test(description = "Get all data where category = 'Animals' and link contains 'github'")
    public void testGetAnimalGithubApis() {
        List<ApiEntry> animalGithubApis = AnimalApiHelper.getAnimalGithubApis(entries);
        Assert.assertFalse(animalGithubApis.isEmpty(), "No APIs found with category = 'Animals' and link contains 'github'");
        System.out.println("testGetAnimalGithubApis: Passed");
    }


    @Test(description = "Get all distinct cors values")
    public void testGetDistinctCorsValues() {
        Set<String> corsValues = AnimalApiHelper.getDistinctCorsValues(entries);
        Assert.assertFalse(corsValues.isEmpty(), "No distinct cors values found");
        System.out.println("testGetDistinctCorsValues: Passed");
    }

    @Test(description = "Get number of APIs in category = 'Animals'")
    public void testGetNumberOfAnimalApis() {
        long numberOfAnimalApis = AnimalApiHelper.getNumberOfAnimalApis(entries);
        Assert.assertNotEquals(numberOfAnimalApis, 0, "No APIs found with category = 'Animals'");
        System.out.println("testGetNumberOfAnimalApis: Passed");
    }
}
