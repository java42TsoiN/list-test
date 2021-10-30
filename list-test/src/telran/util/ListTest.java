package telran.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.function.Predicate;

import javax.management.modelmbean.ModelMBeanNotificationBroadcaster;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ListTest {
private List<Integer> numbers;
private List<String> strings;
Integer initialNumbers[] = {10, 20, 40};
String initialStrings[] = {"name1", "name2"};
	@BeforeEach
	void setUp() throws Exception {
		numbers = getInitialNumbers();
		strings = getInitialStrings();
	}

	private List<String> getInitialStrings() {
		List<String> res = new ArrayList<>();
		for (int i = 0; i < initialStrings.length; i++) {
			res.add(initialStrings[i]);
		}
		return res;
	}

	private List<Integer> getInitialNumbers() {
		
		List<Integer> res = new ArrayList<>(1);
		for (int i = 0; i < initialNumbers.length; i++) {
			res.add(initialNumbers[i]);
		}
		return res;
	}

	@Test
	void testGet() {
		assertEquals(10, numbers.get(0));
		assertEquals("name1", strings.get(0));
		assertNull(numbers.get(-1));
		assertNull(numbers.get(3));
		


	}
	@Test
	void testAddAtIndex() {
		int inserted0 = 100;
		int inserted2 = -8;
		int inserted4 = 1000;
		Integer[] expected = {inserted0, 10, inserted2, 20, 40, inserted4};
		assertTrue(numbers.add(0, inserted0));
		assertTrue( numbers.add(2, inserted2));
		assertTrue( numbers.add(5, inserted4));
		assertArrayEquals(expected, getArrayFromList(numbers));
		assertFalse(numbers.add(7, 1000));
		assertFalse( numbers.add(-1, 1000));
	}
	@Test
	void testRemove() {
		Integer expected0[] = {20, 40};
		Integer expected1[] = {20};
		assertNull(numbers.remove(3));
		assertNull(numbers.remove(-1));
		assertEquals(10, numbers.remove(0));
		assertArrayEquals(expected0, getArrayFromList(numbers));
		assertEquals(40, numbers.remove(1));
		assertArrayEquals(expected1, getArrayFromList(numbers));
		
	}
	@Test 
	void testSize() {
		assertEquals(initialNumbers.length, numbers.size());
		numbers.add(100);
		assertEquals(initialNumbers.length + 1, numbers.size());
		numbers.remove(0);
		assertEquals(initialNumbers.length, numbers.size());
	}
	
	@Test
	void testContainsNumbers() {
		assertTrue(numbers.contains(initialNumbers[0]));
		assertFalse(numbers.contains(1000));
		numbers.add(1000);
		assertTrue(numbers.contains(1000));
		
		
	}
	@Test
	void testContainsStrings() {
		
		
		strings.add("Hello");
		String pattern = new String("Hello");
		assertTrue(strings.contains(pattern));
		assertTrue(strings.contains("Hello"));
	}
	@Test
	void testContainsPersons() {
		Person prs = new Person(123, "Moshe");
		Person prs2 = new Person(124, "Vasya");
		List<Person> persons = new ArrayList<>();
		persons.add(prs);
		persons.add(prs2);
		assertTrue(persons.contains(new Person(124, "Vasya")));
		assertTrue(persons.contains(prs));
		assertFalse(persons.contains(new Person(125, "Olya")));
	}
	@Test
	void containsPredicateNumbersTest() {
		Predicate<Integer> predicate100 = new GreaterNumberPredicate(100);
		Predicate<Integer> predicate25 = new GreaterNumberPredicate(25);
		assertFalse(numbers.contains(predicate100));
		assertTrue(numbers.contains(predicate25));
		
	}
	@Test
	void containsPredicateStringsTest() {
		Predicate<String> predicateName = new StartWithPredicate("name");
		Predicate<String> predicateMain = new StartWithPredicate("main");
		assertFalse(strings.contains(predicateMain));
		assertTrue(strings.contains(predicateName));
		
		
	}

	@SuppressWarnings("unchecked")
	private <T> T[] getArrayFromList(List<T> list) {
		int size = list.size();
		T[] res = (T[]) new Object[size];
		
		for (int i = 0; i < size; i++) {
			res[i] = list.get(i);
		}
		return res;
	}
	
	@Test
	void indexOfTest() {
		assertEquals(0, numbers.indexOf(10));
		assertEquals(2, numbers.indexOf(40));
		assertEquals(-1, numbers.indexOf(100));
	}
	@Test
	void lastIndexOfTest() {
		assertEquals(0, numbers.lastIndexOf(10));
		assertEquals(2, numbers.lastIndexOf(40));
		assertEquals(-1, numbers.lastIndexOf(100));
		numbers.add(10);
		assertEquals(3, numbers.lastIndexOf(10));
		
	}
	@Test
	void indexOfPredicate() {
		assertEquals(2, numbers.indexOf(new GreaterNumberPredicate(25)));
		assertEquals(0, numbers.indexOf(new GreaterNumberPredicate(5)));
		assertEquals(-1, numbers.indexOf(new GreaterNumberPredicate(45)));
	}
	@Test
	void lastIndexOfPredicate() {
		assertEquals(2, numbers.lastIndexOf(new GreaterNumberPredicate(25)));
		assertEquals(2, numbers.lastIndexOf(new GreaterNumberPredicate(5)));
		assertEquals(-1, numbers.lastIndexOf(new GreaterNumberPredicate(45)));
	}
	@Test
	void removeIfTest() {
		Integer expected[] = {10, 20};
		Integer expectedEmpty[] = {};
		Predicate<Integer> greater25 = new GreaterNumberPredicate(25);
		assertTrue(numbers.removeIf(greater25));
		assertFalse(numbers.removeIf(greater25));
		assertArrayEquals(expected, getArrayFromList(numbers));
		assertTrue(numbers.removeIf(new GreaterNumberPredicate(0)));
		assertArrayEquals(expectedEmpty, getArrayFromList(numbers));
		
		
		
	}
	@Test
	void removeAllTest() {
		numbers.add(20);
		List<Integer> otherNumbers = new ArrayList<>();
		otherNumbers.add(20);
		otherNumbers.add(40);
		assertTrue(numbers.removeAll(otherNumbers));
		Integer expected[] = {10};
		assertArrayEquals(expected, getArrayFromList(numbers));
		assertFalse(numbers.removeAll(otherNumbers));
	}
	@Test
	void removeAllSame() {
		assertTrue(numbers.removeAll(numbers));
		assertArrayEquals(new Integer[0], getArrayFromList(numbers));
	}

	@Test
	void containTest() {
		assertTrue(numbers.contains(20));
		assertFalse(numbers.contains(25));
		numbers.add(25);
		assertTrue(numbers.contains(25));
	}
	@Test
	void comparatorTest() {
		String  expected[] = {"name1","name2","name3","name4"};
		strings.add("name4");
		strings.add("name3");
		strings.sort(new StringComparator());
		assertArrayEquals(expected, getArrayFromList(strings));
	}
	@Test
	void comparatorIntTest() {
		Integer  expected[] = {10,20,30,40};
		numbers.add(30);
		
		numbers.sort(new IntegerComparator());
		assertArrayEquals(expected, getArrayFromList(numbers));
	}
	@Test
	void retainAll() {
		Integer  expected[] = {10};
		Integer  expected1[] = {10,20,40};
		List<Integer> retain = new ArrayList<>();
		numbers.retainAll(numbers);
		assertArrayEquals(expected1, getArrayFromList(numbers));
		
		retain.add(10);
		numbers.retainAll(retain);
		assertArrayEquals(expected, getArrayFromList(numbers));
		
	}
	
}