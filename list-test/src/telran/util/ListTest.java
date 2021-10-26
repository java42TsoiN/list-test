package telran.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.function.Predicate;

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
		assertEquals(null, numbers.get(-1));
		assertNull( numbers.get(3));
	}
	@Test
	void testAdd() {
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
		assertEquals(10, numbers.remove(0));
		assertArrayEquals(expected0, getArrayFromList(numbers));
		assertEquals(40, numbers.remove(1));
		assertArrayEquals(expected1, getArrayFromList(numbers));
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
	void containsPredicateNumbersTest() {
		Predicate<Integer> predicate100= new GreaterNumberPredicate(100);
		Predicate<Integer> predicate25= new GreaterNumberPredicate(25);
		assertFalse(numbers.contains(predicate100));
		assertTrue(numbers.contains(predicate25));
	}
	@Test
	void testContainsPersons() {
		Person prs = new Person(123, "Moshe");
		Person prs2 = new Person(124, "Vasya");
		List<Person> persons = new ArrayList<>();
		persons.add(prs);
		persons.add(prs2);
		assertTrue(persons.contains(new Person(124, "Vasya")));
		assertTrue(persons.contains(prs2));
		assertFalse(persons.contains(new Person(125, "Olya")));
	}
	@Test
	void containsPredicateStringsTest() {
		Predicate<String> predicateName= new StartWithPredicate("name");
		Predicate<String> predicateMain= new StartWithPredicate("main");
		assertFalse(strings.contains(predicateMain));
		assertTrue(strings.contains(predicateName));
	}
	@Test
	void testIndexOfPattern() {
		strings.add("name1");
		String pattern = new String("name1");
		assertEquals(0,strings.indexOf(pattern));
		// V.R. It is necessary to add the following test
		 assertEquals(-1, strings.indexOf("XXX"));
	}
	@Test
	void testLastIndexOfPattern() {
		strings.add("name3");
		strings.add("name1");
		String pattern = new String("name3");
		String pattern1 = new String("name1");
		// V.R. lastIndexOf instead of LastIndexOf
		assertEquals(2,strings.lastIndexOf(pattern));
		assertEquals(3,strings.lastIndexOf(pattern1));
	}
	@Test
	void testIndexOfPredicate() {
		numbers.add(20);
		strings.add("name1");
		Predicate<Integer> predicateNumber= new EqualNumbersPredicate(13);
		Predicate<Integer> predicateNumber2= new EqualNumbersPredicate(20);
		assertEquals(-1,numbers.indexOf(predicateNumber));
		assertEquals(1,numbers.indexOf(predicateNumber2));
		
		Predicate<String> predicateString= new EqualStringsPredicate("name3");
		Predicate<String> predicateString1= new EqualStringsPredicate("name1");
		assertEquals(-1,strings.indexOf(predicateString));
		assertEquals(0,strings.indexOf(predicateString1));
	}
	@Test
	void testLastIndexOfPredicate() {
		numbers.add(20);
		strings.add("name1");
		Predicate<Integer> predicateNumber= new EqualNumbersPredicate(13);
		Predicate<Integer> predicateNumber2= new EqualNumbersPredicate(20);
		// V.R. lastIndexOf instead of LastIndexOf
		assertEquals(-1,numbers.lastIndexOf(predicateNumber));
		assertEquals(3,numbers.lastIndexOf(predicateNumber2));
		Predicate<String> predicateString= new EqualStringsPredicate("name3");
		Predicate<String> predicateString1= new EqualStringsPredicate("name1");
		// V.R. lastIndexOf instead of LastIndexOf
		assertEquals(-1,strings.lastIndexOf(predicateString));
		assertEquals(2,strings.lastIndexOf(predicateString1));
	}
	
	@Test
	void testRemovePredicate() {
		Integer[] expected = { 10,  40};
		String[] expected1 = {"name2"};
		Predicate<Integer> predicateNumber= new EqualNumbersPredicate(13);
		Predicate<Integer> predicateNumber2= new EqualNumbersPredicate(20);
		assertFalse(numbers.removeIf(predicateNumber));
		assertTrue(numbers.removeIf(predicateNumber2));
		assertArrayEquals(expected,getArrayFromList(numbers));
		
		Predicate<String> predicateString= new EqualStringsPredicate("name3");
		Predicate<String> predicateString1= new EqualStringsPredicate("name1");
		assertFalse(strings.removeIf(predicateString));
		assertTrue(strings.removeIf(predicateString1));
		assertArrayEquals(expected1,getArrayFromList(strings));
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
}