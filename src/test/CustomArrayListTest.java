import org.junit.jupiter.api.BeforeEach;

class CustomArrayListTest extends CustomCollectionTest {
    @BeforeEach
    void createCollection() {
        collection = new CustomArrayList<>();
    }
}