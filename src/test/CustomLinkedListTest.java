import org.junit.jupiter.api.BeforeEach;

class CustomLinkedListTest extends CustomCollectionTest {
    @BeforeEach
    void createCollection() {
        collection = new CustomLinkedList<>();
    }
}