package com.auth.mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MockitoBasic {

	// 1.@Mock Annotation
	@Mock
	List<String> mockedList;

	@Test
	public void whenUseMockAnnotation_thenMockIsInjected() {
		mockedList.add("one");
		Mockito.verify(mockedList).add("one");
		assertEquals(0, mockedList.size());

		Mockito.when(mockedList.size()).thenReturn(100);
		assertEquals(100, mockedList.size());
	}

	// 2.@Spy Annotation
	@Spy
	List<String> spiedList = new ArrayList<String>();

	@Test
	public void whenUseSpyAnnotation_thenSpyIsInjectedCorrectly() {
		spiedList.add("one");
		spiedList.add("two");

		Mockito.verify(spiedList).add("one");
		Mockito.verify(spiedList).add("two");

		assertEquals(2, spiedList.size());

		Mockito.doReturn(100).when(spiedList).size();
		assertEquals(100, spiedList.size());
	}

	// 3. @InjectMocks Annotation
	@Mock
	Map<String, String> wordMap;

	@InjectMocks
	MyDictionary dic = new MyDictionary();

	@Test
	public void whenUseInjectMocksAnnotation_thenCorrect() {
		Mockito.when(wordMap.get("aWord")).thenReturn("aMeaning");

		assertEquals("aMeaning", dic.getMeaning("aWord"));
	}

	public class MyDictionary {
		Map<String, String> wordMap;

		public MyDictionary() {
			wordMap = new HashMap<String, String>();
		}

		MyDictionary(Map<String, String> wordMap) {
			this.wordMap = wordMap;
		}

		public void add(final String word, final String meaning) {
			wordMap.put(word, meaning);
		}

		public String getMeaning(final String word) {
			return wordMap.get(word);
		}
	}

	// 4. Injecting a Mock Into a Spy
	@Mock
	Map<String, String> wordMap1;

	MyDictionary spyDic;

	

	
}
