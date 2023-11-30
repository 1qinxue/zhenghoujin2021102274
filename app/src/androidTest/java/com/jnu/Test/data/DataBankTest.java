package com.jnu.Test.data;

import static org.junit.Assert.*;
import androidx.test.platform.app.InstrumentationRegistry;
import com.jnu.Test.R;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

public class DataBankTest {

    @Before
    public void setUp() throws Exception {
        DataBank dataBank = new DataBank();

    }

    @After
    public void tearDown() throws Exception {
        DataBank dataBank = new DataBank();
//        dataBank.saveBooks(InstrumentationRegistry.getInstrumentation().getTargetContext(),new ArrayList<Book>());
    }

    @Test
    public void booksInput() {

    }

    @Test
    public void saveBooks() {
        ArrayList<Book> books = new ArrayList<Book>();
        books.add(new Book("创新工程实践", 30, R.drawable.book_no_name));
        books.add(new Book("信息安全教学基础（第2版）", 100, R.drawable.book_1));

        DataBank dataBank = new DataBank();
        dataBank.saveBooks(InstrumentationRegistry.getInstrumentation().getTargetContext(),books);
        ArrayList<Book> books2 = dataBank.booksInput(InstrumentationRegistry.getInstrumentation().getTargetContext());
        assertNotSame(books,books2);
        assertEquals(books.size(),books2.size());
        for(int i=0;i<books.size();i++) {
            Assert.assertNotSame(books.get(i),books2.get(i));
            Assert.assertEquals(books.get(i).getName(),books2.get(i).getName());
            Assert.assertEquals(books.get(i).getPrice(),books2.get(i).getPrice(),0.01);
            Assert.assertEquals(books.get(i).getImageId(),books2.get(i).getImageId());
        }

    }
}