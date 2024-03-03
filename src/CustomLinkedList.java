import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Кастомная LinkedList c собственной реализацией
 *
 * @param <T> Тип сохраняемых объектов
 */
public class CustomLinkedList<T> implements CustomCollection<T> {
    /**
     * Внутренний узел хранящий объект и ссылки на следующий и предыдущий объекты
     *
     * @param <E> Тип сохраняемого объекта
     */
    private class Node<E> {
        /**
         * Хранимый объект
         */
        public E data;

        /**
         * Ссылка на последующий узел
         */
        public Node<E> next;
        /**
         * Ссылка на предыдущий узел
         */
        public Node<E> prev;

        /**
         * Создание узла с полями
         *
         * @param prev Ссылка на предыдущий узел
         * @param data Сохраняемый объект
         * @param next Ссылка на последующий узел
         */
        public Node(Node<E> prev, E data, Node<E> next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }

        /**
         * Создание пустого узла
         */
        public Node() {
        }
    }

    /**
     * Указатель на первый узел списка
     */
    private Node<T> head;

    /**
     * Указатель на последний узел списка
     */
    private Node<T> tail;

    /**
     * Размер коллекции
     */
    private int size = 0;

    /**
     * Добавить элемент в начало коллекции
     *
     * @param obj Сохраняемый объект
     */
    public void addFirst(T obj) {
        final Node<T> oldHead = head;
        final Node<T> newNode = new Node<>(null, obj, oldHead);
        head = newNode;
        if (oldHead == null)
            tail = newNode;
        else
            oldHead.prev = newNode;
        size++;
    }

    /**
     * Получить первый элемент коллекции
     *
     * @return Первый элемент коллекции
     */
    public T getFirst() {
        final Node<T> curHead = head;
        if (curHead == null)
            throw new NoSuchElementException();
        return head.data;
    }

    /**
     * Добавить объект в конец коллекции
     *
     * @param obj Запрашиваемый объект
     */
    @Override
    public void add(T obj) {
        final Node<T> oldTail = tail;
        final Node<T> newNode = new Node<>(oldTail, obj, null);
        tail = newNode;
        if (oldTail == null)
            head = newNode;
        else
            oldTail.prev = newNode;
        size++;
    }

    /**
     * Получить последний объект из коллекции
     *
     * @return Запрашиваемый объект
     * @throws NoSuchElementException Элементы в коллекции отсутствуют
     */
    public T getLast() {
        final Node<T> curtail = tail;
        if (curtail == null)
            throw new NoSuchElementException();
        return tail.data;
    }

    /**
     * Проверка входящего индекса на корректность
     *
     * @param index Проверяемый индекс
     * @throws IndexOutOfBoundsException Если получаемый индекс выходит за размер коллекции
     */
    private void checkInputIndex(int index) {
        if (index < 0 && index >= size) {
            throw new IndexOutOfBoundsException("Несуществующий индекс");
        }
    }

    /**
     * Добавить объект в коллекцию по индексу
     *
     * @param index Индекс сохраняемого объекта
     * @param obj   Сохраняемый объект
     */
    @Override
    public void add(int index, T obj) {
        if (index == size) {
            add(obj);
        } else if (index == 0) {
            addFirst(obj);
        } else {
            checkInputIndex(index);
            Node<T> currentNode = head;
            Node<T> nextNode = new Node<>();

            for (int i = 0; i < index - 1; i++) {
                currentNode = currentNode.next;
                nextNode = currentNode.next;
            }
            Node<T> newNode = new Node<>(currentNode, obj, nextNode);
            currentNode.next = newNode;
            nextNode.prev = newNode;

            size++;
        }
    }

    /**
     * Получить хранимый объект по заданному индексу
     *
     * @param index Индекс по которому запрашивается объект
     * @return Запрашиваемый объект
     */
    @Override
    public T get(int index) {
        checkInputIndex(index);

        if (index == 0) {
            return getFirst();
        } else if (index == size) {
            return getLast();
        }

        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.prev;
        }

        return currentNode.data;
    }

    /**
     * Удалить хранимый объект по его индексу
     *
     * @param index Индекс удаляемого объекта
     */
    @Override
    public void remove(int index) {
        checkInputIndex(index);

        if (index == 0) {
            head = head.next;
            head.prev = null;
            size--;
        } else if (index == size) {
            tail = tail.prev;
            tail.next = null;
            size--;
        }

        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        Node<T> prevNode = currentNode.prev;
        Node<T> nextNode = currentNode.next;
        prevNode.next = nextNode;
        nextNode.prev = prevNode;
        size--;
    }

    /**
     * Очистить всю коллекцию
     */
    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Отсортировать коллекцию с помощью метода compareTo() интерфейса Comparable
     *
     * @throws IllegalArgumentException Если класс сортируемых объектов не реализует интерфейс Comparable
     * @throws RuntimeException         Если в коллекции содержится null
     */
    @Override
    public void sorted() {
        T[] array = (T[]) new Object[size];
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (currentNode.data == null) {
                throw new RuntimeException("Для сортировки список не должен содержать null");
            }
            if (!(currentNode.data instanceof Comparable)) {
                throw new IllegalArgumentException("Класс хранимых объектов должен реализовывать интерфейс Comparable");
            }
            array[i] = currentNode.data;
            currentNode = currentNode.prev;
        }

        Arrays.sort(array);
        clear();

        for (T t : array) {
            add(t);
        }
    }
}
